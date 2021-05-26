package nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface AttendanceItemServiceAdapter {

    AttendanceResultDto getValueOf(String employeeId, GeneralDate workingDate, Collection<Integer> itemIds);

    List<AttendanceResultDto> getValueOf(List<String> employeeIds, DatePeriod workingDatePeriod, Collection<Integer> itemIds);

    Map<String, AnyPeriodRecordValuesExportDto> getRecordValues(
            List<String> employeeIds,
            String frameCode,
            List<Integer> itemIds
    );
}
