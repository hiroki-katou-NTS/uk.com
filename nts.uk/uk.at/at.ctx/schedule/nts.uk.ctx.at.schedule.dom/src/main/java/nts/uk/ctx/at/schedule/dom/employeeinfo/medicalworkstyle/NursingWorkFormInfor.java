package nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * 介護勤務形態情報
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.社員情報.スケジュールチーム
 * @author HieuLt
 *
 */
@Value
public class NursingWorkFormInfor implements DomainValue {
	
	/** 介護勤務形態 **/
	private final MedicalCareWorkStyle medicalCareWorkStyle;
	
	/** 介護の兼務可能か  **/
	private final boolean asNursingCare;
	
	/** 常勤備考 **/
	private final FulltimeRemarks fulltimeRemarks;
	
	/** 夜勤備考**/
	private final NightShiftRemarks nightShiftRemarks;
	
}
