package nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AttendanceItemServiceAdapter {
     Optional<AttendanceItemDtoValue> getValueOf(String employeeId, GeneralDate workingDate, int itemId);

    /** RequestList332 */
     AttendanceResultDto getValueOf(String employeeId, GeneralDate workingDate, Collection<Integer> itemIds);

     List<AttendanceResultDto> getValueOf(Collection<String> employeeId, DatePeriod workingDate,
                                             Collection<Integer> itemIds);
}
