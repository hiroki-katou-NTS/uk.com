package nts.uk.ctx.at.function.ac.outputitemsofworkstatustable;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AttendanceItemDtoValue;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AttendanceItemServiceAdapter;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AttendanceResultDto;
import nts.uk.ctx.at.record.pub.dailyattendanceitem.AttendanceItemService;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AttendanceItemServiceAdapterImpl implements AttendanceItemServiceAdapter {
    @Inject
    private AttendanceItemService service;

    @Override
    public AttendanceResultDto getValueOf(String employeeId, GeneralDate workingDate, Collection<Integer> itemIds) {
        val result = new AttendanceResultDto();
        val rs = service.getValueOf(employeeId, workingDate, itemIds);
        if (rs != null) {
            val listsAttendanceItems = rs.getAttendanceItems().stream()
                    .map(e -> new AttendanceItemDtoValue(e.getValueType(), e.getItemId(), e.value()))
                    .collect(Collectors.toCollection(ArrayList::new));
            result.setEmployeeId(rs.getEmployeeId());
            result.setWorkingDate(rs.getWorkingDate());
            result.setAttendanceItems(listsAttendanceItems);
        }
        return result;
    }

    @Override
    public List<AttendanceResultDto> getValueOf(List<String> employeeIds, DatePeriod workingDatePeriod, Collection<Integer> itemIds) {
        val result = new ArrayList<AttendanceResultDto>();
        val rs = service.getValueOf(employeeIds, workingDatePeriod, itemIds);
        if (rs != null && !rs.isEmpty()) {
            for (val item : rs) {
                val listsAttendanceItems = item.getAttendanceItems().stream()
                        .map(e -> new AttendanceItemDtoValue(e.getValueType(), e.getItemId(), e.value()))
                        .collect(Collectors.toCollection(ArrayList::new));

                result.add(new AttendanceResultDto(item.getEmployeeId(), item.getWorkingDate(), listsAttendanceItems));
            }
        }
        return result;
    }

}
