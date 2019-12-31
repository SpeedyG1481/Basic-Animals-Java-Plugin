package com.speedyg.ras.entities;

public enum AdoptedType {
    COW("Cow", "Inek"), CHICKEN("Chicken", "Tavuk"), LLAMA("Llama", "Lama"), PIG("Pig", "Domuz"),
    SHEEP("Sheep", "Koyun"), MOOSHROOM("Mooshroom Cow", "Mantar Inek"), PARROT("Parrot", "Papağan"),
    RABBIT("Rabbit", "Tavşan"), WOLF("Wolf", "Kurt"), POLAR_BEAR("Polar Bear", "Kutup Ayısı");

    // 0 Englih
    // 1 Turkish
    private String[] locale;

    AdoptedType(String... name) {
        locale = name;
    }

    public String getLocaleName(int number) {
        return locale[number];
    }

    public String getLocaleName(String local) {
        if (local == null)
            return locale[0];

        switch (local) {
            case "TR":
                return locale[1];
            case "EN":
                return locale[0];
        }
        return locale[0];
    }
}
