package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;

import lombok.experimental.var;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AttendanceItemDtoValue;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AttendanceResultDto;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * DomainService: 勤務状況表の表示内容を作成する
 *
 * @author chinh.hm
 */
@Stateless
public class CreateDisplayContentWorkStatusDService {
    public static List<DisplayContentWorkStatus> displayContentsOfWorkStatus(Require require,
                                                                             DatePeriod datePeriod,
                                                                             List<EmployeeInfor> employeeInforList,
                                                                             WorkStatusOutputSettings outputSettings,
                                                                             List<WorkPlaceInfo> workPlaceInfos) {
        val listSid = employeeInforList.stream().map(EmployeeInfor::getEmployeeId).collect(Collectors.toList());
        val listEmployeeStatus = require.getListAffComHistByListSidAndPeriod(listSid, datePeriod);
        val outputItems = outputSettings.getOutputItem().stream().filter(OutputItem::isPrintTargetFlag).collect(Collectors.toList());
        List<OutputItemDetailSelectionAttendanceItem> attendanceItemList = new ArrayList<>();
        outputItems.forEach(e ->
                attendanceItemList.addAll(e.getSelectedAttendanceItemList())
        );
        val attendanceItemIds = attendanceItemList.stream().map(OutputItemDetailSelectionAttendanceItem::getAttendanceItemId).collect(Collectors.toList());

        val employeeIds = listEmployeeStatus.stream().map(StatusOfEmployee::getEmployeeId).collect(Collectors.toList());
        val listAttendance = require.getValueOf(employeeIds, datePeriod, attendanceItemIds);


        val rs = new ArrayList<DisplayContentWorkStatus>();

        return rs;
    }

    public interface Require {
        //[RQ 588] 社員の指定期間中の所属期間を取得する
        List<StatusOfEmployee> getListAffComHistByListSidAndPeriod(List<String> sid, DatePeriod datePeriod);

        Optional<AttendanceItemDtoValue> getValueOf(String employeeId, GeneralDate workingDate, int itemId);

        // RequestList 332

        AttendanceResultDto getValueOf(String employeeId, GeneralDate workingDate, Collection<Integer> itemIds);

        List<AttendanceResultDto> getValueOf(Collection<String> employeeId, DatePeriod workingDate,
                                             Collection<Integer> itemIds);

        DisplayContenteEmployeeInfor getInfor(String employeeId);
    }
}
