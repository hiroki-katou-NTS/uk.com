package nts.uk.ctx.at.shared.dom.scherec.optitem;

import lombok.AllArgsConstructor;

/**
 * 
 * @author anhnm
 * Refactor 5
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).任意項目
 * 計算区分
 * 
 */
@AllArgsConstructor
public enum CalculationClassification {

    // 計算する
    CALC(1, "計算する"),
    
    // 計算しない
    NOT_CALC(0, "計算しない");
    
    public final int value;
    
    public final String name;
    
    /**
	 * 計算するか判定する
	 * @return するならtrue
	 */
	public boolean isCalc() {
		return CALC.equals(this);
	}
	
	/**
	 * 計算しないか判定する
	 * @return しないならtrue
	 */
	public boolean isNotCalc() {
		return NOT_CALC.equals(this);
	}
}
