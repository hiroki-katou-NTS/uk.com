package nts.uk.ctx.bs.employee.pubimp.temporaryabsence;


import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsHistRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsItemRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHisItem;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHistory;
import nts.uk.ctx.bs.employee.pub.temporaryabsence.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class TempAbsencePubImpl implements TempAbsencePub {

    @Inject
    private TempAbsHistRepository tempAbsHistRepo;

    @Inject
    private TempAbsItemRepository tempAbsItemRepo;

    @Override
    public TempAbsenceExport getListAffWkpHistItem(DatePeriod period, List<String> employeeIds) {

        List<TempAbsenceHistory> tempAbsMap = tempAbsHistRepo.getByListSid(employeeIds, period);

        List<String> historyIds = new ArrayList<>();
        List<EmployeeLeaveHistoryExport> leaveHists = tempAbsMap.stream().map(x ->
            new EmployeeLeaveHistoryExport(
                x.getCompanyId(),
                x.getEmployeeId(),
                x.getDateHistoryItems().stream().map(i -> {
                    historyIds.add(i.identifier());
                    return new DateHistoryItemExport(i.identifier(), i.start(), i.end());
                }).collect(Collectors.toList())
            )).collect(Collectors.toList());

        List<TempAbsenceHisItem> mapTempAbsItemMap = tempAbsItemRepo.getItemByHitoryIdList(historyIds);
        List<TempAbsenceHisItemDto> leaveHistItems = mapTempAbsItemMap.stream().map(x ->
            new TempAbsenceHisItemDto(
                x.getTempAbsenceFrNo().v().intValue(),
                x.getHistoryId(),
                x.getEmployeeId(),
                x.getRemarks().v(),
                x.getSoInsPayCategory(),
                x.getFamilyMemberId()
            )).collect(Collectors.toList());

        return new TempAbsenceExport(leaveHists, leaveHistItems);
    }
}
