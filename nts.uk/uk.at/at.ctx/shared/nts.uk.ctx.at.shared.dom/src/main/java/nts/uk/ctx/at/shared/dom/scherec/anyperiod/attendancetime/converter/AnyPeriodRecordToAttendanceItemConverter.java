package nts.uk.ctx.at.shared.dom.scherec.anyperiod.attendancetime.converter;

import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AnyPeriodRecordToAttendanceItemConverter extends AttendanceItemConverter {
    AnyPeriodRecordToAttendanceItemConverter completed();

    AnyPeriodRecordToAttendanceItemConverter withBase(String employeeId);

    AnyPeriodRecordToAttendanceItemConverter withAttendanceTime(AttendanceTimeOfAnyPeriod domain);

    List<ItemValue> convert(Collection<Integer> attendanceItemIds);

    Optional<AttendanceTimeOfAnyPeriod> toAttendanceTime();
}
