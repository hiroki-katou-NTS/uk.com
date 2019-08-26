package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import java.util.Optional;
import java.util.List;

/**
* 健康保険喪失時情報
*/
public interface HealthInsLossInfoRepository {
    Optional<HealthInsLossInfo> getHealthInsLossInfoById(String empId);
    void insert(HealthInsLossInfo healthInsLossInfo);
    void  update(HealthInsLossInfo healthInsLossInfo);
}
