package nts.uk.ctx.at.aggregation.dom.scheduledailytable;

import java.math.BigDecimal;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.LicenseClassification;

/**
 * 職場別の回数集計結果
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.勤務計画実施表.勤務計画実施表の職場計を集計する.職場別の回数集計結果
 * @author lan_lt
 */
@Value
public class NumberTimeEachWorkplaceCounter {
	/** 社員ID **/
	private GeneralDate ymd;
	
	/** 回数集計NO **/
	private Integer totalCountNo;
	
	/** 免許区分  **/
	private LicenseClassification licenseCls;
	
	/** 値  */
	private BigDecimal value;
}
