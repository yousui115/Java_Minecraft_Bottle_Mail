package yousui115.bottlemail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import yousui115.bottlemail.item.ItemPieceOfPaper;

//TODO: 動けばいいや！のスタンス

public class TextReader
{
    //■このModディレクトリまでのパス
    private String strSource;
    //■Modディレクトリ内の目当てのファイルまでのパス
    private String strFile;

    /**
     * ■コンストラクタ
     * @param source
     * @param file
     */
    public TextReader(String source, String file)
    {
        this.strSource = source;
        this.strFile = file;
    }

    /**
     * ■ファイル読込の実行
     */
    public void readText()
    {
        try
        {
            //■最低限必要な情報が無いと駄目
            if (strSource == null || strFile == null) { return; }

            System.out.println("######################  readText() ##########################");
            System.out.println("strSource = " + strSource);
            System.out.println("strFile   = " + strFile);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            //Document doc = builder.parse(stream);

            //■テスト環境？ 本番環境？
            int length = strSource.length();
            boolean isJar = strSource.substring(length - 4).contains(".jar");
            if (isJar)
            {
                //■本番環境
                readJar(builder);
            }
            else
            {
                //■テスト環境
                //readFolder(builder);
                readXML(builder.parse(this.strSource + "\\" + this.strFile));

            }
        }
        catch (ParserConfigurationException e)
        {
            streamErr(e.toString());
        }
        catch(IOException e)
        {
            streamErr(e.toString());
        }
        catch(SAXException e)
        {
            streamErr(e.toString());
        }
    }

    /**
     * ■ファイル読込処理（本番環境用）
     */
    private void readJar(DocumentBuilder builder)
    {
        System.out.println("######################  readJar() ##########################");

        try
        {
            //■Jarファイル オープン
            JarFile jarFile = new JarFile(this.strSource);
            //■
            ZipEntry entry = jarFile.getEntry(this.strFile);

            if (entry != null)
            {
                //■
                InputStream stream = jarFile.getInputStream(entry);

                //■XMLファイル読込
                readXML(builder.parse(stream));

                //■Jarファイル クローズ
                jarFile.close();
            }
            else
            {
                streamErr("readJar : err");
            }
        }
        catch(FileNotFoundException e)
        {
            streamErr(e.toString());
        }
        catch(IOException e)
        {
            streamErr(e.toString());
        }
        catch(SAXException e)
        {
            streamErr(e.toString());
        }
    }

    /**
     * ■
     */
    private void readXML(Document doc)
    {
        System.out.println("######################  readXML() ##########################");

        Element element0 = doc.getDocumentElement();
        NodeList nodelist0 = element0.getChildNodes();

        for( int i=0; i<nodelist0.getLength(); i++ )
        {
            //■book
            Node node0 = nodelist0.item(i);

            if (!(node0 instanceof Element)) { continue; }

            Element element1 = (Element) node0;
            NodeList nodelist1 = element1.getChildNodes();

            //■Mailを作成。
            Mail mail = new Mail();

            for (int j = 0; j < nodelist1.getLength(); j++)
            {
                //■ノードアイテムの取得
                Node node1 = nodelist1.item(j);

                //■要素
                Node item = node1.getChildNodes().item(0);
                if (item == null) { continue; }
                String str = item.getNodeValue();
                System.out.println(str);

                //■欲しいノードを選別
                if (node1.getNodeName().contains("title"))
                {
                    //■タイトル
                    mail.strTitle = str;
                }
                else if (node1.getNodeName().contains("author"))
                {
                    //■著者
                    mail.strAuthor = str;
                }
                else if (node1.getNodeName().contains("message"))
                {
                    //■本文
                    mail.strMsg = str;
                }
                else if (node1.getNodeName().contains("item"))
                {
                    //■同梱アイテム
                    mail.strItem = str;
//                    List<ItemStack> stacks = OreDictionary.getOres(str);
//                    if (stacks.size() != 0)
//                    {
//                        mail.stack = stacks.get(0).copy();
//                    }
                }
                else if (node1.getNodeName().contains("weight"))
                {
                    //■重み付け
                    mail.weight = Integer.parseInt(str);
                }
            }

            //■メールを追加
            ItemPieceOfPaper.addMail(mail);
        }

    }

    /**
     * ■構文解析
     */
    private ArrayList<String> parse(String message)
    {
        String[] paragraph = message.split(",", 0);
        ArrayList<String> listMsg = new ArrayList<String>();

        for (int i = 0; i < paragraph.length; i++)
        {
            StringBuilder sb1 = new StringBuilder(paragraph[i]);

            //while(!paragraph[i].isEmpty())
            while(sb1.length() > 0)
            {
                int del = sb1.length() < 13 ? sb1.length() : 13;

                //TODO:機能してねー！
                if (sb1.length() > 13 &&
                    ("。".compareTo(sb1.substring(14, 15)) == 0 ||
                     "、".compareTo(sb1.substring(14, 15)) == 0 ))
                {
                    del = 14;
                }

                listMsg.add(sb1.substring(0, del));
                sb1.delete(0, del);

                if (listMsg.size() >= 14) { break; }
            }
            if (listMsg.size() >= 14) { break; }
        }

        return listMsg;
    }

    private void streamErr(String strErr)
    {
        System.out.println("#########[Err]############ " + strErr);
    }
}
