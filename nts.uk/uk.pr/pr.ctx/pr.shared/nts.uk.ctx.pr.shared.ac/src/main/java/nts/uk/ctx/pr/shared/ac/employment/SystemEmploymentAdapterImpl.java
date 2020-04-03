package nts.uk.ctx.pr.shared.ac.employment;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.employment.SyEmploymentPub;
import nts.uk.ctx.pr.shared.dom.adapter.employment.SEmpHistImport;
import nts.uk.ctx.pr.shared.dom.adapter.employment.SystemEmploymentAdapter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class SystemEmploymentAdapterImpl implements SystemEmploymentAdapter {

    @Inject
    SyEmploymentPub syEmploymentPub;

    @Override
    public Optional<SEmpHistImport> findSEmpHistBySid(String companyId, String employeeId, GeneralDate baseDate) {
        return syEmploymentPub.findSEmpHistBySid(companyId, employeeId, baseDate)
                .map(e -> SEmpHistImport
                        .builder()
                        .employeeId(e.getEmployeeId())
                        .employmentCode(e.getEmploymentCode())
                        .employmentName(e.getEmploymentName())
                        .period(e.getPeriod())
                        .build());
    }

    @Override
    public Map<String, SEmpHistImport> findSEmpHistByListSid(String companyId, List<String> lstSID, GeneralDate baseDate) {
        return syEmploymentPub.findSEmpHistBySidVer2(companyId, lstSID, baseDate)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> SEmpHistImport
                        .builder()
                        .employeeId(e.getValue().getEmployeeId())
                        .employmentCode(e.getValue().getEmploymentCode())
                        .employmentName(e.getValue().getEmploymentName())
                        .period(e.getValue().getPeriod())
                        .build()));
    }
}
