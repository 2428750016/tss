package com.yuhang.controller;

import com.yuhang.entiy.Poet;
import com.yuhang.entiy.Poetry;
import com.yuhang.service.PoetryService;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wltea.analyzer.lucene.IKAnalyzer;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 关于搜索的的控制器
 */
@Controller
@RequestMapping("/baidu")
public class ShowController {
    @Autowired
    private PoetryService poetryService;

    @RequestMapping("/show")
    public String show(String word,Integer nowpage,Model model)throws  Exception{
        FSDirectory fsDirectory = FSDirectory.open(Paths.get("E:\\index\\08"));
        IndexReader indexReader = DirectoryReader.open(fsDirectory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //查询解析器对象 作用：解析查询的表达式 域名：条件
        //参数一:域名(默认域)
        QueryParser queryParser = new QueryParser("author",new IKAnalyzer());
        Query query = null;
        //声明请求参数信息
        query = queryParser.parse("author:"+word+" OR title:"+word+" OR content:"+word+" OR id:"+word);
        //声明每页显示页数
        int pageSize = 10;
        TopDocs topDocs = null;
        //分页数据
        if(nowpage==null||nowpage<=1){
            nowpage=1;
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
        ScoreDoc[]  scoreDocs = topDocs.scoreDocs;
        List<Poetry> scoreDocs1 = new ArrayList<Poetry>();

        for (ScoreDoc scoreDoc : scoreDocs) {
            Poetry poetry = new Poetry();
            Poet poet = new Poet();
            int docID = scoreDoc.doc; //文档的编号
            Document document = indexReader.document(docID);
            String id = document.get("id");
            String title = document.get("title");
            String author = document.get("author");
            String content = document.get("content");

            String id1 = highlighter.getBestFragment(new IKAnalyzer(), "id", id);
            String title1 = highlighter.getBestFragment(new IKAnalyzer(), "title", title);
            String author1 = highlighter.getBestFragment(new IKAnalyzer(), "author", author);
            String content1 = highlighter.getBestFragment(new IKAnalyzer(), "content", content);
            if(id1!=null){
                poetry.setId(Integer.parseInt(id1));
            }else{
                poetry.setId(Integer.parseInt(id));
            }

            if(title1!=null){
                poetry.setTitle(title1);
            }else{
                poetry.setTitle(title);
            }

            if(author1!=null){
                poet.setName(author1);
                poetry.setPoet(poet);
            }else {
                poet.setName(author);
                poetry.setPoet(poet);
            }
           ;
            if(content1!=null){
                poetry.setContent(content1);
            }else{
                poetry.setContent(content);
            }

            scoreDocs1.add(poetry);
        }

        model.addAttribute("scoreDocs",scoreDocs1);
        model.addAttribute("word",word);
        model.addAttribute("nowpage",nowpage);
        indexReader.close(); //关流
        return "main";
    }
}
