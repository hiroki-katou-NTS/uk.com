package nts.uk.ctx.pr.proto.dom.layout.detail;

import java.util.HashMap;
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
    
	private static HashMap<Integer, CalculationMethod> map = new HashMap<>();
	
	static {
		for(CalculationMethod item : CalculationMethod.values()){
			map.put(item.value, item);
		}
	}
	 
    /**
     * Constructor.
     * @param 計算方法の値
     */
    CalculationMethod(int value) {
        this.value = value;
    }
    
    /**
     * valueOf.
     * @param 計算方法の値
     * @return 計算方法
     */
    static CalculationMethod valueOf(int value) {
        return map.get(value);
    }    
}
