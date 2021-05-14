package nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle;



import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.MedicalCareWorkStyle;
/**
 * 社員の医療勤務形態履歴項目
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.社員の働き方.医療介護.医療勤務形態.社員の医療勤務形態履歴項目
 * @author HieuLt
 *
 */
@Getter
@AllArgsConstructor
public class EmpMedicalWorkStyleHistoryItem implements DomainAggregate{
	
	/**社員ID **/
	private final String empID;
	
	/**履歴ID **/
	private final String historyID;
	
	/** 看護区分コード **/
	private NurseClassifiCode nurseClassifiCode;
	/**夜勤専従か  **/
	private boolean isOnlyNightShift;
	
	/** 医療勤務形態 **/
	private MedicalCareWorkStyle medicalWorkStyle;
	
	/** 他部署兼務か  **/
	private boolean isConcurrently;
	
}
