package nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle;



import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;


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
	
	private final String  historyID;
	
	/**夜勤専従か --- 「する」の場合は、夜勤で専従する **/
	private boolean nightShiftFullTime;
	
	/** 医療情報 **/
	private Optional<MedicalWorkFormInfor> optMedicalWorkFormInfor;
	
	/**介護情報 **/
	private Optional<NursingWorkFormInfor> opyNursingWorkFormInfor;
	
	
	
}
