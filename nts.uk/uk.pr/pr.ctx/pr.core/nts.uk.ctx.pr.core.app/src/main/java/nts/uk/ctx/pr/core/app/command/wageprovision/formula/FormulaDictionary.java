package nts.uk.ctx.pr.core.app.command.wageprovision.formula;

public enum FormulaDictionary {
    PLUS("+", "＋"),
    SUBTRACT("-", "ー"),
    MULTIPLICY("*", "×"),
    DIVIDE("/", "÷"),
    POW("^", "^"),
    OPEN_BRACKET("(", "("),
    CLOSE_BRACKET(")",")"),
    GREATER(">",">"),
    LESS("<", "<"),
    LESS_OR_EQUAL("<=","≦"),
    GREATER_OR_EQUAL("<=","≧"),
    EQUAL("=","＝"),
    DIFFERENCE("<>","≠"),
    IF("IF", "関数＠条件式"),
    AND("AND", "関数＠かつ"),
    OR("OR", "関数＠または"),
    ROUND_OFF("IF", "関数＠四捨五入"),
    TRUNCATION("TRUNC", "関数＠切り捨て"),
    ROUND_UP("IF", "関数＠切り上げ"),
    MAX_VALUE("MAX", "関数＠最大値"),
    MIN_VALUE("MIN", "関数＠最小値"),
    NUM_OF_FAMILY_MEMBER("", "関数＠家族人数"),
    YEAR_MONTH("EDATE", "関数＠年月加算"),
    YEAR_EXTRACTION("YEAR", "関数＠年抽出"),
    MONTH_EXTRACTION("MONTH", "関数＠月抽出");

    /** The value. */
    public final String excelName;

    /** The name id. */
    public final String jpName;

    private FormulaDictionary(String excelName, String jpName) {
        this.excelName = excelName;
        this.jpName = jpName;
    }
}
