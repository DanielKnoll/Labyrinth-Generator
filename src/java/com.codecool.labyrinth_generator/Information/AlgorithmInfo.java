package com.codecool.labyrinth_generator.Information;

import java.util.ArrayList;
import java.util.List;

public abstract class AlgorithmInfo {
    private String name;
    private String defaultApiLink;
    private List<String> classNames = new ArrayList<String>();
    private List<String> classCodes = new ArrayList<String>();
    private String algoWikiInfo;
    private List<String> imageNames = new ArrayList<String>();

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public String getDefaultApiLink() {
        return defaultApiLink;
    }

    void setDefaultApiLink(String defaultApiLink) {
        this.defaultApiLink = defaultApiLink;
    }

    public List<String> getClassNames() {
        return classNames;
    }

    void setClassNames(List<String> classNames) {
        this.classNames = classNames;
    }

    public List<String> getClassCodes() {
        return classCodes;
    }

    void setClassCodes(List<String> classCodes) {
        this.classCodes = classCodes;
    }

    public String getAlgoWikiInfo() {
        return algoWikiInfo;
    }

    void setAlgoWikiInfo(String algoWikiInfo) {
        this.algoWikiInfo = algoWikiInfo;
    }

    public List<String> getImageNames() {
        return imageNames;
    }

    void setImageNames(List<String> imageNames) {
        this.imageNames = imageNames;
    }

}
