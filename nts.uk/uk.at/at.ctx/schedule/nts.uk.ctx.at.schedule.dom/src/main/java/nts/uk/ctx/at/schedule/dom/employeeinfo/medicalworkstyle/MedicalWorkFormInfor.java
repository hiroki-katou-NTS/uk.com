package nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle;


import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
/**
 * 医療勤務形態情報
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.社員情報.医療勤務形態
 * @author HieuLt
 *
 */
@Value
public class MedicalWorkFormInfor implements DomainValue {
	
	/** 医療勤務形態 **/
	private final MedicalCareWorkStyle medicalCareWorkStyle;
	/** 看護区分コード **/
	private final NurseClassifiCode nurseClassifiCode;
	/** 他部署兼務か  --- するの場合、他部署で兼務する**/
	private final boolean otherDepartmentConcurrently;
	
}
