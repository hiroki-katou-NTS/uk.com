package nts.uk.ctx.at.aggregation.dom.form9;

import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * 社員IDと年月日
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.様式９.社員の出力医療時間を取得する.社員IDと年月日
 * @author lan_lt
 *
 */
@Value
public class EmployeeIdAndYmd {
	
	/** 社員ID **/
	private String employeeId;
	
	/** 年月日 **/
	private GeneralDate ymd;

}
