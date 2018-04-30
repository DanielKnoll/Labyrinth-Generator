package com.codecool.labyrinth_generator.Information;

import java.util.ArrayList;
import java.util.List;

public class DfsInfo extends AlgorithmInfo {
    private List<String> classNames = new ArrayList<String>();
    private List<String> classCodes = new ArrayList<String>();
    private String algoWikiInfo;
    private List<String> imageNames = new ArrayList<String>();

    public DfsInfo() {
        fillInfo();
        setName("Depth-first search algorithm");
        setDefaultApiLink("wall=0&amp;algo=0&amp;width=18&amp;height=10");
        setClassNames(classNames);
        setClassCodes(classCodes);
        setAlgoWikiInfo(algoWikiInfo);
        setImageNames(imageNames);

    }

    private void fillInfo() {
        classNames.add("Class name");
        classNames.add("Class name2");
        classCodes.add("Code will be here");
        classCodes.add("Code will be here2");
        imageNames.add("300px-Depth-first-tree.svg.png");
        imageNames.add("300px-Depth-first-tree.svg.png");
        algoWikiInfo =
                "<p>Depth-first search (DFS) is an algorithm for traversing or searching tree or graph data structures. One starts at the root (selecting some arbitrary node as the root in the case of a graph) and explores as far as possible along each branch before backtracking.</p>" +
                "<p>A version of depth-first search was investigated in the 19th century by French mathematician Charles Pierre Trémaux[1] as a strategy for solving mazes.</p>" +
                "<p>Nunc condimentum, nulla in faucibus commodo, elit massa pharetra nibh, at tincidunt libero dolor quis augue. Donec pulvinar consectetur tortor, eget gravida ligula molestie at. Aenean ullamcorper tempor fermentum. Vestibulum metus ante, aliquam sed ligula vitae, auctor tempus quam. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Cras vitae imperdiet justo, sed egestas nulla. Nunc sit amet malesuada mauris, sed pellentesque arcu. Sed venenatis mattis mi ac tincidunt. Pellentesque nec justo ullamcorper, cursus massa at, condimentum justo. Mauris pharetra ligula in nibh efficitur, ut facilisis nisi pulvinar. Nullam eu consectetur mi. In in dapibus sem, a pharetra sem. Pellentesque in blandit magna. Nulla in orci finibus, ornare massa ut, mollis velit.</p>" +
                "<p>Etiam condimentum congue est, eget accumsan enim ornare eget. Morbi sed dui sit amet purus hendrerit egestas. Mauris at tellus sit amet dolor commodo suscipit sit amet eget elit. Curabitur nec diam id risus pulvinar euismod. Sed et rutrum lacus. Fusce sit amet augue auctor nulla semper luctus eget at dolor. Vivamus egestas tincidunt tincidunt. Donec sapien velit, venenatis eu tincidunt nec, consequat eu lacus. Nulla finibus sodales mauris, sed consequat urna hendrerit sed. Morbi eleifend consectetur imperdiet. Morbi eu venenatis mauris. Morbi non mi vel orci ultricies semper. Pellentesque rutrum, odio sit amet auctor accumsan, enim arcu vestibulum quam, vel fringilla leo odio eget lorem. Sed nisl purus, porttitor et sagittis vel, pharetra lacinia lectus.</p>";
    }
}
