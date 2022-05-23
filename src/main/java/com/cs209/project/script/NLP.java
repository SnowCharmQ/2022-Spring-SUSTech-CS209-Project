package com.cs209.project.script;

import java.io.*;
import java.util.*;

import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class NLP {
    public static void main(String[] args) throws IOException {
        // creates a StanfordCoreNLP object, with POS tagging, lemmatization,
        // NER, parsing, and coreference resolution
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        File f = new File("src/test/java/com/cs209/project/file/MybatisIssuefile.txt");
        File ff = new File("src/test/java/com/cs209/project/file/NLPanswerMybatisIssue.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(ff));
        BufferedReader br = new BufferedReader(new FileReader(f));
        String text;
        HashMap<String, Integer> temp = new HashMap<>();
        while((text = br.readLine())!=null) {

            // read some text in the text variable

            // create an empty Annotation just with the given text
            Annotation document = new Annotation(text);

            // run all Annotators on this text
            pipeline.annotate(document);


            // these are all the sentences in this document
            // a CoreMap is essentially a Map that uses class objects as keys and
            // has values with custom types
            List<CoreMap> sentences = document.get(SentencesAnnotation.class);

            List<String> words = new ArrayList<>();
            List<String> posTags = new ArrayList<>();
            List<String> nerTags = new ArrayList<>();
            for (CoreMap sentence : sentences) {
                // traversing the words in the current sentence
                // a CoreLabel is a CoreMap with additional token-specific methods
                for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
                    // this is the text of the token
                    String word = token.get(TextAnnotation.class);
                    words.add(word);
                    // this is the POS tag of the token
                    String pos = token.get(PartOfSpeechAnnotation.class);
                    posTags.add(pos);
                    // this is the NER label of the token
                    String ne = token.get(NamedEntityTagAnnotation.class);
                    nerTags.add(ne);
                }
            }
            for(int i = 0;i< words.size();i++){
                if(posTags.get(i).equals("NN")){
                    if(temp.containsKey(words.get(i))){
                        temp.replace(words.get(i),temp.get(words.get(i)),  temp.get(words.get(i)) +1);
                    }else{
                        temp.put(words.get(i), 1);
                    }
                }
            }
            System.out.println(words.toString());
            System.out.println(posTags.toString());
            System.out.println(nerTags.toString());
        }
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(temp.entrySet()); //转换为list
        list.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        //for循环
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getKey() + ": " + list.get(i).getValue());
            bw.write(list.get(i).getKey() + "\t" + list.get(i).getValue() + "\n");
        }
        bw.close();

    }

}

