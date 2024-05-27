package com.example.aircraftwar2024.supply;


import com.example.aircraftwar2024.basic.AbstractFlyingObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 炸弹道具，自动触发
 * <p>
 * 使用效果：清除界面上除BOSS机外的所有敌机（包括子弹）
 * <p>
 * 【观察者模式】
 *
 * @author hitsz
 */
public class BombSupply extends AbstractFlyingSupply {
    private static List<AbstractFlyingObject> flyingObjects = new ArrayList<>();

    public BombSupply(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    public static void addFlyingObject(AbstractFlyingObject flyingObject) {
        flyingObjects.add(flyingObject);
    }

    //删除观察者
    public static void removeFlyingObject() {
        flyingObjects.removeIf(AbstractFlyingObject::notValid);
    }

    //通知所有观察者
    public void notifyAllFlyingObject() {
        for(AbstractFlyingObject flyingObject : flyingObjects) {
            addScore += flyingObject.update();
        }
    }

    @Override
    public void activate() {
        System.out.println("BombSupply active");
        notifyAllFlyingObject();
    }

}
