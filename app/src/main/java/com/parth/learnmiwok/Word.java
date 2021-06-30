package com.parth.learnmiwok;

public class Word {
    private String DefaultTranslation;
    private String MiwokTranslation;
    final static int NO_IMAGE_STATE =-1;
    private int songResource;
    private int imageResource=NO_IMAGE_STATE;

    public Word(String defaultTranslation, String miwokTranslation, int imageResource, int songResource) {
        DefaultTranslation = defaultTranslation;
        MiwokTranslation = miwokTranslation;
        this.songResource = songResource;
        this.imageResource = imageResource;
    }


    public Word(String defaultTranslation, String miwokTranslation, int songResource) {
        DefaultTranslation = defaultTranslation;
        MiwokTranslation = miwokTranslation;
        this.songResource=songResource;
    }


    public String getDefaultTranslation() {
        return DefaultTranslation;
    }

    public int getImageResource(){
        return imageResource;
    }

    public boolean hasImage(){
        return imageResource!=NO_IMAGE_STATE;
    }

    public int getSongResource() {
        return songResource;
    }

    public String getMiwokTranslation() {
        return MiwokTranslation;
    }



}
