package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import lombok.Getter;
import lombok.Setter;

/**
 * 計算内部で使用する処理計算完了か、未完了かと実績データを保持するクラス
 * @author keisuke_hoshina
 *
 */
@Getter
public class ManageCalcStateAndResult {

	//計算処理が実行された
	boolean isCalc;
	
	@Setter
	IntegrationOfDaily integrationOfDaily;
	
	/**
	 * @param isCalc 計算したかどうかのboolean true = 計算できた。
	 * Constructor 
	 */
	private ManageCalcStateAndResult(boolean isCalc, IntegrationOfDaily integrationOfDaily) {
		super();
		this.isCalc = isCalc;
		this.integrationOfDaily = integrationOfDaily;
	}
	
	/**
	 * 計算処理を実行した時のConstructor
	 * @param integrationOfDaily
	 * @return
	 */
	public static ManageCalcStateAndResult successCalc(IntegrationOfDaily integrationOfDaily) {
		return new ManageCalcStateAndResult(true, integrationOfDaily);
	}
	
	/**
	 * 計算処理をしなかったときのConstructor
	 * @param integrationOfDaily
	 * @return
	 */
	public static ManageCalcStateAndResult failCalc(IntegrationOfDaily integrationOfDaily) {
		return new ManageCalcStateAndResult(false, integrationOfDaily);
	}	
}
