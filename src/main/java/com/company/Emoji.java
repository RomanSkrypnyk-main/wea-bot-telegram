package com.company;

public enum Emoji {
    UMBRELLA_WITH_RAIN_DROPS(null, '\u2614'),
    HIGH_VOLTAGE_SIGN(null, '\u26A1'),
    SNOWMAN_WITHOUT_SNOW(null, '\u26C4'),
    SUN_BEHIND_CLOUD(null, '\u26C5'),
    CLOSED_UMBRELLA('\uD83C', '\uDF02'),
    SUN_WITH_FACE('\uD83C', '\uDF1E'),
    FOGGY('\uD83C', '\uDF01'),
    CLOUD(null, '\u2601'),
    GLOBE_WITH_MERIDIANS('\uD83C', '\uDF10'),
    DOOR('\uD83D', '\uDEAA'),
    HEAVY_EXCLAMATION_MARK_SYMBOL(null, '\u2757'),
    ROCKET('\uD83D', '\uDE80'),
    LEFT_RIGHT_ARROW(null, '\u2194');

    Character firstChar;
    Character secondChar;

    Emoji(Character firstChar, Character secondChar) {
        this.firstChar = firstChar;
        this.secondChar = secondChar;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (this.firstChar != null) {
            sb.append(this.firstChar);
        }
        if (this.secondChar != null) {
            sb.append(this.secondChar);
        }

        return sb.toString();
    }
}
