package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomworkstlinfor;

import java.util.Optional;

/**
* 社保勤務形態履歴
*/
public interface CorWorkFormInfoRepository {
    Optional<CorWorkFormInfo> getCorWorkFormInfoByHisId(String hisId);
    Optional<CorWorkFormInfo> getCorWorkFormInfoByHisId(String empID, String hisId);
}
