package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;


import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AttendanceItemDtoValue;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AttendanceResultDto;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.EmployeeInfor;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.StatusOfEmployee;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.WorkPlaceInfo;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.OperatorsCommonToForms;

import javax.ejb.Stateless;
import java.util.*;
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
                                                                             List<EmployeeInfor> employeeInfoList,
                                                                             WorkStatusOutputSettings outputSettings,
                                                                             List<WorkPlaceInfo> workPlaceInfo) {
        val listSid = employeeInfoList.parallelStream().map(EmployeeInfor::getEmployeeId).collect(Collectors.toList());
        val listEmployeeStatus = require.getListAffComHistByListSidAndPeriod(listSid, datePeriod);

        val mapSids = employeeInfoList.parallelStream().collect(Collectors.toMap(EmployeeInfor::getEmployeeId, e -> e));

        val mapWrps = workPlaceInfo.parallelStream().collect(Collectors.toMap(WorkPlaceInfo::getWorkPlaceId, e -> e));

        val rs = new ArrayList<DisplayContentWorkStatus>();

        val outputItems = outputSettings.getOutputItem().parallelStream().filter(OutputItem::isPrintTargetFlag)
                .collect(Collectors.toList());

        listEmployeeStatus.parallelStream().forEach(e -> {


            val item = new DisplayContentWorkStatus();
            val eplInfo = mapSids.get(e.getEmployeeId());
            if (eplInfo != null) {
                item.setEmployeeCode(eplInfo.getEmployeeCode());
                item.setEmployeeName(eplInfo.getEmployeeName());
                val wplInfo = mapWrps.get(eplInfo.getWorkPlaceId());
                if (wplInfo != null) {
                    item.setWorkPlaceCode(wplInfo.getWorkPlaceCode());
                    item.setWorkPlaceName(wplInfo.getWorkPlaceName());
                } else return;
            } else return;
            val listIds = outputItems.stream()
                    .flatMap(x -> x.getSelectedAttendanceItemList().stream()
                            .map(OutputItemDetailAttItem::getAttendanceItemId))
                    .distinct().collect(Collectors.toCollection(ArrayList::new));

            List<AttendanceResultDto> listAttendancesz = new ArrayList<>();
            for (val date : e.getListPeriod()) {
                val listValue = require.getValueOf(Collections.singletonList(e.getEmployeeId()), date, listIds);
                if (listValue == null) continue;
                listAttendancesz.addAll(listValue);
            }
            Map<GeneralDate, Map<Integer, AttendanceItemDtoValue>> allValue = listAttendancesz.stream()
                    .collect(Collectors.toMap(AttendanceResultDto::getWorkingDate,
                            k -> k.getAttendanceItems().stream()
                                    .collect(Collectors.toMap(AttendanceItemDtoValue::getItemId, l -> l))));
            val itemOneLines = new ArrayList<OutputItemOneLine>();
            for (val j : outputItems) {
                val itemValue = new ArrayList<DailyValue>();
                allValue.forEach((key, value1) -> {
                    val listAtId = j.getSelectedAttendanceItemList();

                    StringBuilder character = new StringBuilder();
                    Double actualValue = 0D;
                    if (j.getItemDetailAttributes() == CommonAttributesOfForms.WORK_TYPE ||
                            j.getItemDetailAttributes() == CommonAttributesOfForms.WORKING_HOURS) {
                        for (val d : listAtId) {
                            val sub = value1.getOrDefault(d.getAttendanceItemId(), null);
                            if (sub == null || sub.getValue() == null) continue;
                            character.append(sub.getValue());
                        }
                        itemValue.add(
                                new DailyValue(
                                        actualValue,
                                        j.getItemDetailAttributes(),
                                        character.toString(),
                                        key
                                ));
                    } else {
                        for (val d : listAtId) {
                            val sub = value1.getOrDefault(d.getAttendanceItemId(), null);
                            if (sub == null || sub.getValue() == null) continue;
                            actualValue = actualValue + ((d.getOperator() == OperatorsCommonToForms.ADDITION ? 1 : -1) *
                                    Double.parseDouble(sub.getValue()));
                        }
                        itemValue.add(new DailyValue(
                                actualValue,
                                j.getItemDetailAttributes(),
                                character.toString(),
                                key
                        ));
                    }


                });
                val total = itemValue.parallelStream().filter(q -> q.getActualValue() != null)
                        .mapToDouble(DailyValue::getActualValue).sum();
                itemOneLines.add(
                        new OutputItemOneLine(
                                total,
                                j.getName().v(),
                                itemValue
                        ));
                item.setOutputItemOneLines(itemOneLines);
            }
            System.out.println(item.getEmployeeCode() + item.getWorkPlaceName());
            rs.add(item);
        });

        if (rs.isEmpty()) {
            throw new BusinessException("Msg_1816");
        }

        return rs;
    }

    public interface Require {
        //[RQ 588] 社員の指定期間中の所属期間を取得する
        List<StatusOfEmployee> getListAffComHistByListSidAndPeriod(List<String> sid, DatePeriod datePeriod);

        List<AttendanceResultDto> getValueOf(List<String> employeeIds, DatePeriod workingDatePeriod, Collection<Integer> itemIds);
    }
}
