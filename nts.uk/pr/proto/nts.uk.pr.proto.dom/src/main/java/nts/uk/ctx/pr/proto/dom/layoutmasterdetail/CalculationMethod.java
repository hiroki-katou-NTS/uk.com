package nts.uk.ctx.pr.proto.dom.layoutmasterdetail;
/**
 * 
 * 計算方法
 *
 */
public enum CalculationMethod {
	/** 0:手入力*/
	MANUAL_ENTRY(0),
	/** 1:個人情報 */
	PERSONAL_INFORMATION(1),
	/** 2:計算式*/
	FORMULA(2),
	/** 3:賃金テーブル*/
	WAGE_TABLE(3),
	/** 4: 共通金額*/
	COMMON_AMOUNT_MONEY(4),
	/** 5:支給相殺*/
	PAYMENT_CANCELED(5);
	 /**
     * value.
     */
    public final int value;
    /**
     * Constructor.
     * @param 計算方法の値
     */
    private CalculationMethod(int value) {
        this.value = value;
    }
    
    /**
     * valueOf.
     * @param 計算方法の値
     * @return 計算方法
     */
    public static CalculationMethod valueOf(int value) {
        switch (value) {
            case 0:
                return MANUAL_ENTRY;
            case 1:
                return PERSONAL_INFORMATION;
            case 2:
                return FORMULA;
            case 3:
                return WAGE_TABLE;
            case 4:
                return COMMON_AMOUNT_MONEY;
            case 5:
                return PAYMENT_CANCELED;
            default:
                throw new RuntimeException("Invalid value of CalculationMethod");
        }
    }
    
    /**
     * value
     * @return int 
     */
    public int value() {
        return value;
    }
}
