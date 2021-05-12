package nts.uk.ctx.at.function.pubimp.alarmworkplace;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.alarmworkplace.service.aggregateprocess.advanceacquisition.AdvanceAcquisitionService;
import nts.uk.ctx.at.function.pub.alarmworkplace.AlarmWorkplacePub;
import nts.uk.ctx.at.function.pub.alarmworkplace.EmployeeInfoExport;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Stateless
public class AlarmWorkplacePubImpl implements AlarmWorkplacePub {

    @Inject
    private AdvanceAcquisitionService advanceAcquisitionService;

    @Override
    public Map<String, List<EmployeeInfoExport>> advanceAcquisitionProcess(List<String> workplaceIds, DatePeriod period) {
        return advanceAcquisitionService.process(workplaceIds, period).entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, x -> x.getValue().stream().map(c ->
                        new EmployeeInfoExport(c.getSid(), c.getEmployeeCode(), c.getEmployeeName()))
                        .collect(Collectors.toList())));
    }
}
