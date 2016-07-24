package com.ar.siosi.swmaestrobackendproject;

import android.graphics.Bitmap;
import android.location.Location;

import java.util.List;

/**
 * Created by siosi on 2016-07-13.
 */
public class PoketMon {

    private int poketId; // 포켓몬 아이디
    private int poketMonHp; // 포켓몬 hp
    private String poketMonName; // 포켓몬 이름
    private int level; // 레벨
    private int exp; // 경험치
    private String poketMonType; // 포켓몬 속성
    private int damage; // 공격력
    private int defence; // 방어력
    private Bitmap bitmap; // 포켓몬 이미지

    private Location location; // 출물 위치

    PoketMon() {

    }

    PoketMon(int poketId, int poketMonHp,String poketMonName,int level,int exp,
             String poketMonType,int damage,int defence) {
        this.poketId = poketId;
        this.poketMonHp = poketMonHp;
        this.poketMonName = poketMonName;
        this.level = level;
        this.exp = exp;
        this.poketMonType = poketMonType;
        this.damage = damage;
        this.defence = defence;
    }

    public Location pocketLocation; // 포켓몬 위치
    private List<String> skiiList; // 스킬 있는거

    public int getPoketMonHp() {
        return poketMonHp;
    }

    public void setPoketMonHp(int poketMonHp) {
        this.poketMonHp = poketMonHp;
    }

    public String getPoketMonName() {
        return poketMonName;
    }

    public void setPoketMonName(String poketMonName) {
        this.poketMonName = poketMonName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public String getPoketMonType() {
        return poketMonType;
    }

    public void setPoketMonType(String poketMonType) {
        this.poketMonType = poketMonType;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public List<String> getSkiiList() {
        return skiiList;
    }

    public void setSkiiList(List<String> skiiList) {
        this.skiiList = skiiList;
    }
}
