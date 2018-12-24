package com.yuhang;

import com.yuhang.dao.PoetDao;
import com.yuhang.entiy.Poet;
import com.yuhang.entiy.Poetry;
import com.yuhang.service.PoetryService;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.wltea.analyzer.lucene.IKAnalyzer;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TangpoetryApplicationTests {

    @Autowired
    private PoetryService poetryService;
    @Autowired
    private PoetDao poetDao;
    /**
     * 创建索引
     * 测试中的错误：数据库查询为空
     * 已解决
     *
     * @throws IOException
     */
    @Test
    public void contextIndex()throws IOException {
        FSDirectory fsDirectory = FSDirectory.open(Paths.get("E:\\index\\08"));
        IndexWriter indexWriter = new IndexWriter(fsDirectory, new IndexWriterConfig(new IKAnalyzer()));
        List<Poetry> poetries = poetryService.queryAll();
        Document document = null;
        for (Poetry poetry : poetries) {
            document = new Document();
            //添加域值
            document.add(new IntField("id",poetry.getId(), Field.Store.YES));
            document.add(new StringField("author",poetry.getPoet().getName(), Field.Store.YES));
            document.add(new TextField("title",poetry.getTitle(), Field.Store.YES));
            document.add(new TextField("content",poetry.getContent(), Field.Store.YES));
            //添加到索引的写入器里
            indexWriter.addDocument(document);
        }
        indexWriter.commit();//提交
        indexWriter.close();//关闭
    }

    @Test
    public void SearchIndex()throws IOException, ParseException ,InvalidTokenOffsetsException{
        FSDirectory fsDirectory = FSDirectory.open(Paths.get("E:\\index\\08"));
        IndexReader indexReader = DirectoryReader.open(fsDirectory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        //查询解析器对象 作用：解析查询的表达式 域名：条件
        //参数一:域名(默认域)
        QueryParser queryParser = new QueryParser("author",new IKAnalyzer());
        Query query = null;
        //声明请求参数信息
        int nowpage = 1;
        int pageSize = 10;
        query = queryParser.parse("床前"); //解析方法
        query = queryParser.parse("author:李白");
        //query = queryParser.parse("李  OR  content:李 OR title:李");
        TopDocs topDocs = null;
        //分页数据
        if(nowpage <=1){
            topDocs = indexSearcher.search(query,pageSize);
        }else if(nowpage >1){
            //假如说： 不是第一页 必须先获取上一页的最后一条记录的ScoreDoc
            topDocs = indexSearcher.search(query, (nowpage-1)*pageSize);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            ScoreDoc sd = scoreDocs[scoreDocs.length-1];
            // 参数一: 当前页的上一页的最后的文档的ScoreDoc对象
            topDocs = indexSearcher.searchAfter(sd,query,pageSize);
        }
        //创建高亮器对象
        Scorer scorer = new QueryScorer(query);
        //使用自定义的高亮样式
        Formatter formatter = new SimpleHTMLFormatter("<span style=\"color:red\">","</span>");
        Highlighter highlighter = new Highlighter(formatter,scorer);
        ScoreDoc[]  scoreDo = topDocs.scoreDocs;
        List<ScoreDoc>scoreDocs = Arrays.asList(scoreDo);
        for (ScoreDoc scoreDoc : scoreDocs){
            int docID = scoreDoc.doc; //文档的编号
            Document document = indexReader.document(docID);
            System.out.println(
                    scoreDoc.score+"|"+
                            document.get("id")+"|" +
                            document.get("title")+"|" +
                            highlighter.getBestFragment(new IKAnalyzer(),"author",document.get("author"))+"|"+
                            highlighter.getBestFragment(new IKAnalyzer(),"content",document.get("content"))+"|"
            );
        }
        indexReader.close();
    }


    @Test
    public void test243(){
        List<Poet> poet = poetDao.findPoet();
        for (Poet poet1 : poet) {
            System.out.println(poet1.getName());
        }
    }
}




