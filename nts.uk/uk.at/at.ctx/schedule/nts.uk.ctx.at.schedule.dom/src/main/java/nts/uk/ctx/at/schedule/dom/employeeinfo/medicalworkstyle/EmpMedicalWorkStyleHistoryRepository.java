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
	Optional<EmpMedicalWorkStyleHistory> get(String empID , GeneralDate referenceDate);
	
	/** [2] 社員の医療勤務形態項目*get(List<社員ID>, 基準日) **/
	List<EmpMedicalWorkStyleHistory> get(List<String> listEmp , GeneralDate referenceDate);
	
	/** **/
	void insert();
}
