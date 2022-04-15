package com.example.emovs;

public enum GeneralShowType {
    MOVIES ,TV_SHOWS , DVD , BANGTAN_TV , VLIVE , OTHERS;
    public String toString(){
        switch (this){
            case MOVIES:    return "movies";
            case TV_SHOWS:  return "tv_shows";
            default:        return "none";
        }
    }
}
