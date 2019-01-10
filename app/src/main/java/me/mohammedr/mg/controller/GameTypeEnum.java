package me.mohammedr.mg.controller;


/**
 * Game Type
 */
public enum GameTypeEnum {
    /**
     * No of moves and card can be flipped at a time
     */
    DEFAULT_TILES(16, 2);

    private final int maxNoOfFlips;

    private final int tiles;

    GameTypeEnum(int tiles, int maxNoOfFlips) {
        this.tiles = tiles;
        this.maxNoOfFlips = maxNoOfFlips;
    }

    public int getMaxNoOfFlips() {
        return maxNoOfFlips;
    }

    public int getTiles() {
        return tiles;
    }

}