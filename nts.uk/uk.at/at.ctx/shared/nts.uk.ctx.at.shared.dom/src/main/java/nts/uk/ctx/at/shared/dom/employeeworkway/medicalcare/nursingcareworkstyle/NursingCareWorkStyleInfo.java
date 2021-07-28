package nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.nursingcareworkstyle;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.MedicalCareWorkStyle;

/**
 * 介護勤務形態情報
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.社員の働き方.医療介護.介護勤務形態.介護勤務形態情報
 * @author HieuLt
 *
 */
@Value
public class NursingCareWorkStyleInfo implements DomainValue {
	
	/** 介護勤務形態 **/
	private final MedicalCareWorkStyle medicalCareWorkStyle;
	
	/** 介護の兼務可能か  **/
	private final boolean asNursingCare;
	
	/** 常勤備考 **/
	private final FulltimeRemarks fulltimeRemarks;
	
	/** 夜勤備考**/
	private final NightShiftRemarks nightShiftRemarks;
	
}
