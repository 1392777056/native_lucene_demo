package cn.zhangsan.mytest;

import com.hankcs.lucene.HanLPAnalyzer;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/4 15:17.
 */
public class MyLucene {

    @Test
    public void myLuceneTest() throws IOException {

        // 文件的位置
        String url = "D:\\Lucene_index";
        // 创建目录
        FSDirectory directory = FSDirectory.open(Paths.get(url));
        // 分词器  ---- 标准的分词器  特点：针对中文 逐一分词
        /*Analyzer analyzer = new StandardAnalyzer();*/
        Analyzer analyzer = new HanLPAnalyzer();
        // 核心写对象配置信息对象，  参数：指定分词器
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        // 创建索引库核心写对象
        IndexWriter iw = new IndexWriter(directory,config);

        // 加载数据源
        File file = new File("E:\\tool\\lucene\\searchsource");
        // 加载文件列表
        File[] files = file.listFiles();
        for (File file1 : files) {
            // 获取文件名称
            String fileName = file1.getName();
            // 获取文件的内容
            String fileStr = FileUtils.readFileToString(file1);
            // 获取文件的大小
            long fileSize = FileUtils.sizeOf(file1);
            // 获取文件路径
            String path = file1.getPath();

            // 创建文件对象
            Document document = new Document();

            // 参数一：域字段名称  参数二：域字段内容   参数三：是否存储
            Field filedNameField = new TextField("fileName",fileName,Field.Store.YES);
            Field fileStrField = new TextField("fileStr",fileName,Field.Store.YES);
            // 文件路径域：(不分词，不索引，只存储)
            Field pathField = new StoredField("path",path);
            Field fileSizeField = new StoredField("fileSize",path);

            document.add(filedNameField);
            document.add(fileStrField);
            document.add(pathField);
            document.add(fileSizeField);

            // 将文档对象 添加到索引库
            iw.addDocument(document);
            iw.commit();
        }
        // 关闭资源
        iw.close();

    }


}
