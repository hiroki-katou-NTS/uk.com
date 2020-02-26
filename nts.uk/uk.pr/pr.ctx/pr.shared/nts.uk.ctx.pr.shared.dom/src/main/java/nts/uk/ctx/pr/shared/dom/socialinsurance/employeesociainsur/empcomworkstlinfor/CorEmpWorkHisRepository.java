package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomworkstlinfor;


import java.util.Optional;

/**
* 社保勤務形態履歴
*/
public interface CorEmpWorkHisRepository {

    Optional<CorEmpWorkHis> getAllCorEmpWorkHisByEmpId(String empId);
}
