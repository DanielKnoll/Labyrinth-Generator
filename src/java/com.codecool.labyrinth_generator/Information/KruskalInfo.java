package com.codecool.labyrinth_generator.Information;

import java.util.ArrayList;
import java.util.List;

public class KruskalInfo extends AlgorithmInfo {
    private List<String> classNames = new ArrayList<String>();
    private List<String> classCodes = new ArrayList<String>();
    private String algoWikiInfo;
    private List<String> imageNames = new ArrayList<String>();

    public KruskalInfo() {
        fillInfo();
        setName("Kruskal's algorithm");
        setDefaultApiLink("wall=0&amp;algo=1&amp;width=19&amp;height=13");
        setClassNames(classNames);
        setClassCodes(classCodes);
        setAlgoWikiInfo(algoWikiInfo);
        setImageNames(imageNames);

    }

    private void fillInfo() {
        classNames.add("Class name");
        classCodes.add("Code will be here");
        imageNames.add("200px-Kruskal_Algorithm_1.svg.png");
        algoWikiInfo =
                "<p>Nunc condimentum, nulla in faucibus commodo, elit massa pharetra nibh, at tincidunt libero dolor quis augue. Donec pulvinar consectetur tortor, eget gravida ligula molestie at. Aenean ullamcorper tempor fermentum. Vestibulum metus ante, aliquam sed ligula vitae, auctor tempus quam. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Cras vitae imperdiet justo, sed egestas nulla. Nunc sit amet malesuada mauris, sed pellentesque arcu. Sed venenatis mattis mi ac tincidunt. Pellentesque nec justo ullamcorper, cursus massa at, condimentum justo. Mauris pharetra ligula in nibh efficitur, ut facilisis nisi pulvinar. Nullam eu consectetur mi. In in dapibus sem, a pharetra sem. Pellentesque in blandit magna. Nulla in orci finibus, ornare massa ut, mollis velit.</p>";
    }
}
