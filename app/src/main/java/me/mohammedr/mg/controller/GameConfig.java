package me.mohammedr.mg.controller;

import android.util.Pair;
import android.util.SparseIntArray;

import java.util.HashMap;

/**
 * Game configuration (Board configuration)
 */
public class GameConfig {

    /**
     * The total number of tiles in a game
     */
    private int numOfTiles;

    /**
     * Max number of card flips in a board
     */
    private int maxNoOfCardFlips;

    /**
     * Pair containing the no of rows and columns
     * First : Row
     * Second : Column
     */
    private Pair<Integer, Integer> pairOfRowCol;

    /**
     * Pair of map For ex: (0,4)(4,0)
     */
    private HashMap<Integer, Integer> pairsMap;

    /**
     * Drawable id mapped with the card id
     */
    private SparseIntArray tileImageIdMap;

    public GameConfig(int numOfTiles, int maxNoOfCardFlips) {
        this.numOfTiles = numOfTiles;
        this.maxNoOfCardFlips = maxNoOfCardFlips;
        this.pairOfRowCol = new Pair<>((int) Math.sqrt(numOfTiles), (int) Math.sqrt(numOfTiles));
        this.pairsMap = new HashMap<>();
        this.tileImageIdMap = new SparseIntArray();
    }

    public int getMaxNoOfCardFlips() {
        return maxNoOfCardFlips;
    }

    public Pair<Integer, Integer> getRowAndColPair() {
        return pairOfRowCol;
    }

    public void putInPair(int id, int key) {
        pairsMap.put(id, key);
    }

    public void putInTileImage(int id, int drawableId) {
        tileImageIdMap.put(id, drawableId);
    }


    public int getDrawableId(int id) {
        return tileImageIdMap.get(id);
    }

    public boolean isMatched(int[] pairOfFlippedIds) {
        //TODO: To add the support for multiple pairing
        int firstId = pairOfFlippedIds[1];
        int secondId = pairsMap.get(pairOfFlippedIds[0]) != null ? pairsMap.get(pairOfFlippedIds[0]) : Integer.MIN_VALUE;
        return firstId == secondId;
    }
}
