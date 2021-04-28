package nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle;



import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.shr.com.enumcommon.NotUseAtr;


/**
 * 社員の医療勤務形態履歴項目
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.社員情報.スケジュールチーム
 * @author HieuLt
 *
 */
@Getter
@AllArgsConstructor
public class EmpMedicalWorkFormHisItem implements DomainAggregate{
	
	/**社員ID **/
	private final String empID;
	
	/**履歴ID **/
	
	private final String historyID;
	
	/** 看護区分コード **/
	private NurseClassifiCode nurseClassifiCode;
	/**夜勤専従か --- 「する」の場合は、夜勤で専従する **/
	private NotUseAtr nightShiftFullTime;
	
	/** 医療勤務形態 **/
	private MedicalCareWorkStyle medicalWorkStyle;
	
	/** 他部署兼務か  --- するの場合、他部署で兼務する**/
	private NotUseAtr otherDepartmentConcurrently;
	
}
