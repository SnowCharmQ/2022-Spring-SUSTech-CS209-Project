package com.cs209.project.entity;

import java.util.Objects;

public class IssueWord {
    String word;
    int count;

    public IssueWord(String word){
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IssueWord issueWord = (IssueWord) o;
        return Objects.equals(word.toUpperCase(), issueWord.word.toUpperCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(word.toUpperCase());
    }
}
