package nts.uk.ctx.at.function.ac.outputitemsofworkstatustable;

import lombok.val;
import nts.arc.time.GeneralDate;
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
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AttendanceItemServiceAdapterImpl implements AttendanceItemServiceAdapter {
    @Inject
    private AttendanceItemService service;

    @Override
    public AttendanceResultDto getValueOf(String employeeId, GeneralDate workingDate, Collection<Integer> itemIds) {
        val result = new AttendanceResultDto();
        val rs = service.getValueOf(employeeId,workingDate,itemIds);
        if(rs!=null){
            val listsAttendanceItems = rs.getAttendanceItems().stream()
                    .map(e -> new AttendanceItemDtoValue(e.getValueType(), e.getItemId(), e.getItemId()))
                    .collect(Collectors.toCollection(ArrayList::new));
            result.setEmployeeId(rs.getEmployeeId());
            result.setWorkingDate(rs.getWorkingDate());
            result.setAttendanceItems(listsAttendanceItems);
        }
       return result;
    }

}
