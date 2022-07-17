import java.util.HashMap;
import java.util.Map;

public class Bottle {
    private float _bottleVolume;
    private Map<String, Float> _compound = new HashMap<>();
    private float _liquidVolume;

    public Bottle(int volume) {
        _bottleVolume = volume;
    }

    public float get_liquidVolume() {
        return _liquidVolume;
    }

    public Map<String, Float> get_compound() {
        return _compound;
    }

    public float get_bottleVolume() {
        return _bottleVolume;
    }

    private boolean isBottleAccommodateLiquid() {
        return get_liquidVolume() <= get_bottleVolume();
    }

    public void addLiquid(String liquidType, float amount) throws Exception {
        get_compound().put(liquidType, amount);
        _liquidVolume += amount;
        if (isBottleAccommodateLiquid() == false)
            throw new Exception("Bottle can't accommodate this amount of liquid");
    }


    public void plus(Bottle otherBottle) throws Exception {
        float newLiquidVolume = get_liquidVolume() + otherBottle.get_liquidVolume();

        Map<String, Float> newCompound = new HashMap<>(get_compound());
        otherBottle.get_compound().forEach((k, v) ->
                newCompound.merge(k, v * otherBottle.get_liquidVolume(), Float::sum));

        _liquidVolume = newLiquidVolume;
        _compound = newCompound;

        if (isBottleAccommodateLiquid() == false)
            throw new Exception("Bottle can't accommodate this amount of liquid");
    }

    @Override
    public String toString() {
        Map<String, Float> percentMap = new HashMap<>();
        get_compound().forEach((k, v) -> percentMap.merge(k, (v / get_liquidVolume()) * 100, Float::sum));
        String bottleVolume = Float.toString(get_bottleVolume());
        String liquidVolume = Float.toString(get_liquidVolume());

        return "Bottle Volume: " + bottleVolume + "\n" +
                "All Liquid Volume: " + liquidVolume + "\n" +
                "Bottle compound in percent: " + percentMap;
    }
}

class Program {
    public static void main(String[] args) throws Exception {

        Bottle bottle1 = new Bottle(5);
        bottle1.addLiquid(LiquidTypes.VODKA, 0.4f);
        bottle1.addLiquid(LiquidTypes.WATER, 0.2f);
        bottle1.addLiquid(LiquidTypes.ORANGE_JUICE, 0.2f);

        Bottle bottle2 = new Bottle(2);
        bottle2.addLiquid(LiquidTypes.VODKA, 0.6f);
        bottle2.addLiquid(LiquidTypes.WATER, 0.4f);

        bottle1.plus(bottle2);

        System.out.println(bottle1);
    }
}
