package nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * 社員の医療勤務形態履歴Repository											
 * @author HieuLt
 *
 */
public interface EmpMedicalWorkStyleHistoryRepository {
	/** [1] 社員の医療勤務形態項目get(社員ID, 基準日)**/
	Optional<EmpMedicalWorkFormHisItem> get(String empID , GeneralDate referenceDate);
	
	/** [2] 社員の医療勤務形態項目*get(List<社員ID>, 基準日) **/
	List<EmpMedicalWorkFormHisItem> get(List<String> listEmpId , GeneralDate referenceDate);
	
	/** [3] insert(社員の医療勤務形態履歴, 社員の医療勤務形態履歴項目) **/
	void insert(EmpMedicalWorkStyleHistory empMedicalWorkStyleHistory , EmpMedicalWorkFormHisItem empMedicalWorkFormHisItem );
	
	/** [4] update(社員の医療勤務形態履歴)**/
	void update(EmpMedicalWorkStyleHistory empMedicalWorkStyleHistory);
	/** [5] update(社員の医療勤務形態履歴項目)**/
	void update(EmpMedicalWorkFormHisItem empMedicalWorkFormHisItem);
	/**[6] delete(社員ID, 履歴ID)**/
	void delete(String empId , String historyId); 
}
