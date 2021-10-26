package nts.uk.ctx.at.aggregation.dom.form9;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.LicenseClassification;
/**
 * 様式９のソートの社員情報
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.様式９.様式９で並び替える.様式９のソートの社員情報
 * @author lan_lt
 *
 */
@Value
public class Form9SortEmployeeInfo {
	
	/** 社員ID **/
	private final String employeeId;
	
	/** 免許区分 **/
	private final LicenseClassification licenseClassification;
	
	/** 職位コード **/
	private final String jobTitleCode;
	
	/** 社員コード **/
	private final String employeeCode;
	
}
