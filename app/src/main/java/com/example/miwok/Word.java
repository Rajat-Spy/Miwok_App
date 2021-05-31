package com.example.miwok;

public class Word {
    private String mMiwokTranslation;
    private String mDefaultTranslation;
    private static final int NO_IMAGE_PROVIDED = -1;
    private int mImageId = NO_IMAGE_PROVIDED;
    private int mAudioId;
    public Word(String defaultTranslation, String miwokTranslation, int auidioresourceId){
        this.mAudioId = auidioresourceId;
        this.mDefaultTranslation = defaultTranslation;
        this.mMiwokTranslation = miwokTranslation;
    }
    public Word(String defaultTranslation, String miwokTranslation, int imageId, int auidioresourceId){
        this.mDefaultTranslation = defaultTranslation;
        this.mMiwokTranslation = miwokTranslation;
        this.mImageId = imageId;
        this.mAudioId = auidioresourceId;
    }

    public String getDefaultTranslation(){
        return mDefaultTranslation;
    }
    public String getMiwokTranslation(){
        return mMiwokTranslation;
    }
    public int getImageId(){ return mImageId; }
    public boolean hasImage(){ return mImageId != NO_IMAGE_PROVIDED; }
    public int hasAudio(){ return mAudioId; }
}
