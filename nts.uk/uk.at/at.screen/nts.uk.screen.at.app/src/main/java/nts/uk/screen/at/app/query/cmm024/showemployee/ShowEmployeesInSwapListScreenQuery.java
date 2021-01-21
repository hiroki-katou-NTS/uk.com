package nts.uk.screen.at.app.query.cmm024.showemployee;


import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.bs.employee.dom.workplace.group.domainservice.EmployeeInfoData;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Screen F: スワップリストに社員を表示する
 */
@Stateless
public class ShowEmployeesInSwapListScreenQuery {
    @Inject
    private WorkplacePub pub;

    public List<EmployeeInfoData> getEmployees(String workplaceId, GeneralDate baseDate) {
        val datePeriod = new DatePeriod(baseDate, GeneralDate.max());
        val data = pub.getLstEmpByWorkplaceIdsAndPeriod(Arrays.asList(workplaceId),
                datePeriod);
        if (data.isEmpty()) {
            return Collections.emptyList();
        }
        return data.stream()
                .map(c -> new EmployeeInfoData(c.getSid(), c.getEmployeeCode(), c.getEmployeeName()))
                .collect(Collectors.toList());
    }
}
