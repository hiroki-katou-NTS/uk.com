package nts.uk.ctx.at.record.pubimp.anyperiod;

import nts.uk.ctx.at.record.pub.anyperiod.GetAnyPeriodRecordPub;
import nts.uk.ctx.at.record.pub.anyperiod.AnyPeriodRecordValuesExport;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriod;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriodRepository;
import nts.uk.ctx.at.shared.dom.scherec.anyperiod.attendancetime.converter.AnyPeriodRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.service.AttendanceItemConvertFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Stateless
public class GetAnyPeriodRecordPubImpl implements GetAnyPeriodRecordPub {
    @Inject
    private AttendanceTimeOfAnyPeriodRepository attendanceTimeRepo;

    @Inject
    private AttendanceItemConvertFactory converterFactory;

    @Override
    public Map<String, AnyPeriodRecordValuesExport> getRecordValues(
            List<String> employeeIds,
            String frameCode,
            List<Integer> itemIds
    ) {
        Map<String, AnyPeriodRecordValuesExport> results = new HashMap<>();
        List<AttendanceTimeOfAnyPeriod> attendanceTimes = attendanceTimeRepo.findBySids(employeeIds, frameCode);
        Map<String, AttendanceTimeOfAnyPeriod> attendanceTimeMap = attendanceTimes.stream()
                .collect(Collectors.toMap(AttendanceTimeOfAnyPeriod::getEmployeeId, Function.identity()));
        for (String empId : employeeIds) {
            if (!attendanceTimeMap.containsKey(empId)) continue;
            AttendanceTimeOfAnyPeriod attendanceTime = attendanceTimeMap.get(empId);
            AnyPeriodRecordToAttendanceItemConverter converter = converterFactory.createOptionalItemConverter();
            converter.withAttendanceTime(attendanceTime);

            results.put(empId, AnyPeriodRecordValuesExport.of(frameCode, converter.convert(itemIds)));
        }
        return results;
    }
}
