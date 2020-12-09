package nts.uk.ctx.at.shared.ac.temporaryabsence;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.temporaryabsence.*;
import nts.uk.ctx.bs.employee.pub.temporaryabsence.TempAbsenceExport;
import nts.uk.ctx.bs.employee.pub.temporaryabsence.TempAbsencePub;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class SharedTempAbsenceAdapterImpl implements SharedTempAbsenceAdapter {

    @Inject
    private TempAbsencePub tempAbsencePub;

    @Override
    public TempAbsenceImport getTempAbsence(String cid, DatePeriod period, List<String> employeeIds) {
        TempAbsenceExport tempAbsence = tempAbsencePub.getTempAbsence(cid, period, employeeIds);
        return new TempAbsenceImport(
                tempAbsence.getHistories().stream().map(x ->
                        new TempAbsenceHistoryImport(
                                x.getCompanyId(),
                                x.getEmployeeId(),
                                x.getDateHistoryItems().stream().map(c ->
                                        new DateHistoryItemImport(
                                                c.getHistoryId(),
                                                c.getStartDate(),
                                                c.getEndDate()))
                                        .collect(Collectors.toList())))
                        .collect(Collectors.toList()),
                tempAbsence.getHistoryItem().stream().map(x ->
                        new TempAbsenceHisItemImport(
                                x.getTempAbsenceFrNo(),
                                x.getTempAbsenceFrName(),
                                x.getHistoryId(),
                                x.getEmployeeId(),
                                x.getRemarks(),
                                x.getSoInsPayCategory(),
                                x.getFamilyMemberId()))
                        .collect(Collectors.toList()));
    }
}
