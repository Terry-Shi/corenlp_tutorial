package com.mycorenlp;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.SentimentAnnotator;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentAnnotatedTree;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.StringUtils;

/**
 * Ref: http://stanfordnlp.github.io/CoreNLP/api.html
 * @author shijie
 *
 */
public class CoreNLPMain {
    
    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        process();
        long end = System.currentTimeMillis();
        
        System.out.println("Total time: " + (end - begin));
    }
    
    public static void process() {
        // 中文处理需要添加配置文件
        String[] args = new String[] {"-props", "StanfordCoreNLP-chinese.properties"
                //"edu/stanford/nlp/hcoref/properties/zh-coref-default.properties"  // 中文指代消解
                };
        
        // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution
        //Properties props = new Properties();
        // tokenize: 分词；ssplit:分句；pos:词性标注；lemma:获取词原型；parse:句法解析（含依存句法）；dcoref:同义指代； sentiment 情感分析
        //props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref, sentiment");
        Properties props = StringUtils.argsToProperties(args);
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // read some text in the text variable
        //String text = "Stanford University is located in California. It is a great university. Meg is the 2nd female CEO of HP! This is a great PC. This PC is not so good after all! 今天天气真好。"; // Add your text here!
        String text = "一些盗版制品经营者为了应付和躲避打击，经营手法更为隐蔽。";
        //  approach 1
        Annotation  document = pipeline.process(text);
        
        // approach 2
//        // create an empty Annotation just with the given text
//        Annotation document = new Annotation(text);
//        // run all Annotators on this text
//        pipeline.annotate(document);

        // these are all the sentences in this document
        // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);

        // 遍历所有句子，输出每一句的处理结果  
        for (CoreMap sentence : sentences) {
            System.out.println("--------------------- Begin of one sentence--------------------------");
            System.out.println(sentence.toString());
            
            // 遍历每一个词traversing the words in the current sentence
            // a CoreLabel is a CoreMap with additional token-specific methods
            for (CoreLabel token : sentence.get(TokensAnnotation.class)) { // TODO 还有哪些class? 
                // this is the text of the token  拆分后的单词
                String word = token.get(TextAnnotation.class);
                // this is the POS tag of the token 词性：动词VBZ，副词,名词NNP，形容词JJ，冠词DT......
                String pos = token.get(PartOfSpeechAnnotation.class);
                // this is the NER label of the token 命名实体。 例如：ORGANIZATION，O，LOCATION，DATE，TIME，PERSON 目的是识别语料中人名、地名、组织机构名等命名实体
                String ne = token.get(NamedEntityTagAnnotation.class);
                System.out.println("word = " + word + " pos = " + pos + " ne = " + ne);
            }
//            // this is the parse tree of the current sentence
//            Tree treeAnnotation = sentence.get(TreeAnnotation.class);
//            System.out.println("TreeAnnotation = " + treeAnnotation);
//            // 先获取当前句子的依存句法分析结果this is the Stanford dependency graph of the current sentence
//            SemanticGraph dependencies = sentence
//                    .get(CollapsedCCProcessedDependenciesAnnotation.class);
            
            // 情感分析（不支持中文）
            Tree treeSentiment = sentence
                    .get(SentimentAnnotatedTree.class);
            int sentiment = RNNCoreAnnotations.getPredictedClass(treeSentiment);
            System.out.println(printSentiment(sentiment));
            System.out.println("--------------------- End of one sentence--------------------------");
        }

        // This is the coreference link graph
        // Each chain stores a set of mentions that link to each other,
        // along with a method for getting the most representative mention
        // Both sentence and token offsets start at 1!
        // coreference 表示指代关系
        Map<Integer, CorefChain> graph = document.get(CorefChainAnnotation.class);
        System.out.println(graph);
    }
    
    
    public static String getStrforSentiment(int mainSentiment) {
        String sentiment = "";
        switch (mainSentiment) {
        case 0:
            sentiment = "Very Happy";
            break;
        case 1:
            sentiment = "Happy";
            break;
        case 2:
            sentiment = "Neutral";
            break;
        case 3:
            sentiment = "Sad";
            break;
        case 4:
            sentiment = "Very Sad";
            break;
        }
        return sentiment;
    }
    
    public static String printSentiment(int id){
        String sentiment = "";
        switch(id){
            case 0:sentiment = "Very Negative";break;
            case 1:sentiment = "Negative";break;
            case 2:sentiment = "Neutral";break;
            case 3:sentiment = "Positive";break;
            case 4:sentiment = "Very Positive";break;
            default:break;
        }
        return sentiment;
    }
}
