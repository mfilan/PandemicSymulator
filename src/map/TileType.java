package map;

public enum TileType {
    grass(1),
    road(2),
    wholesale(3),
    shop(4),
    pavement(5),
    crossing(6);

    private int number;

    TileType(int number) {
        this.number = number;
    }

    public static String getName(int number) {
        if (number == 1) return String.format("%s", grass);
        if (number == 2) return String.format("%s", road);
        if (number == 3) return String.format("%s", wholesale);
        if (number == 4) return String.format("%s", shop);
        if (number == 5) return String.format("%s", pavement);
        if (number == 6) return String.format("%s", crossing);
        return String.format("%s", grass);
    }
}
