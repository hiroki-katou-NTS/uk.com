package nts.uk.ctx.at.shared.ac.temporaryabsence;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.temporaryabsence.*;
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
    public List<TempAbsenceHistoryImport> getTempAbsenceHistories(String cid, DatePeriod period, List<String> employeeIds) {
        return tempAbsencePub.getTempAbsenceHistories(cid, period, employeeIds).stream()
                .map(x -> new
                        TempAbsenceHistoryImport(
                        x.getCompanyId(),
                        x.getEmployeeId(),
                        x.getDateHistoryItems().stream().map(c ->
                                new DateHistoryItemImport(
                                        c.getHistoryId(),
                                        c.getStartDate(),
                                        c.getEndDate(),
                                        new TempAbsenceHisItemImport(
                                                c.getHistoryItem().getTempAbsenceFrNo(),
                                                c.getHistoryItem().getTempAbsenceFrName(),
                                                c.getHistoryItem().getHistoryId(),
                                                c.getHistoryItem().getEmployeeId(),
                                                c.getHistoryItem().getRemarks(),
                                                c.getHistoryItem().getSoInsPayCategory(),
                                                c.getHistoryItem().getFamilyMemberId())))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }
}
