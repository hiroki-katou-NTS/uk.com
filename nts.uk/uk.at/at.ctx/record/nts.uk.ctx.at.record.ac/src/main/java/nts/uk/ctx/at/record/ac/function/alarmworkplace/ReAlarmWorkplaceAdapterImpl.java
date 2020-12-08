package nts.uk.ctx.at.record.ac.function.alarmworkplace;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.pub.alarmworkplace.AlarmWorkplacePub;
import nts.uk.ctx.at.record.dom.adapter.function.alarmworkplace.EmployeeInfoImport;
import nts.uk.ctx.at.record.dom.adapter.function.alarmworkplace.ReAlarmWorkplaceAdapter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Stateless
public class ReAlarmWorkplaceAdapterImpl implements ReAlarmWorkplaceAdapter {

    @Inject
    private AlarmWorkplacePub alarmWorkplacePub;

    @Override
    public Map<String, List<EmployeeInfoImport>> advanceAcquisitionProcess(List<String> workplaceIds, DatePeriod period) {
        return alarmWorkplacePub.advanceAcquisitionProcess(workplaceIds, period).entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, x -> x.getValue().stream().map(c ->
                        new EmployeeInfoImport(c.getSid(), c.getEmployeeCode(), c.getEmployeeName()))
                        .collect(Collectors.toList())));
    }
}
