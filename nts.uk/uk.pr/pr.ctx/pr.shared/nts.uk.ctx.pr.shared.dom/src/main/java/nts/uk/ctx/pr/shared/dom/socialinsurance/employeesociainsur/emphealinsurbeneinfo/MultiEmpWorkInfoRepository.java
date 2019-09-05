package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import java.util.Optional;

/**
* 社員二以上事業所勤務情報
*/
public interface MultiEmpWorkInfoRepository
{

    Optional<MultiEmpWorkInfo> getMultiEmpWorkInfoById(String employeeId);

    void add(MultiEmpWorkInfo domain);

    void update(MultiEmpWorkInfo domain);

    void remove(String employeeId);

}
