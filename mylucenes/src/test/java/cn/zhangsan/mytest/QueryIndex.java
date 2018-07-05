package cn.zhangsan.mytest;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/4 20:22.
 */
public class QueryIndex {

    @Test
    public void queryIndexs() throws IOException {
        // 创建查询对象，
        TermQuery query = new TermQuery(new Term("fileStr", "spring"));
        excuteQuery(query);
    }

    /**
     * 查询所有
     * @throws IOException
     */
    @Test
    public void multIndexs() throws IOException {
        // 创建查询对象，
        Query query = new MatchAllDocsQuery();
        excuteQuery(query);
    }

    private void excuteQuery(Query query) throws IOException {
        // 索引路径
        String url = "D:\\Lucene_index";
        // 索引目录库
        FSDirectory directory = FSDirectory.open(Paths.get(url));
        // 创读的对象
        DirectoryReader reader = DirectoryReader.open(directory);
        // 创建查询的核心对象
        IndexSearcher indexSearcher = new IndexSearcher(reader);
        // 执行查询 1.查询对象， 2. 查询结果的记录数
        TopDocs docs = indexSearcher.search(query, 10);
        System.out.println("满足条件的查询数量" + docs.totalHits);
        ScoreDoc[] scoreDocs = docs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int doc = scoreDoc.doc;
            System.out.println("文档ID： " + doc);
            System.out.println("文档得分" +scoreDoc.score);
            // 根据id获取文档域字段的内容
            Document doc1 = indexSearcher.doc(doc);
            System.out.println("文档名称：" + doc1.get("fileName"));
            System.out.println("文档大小：" + doc1.get("fileSize"));
            System.out.println("文档路径：" + doc1.get("path"));
            System.out.println("文档内容：" + doc1.get("fileStr"));

        }
    }

}
