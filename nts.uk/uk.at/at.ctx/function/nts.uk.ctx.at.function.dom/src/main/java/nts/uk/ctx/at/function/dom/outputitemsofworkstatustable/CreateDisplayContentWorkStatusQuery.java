package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;


import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AttendanceItemDtoValue;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AttendanceResultDto;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.CodeNameInfoDto;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.EmployeeInfor;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.StatusOfEmployee;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.WorkPlaceInfo;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.OperatorsCommonToForms;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Query: 勤務状況表の表示内容を作成する
 *
 * @author chinh.hm
 */
@Stateless
public class CreateDisplayContentWorkStatusQuery {
    private static final int WORK_TYPE = 1;

    private static final int WORKING_HOURS = 2;

    public static List<DisplayContentWorkStatus> displayContentsOfWorkStatus(Require require,
                                                                             DatePeriod datePeriod,
                                                                             List<EmployeeInfor> employeeInfoList,
                                                                             WorkStatusOutputSettings outputSettings,
                                                                             List<WorkPlaceInfo> workPlaceInfo) {
        if (outputSettings == null) {
            throw new BusinessException("Msg_1816");
        }
        val listSid = employeeInfoList.stream().map(EmployeeInfor::getEmployeeId).distinct().collect(Collectors.toList());
        val listEmployeeStatus = require.getListAffComHistByListSidAndPeriod(listSid, datePeriod)
                .stream().filter(distinctByKey(StatusOfEmployee::getEmployeeId)).collect(Collectors.toList());
        val mapSids = employeeInfoList.stream().filter(distinctByKey(EmployeeInfor::getEmployeeId)).collect(Collectors.toMap(EmployeeInfor::getEmployeeId, e -> e));

        val mapWrps = workPlaceInfo.stream().filter(distinctByKey(WorkPlaceInfo::getWorkPlaceId)).collect(Collectors.toMap(WorkPlaceInfo::getWorkPlaceId, e -> e));

        val rs = new ArrayList<DisplayContentWorkStatus>();

        val outputItems = outputSettings.getOutputItem().stream().filter(OutputItem::isPrintTargetFlag)
                .collect(Collectors.toList());
        if (outputItems.isEmpty()) {
            throw new BusinessException("Msg_1816");
        }
        val cid = AppContexts.user().companyId();
        val listIds = outputItems.stream()
                .flatMap(x -> x.getSelectedAttendanceItemList().stream()
                        .map(OutputItemDetailAttItem::getAttendanceItemId))
                .distinct().collect(Collectors.toCollection(ArrayList::new));
        val listEmployee = listEmployeeStatus.stream()
                .map(StatusOfEmployee::getEmployeeId).collect(Collectors.toList());
        Map<Integer, Map<String, CodeNameInfoDto>> allDataMaster = require.getAllDataMaster(cid, datePeriod.end(), listIds);

        List<AttendanceResultDto> listItemValue = require.getValueOf(listEmployee, datePeriod, listIds)
                .stream().filter(distinctByKey(AttendanceResultDto::getEmployeeId)).collect(Collectors.toList());
        Map<String, Map<GeneralDate, Map<Integer, AttendanceItemDtoValue>>> mapValue = new HashMap<>();
        for (AttendanceResultDto attendanceResultDto : listItemValue) {
            Map<Integer, AttendanceItemDtoValue> allValue = attendanceResultDto.getAttendanceItems().stream()
                    .filter(distinctByKey(AttendanceItemDtoValue::getItemId))
                    .collect(Collectors.toMap(AttendanceItemDtoValue::getItemId, l -> l));
            Map<GeneralDate, Map<Integer, AttendanceItemDtoValue>> mapDateAndItem = new HashMap<>();
            mapDateAndItem.put(attendanceResultDto.getWorkingDate(), allValue);
            mapValue.put(attendanceResultDto.getEmployeeId(), mapDateAndItem);
        }
        for (StatusOfEmployee e : listEmployeeStatus) {
            val item = new DisplayContentWorkStatus();
            val eplInfo = mapSids.get(e.getEmployeeId());
            if (eplInfo == null) {
                continue;
            } else {
                item.setEmployeeCode(eplInfo.getEmployeeCode());
                item.setEmployeeName(eplInfo.getEmployeeName());
                val wplInfo = mapWrps.get(eplInfo.getWorkPlaceId());
                if (wplInfo == null) {
                    continue;
                } else {
                    item.setWorkPlaceCode(wplInfo.getWorkPlaceCode());
                    item.setWorkPlaceName(wplInfo.getWorkPlaceName());
                }
            }
            List<GeneralDate> listDate = e.getListPeriod()
                    .stream()
                    .flatMap(y -> y.datesBetween().stream().map(q -> GeneralDate.legacyDate(q.date())))
                    .collect(Collectors.toList());
            Map<GeneralDate, Map<Integer, AttendanceItemDtoValue>> allValue = mapValue
                    .getOrDefault(e.getEmployeeId(), null);
            val itemOneLines = new ArrayList<OutputItemOneLine>();
            for (OutputItem j : outputItems) {
                val itemValue = new ArrayList<DailyValue>();
                for (GeneralDate l : listDate) {
                    val vl = allValue.getOrDefault(l, null);
                    if (vl == null) continue;
                    val listAtId = j.getSelectedAttendanceItemList();
                    StringBuilder character = new StringBuilder();
                    Double actualValue = 0D;
                    boolean alwaysNull = true;
                    if (j.getItemDetailAttributes() == CommonAttributesOfForms.WORK_TYPE ||
                            j.getItemDetailAttributes() == CommonAttributesOfForms.WORKING_HOURS
                            || j.getItemDetailAttributes() == CommonAttributesOfForms.OTHER_CHARACTER_NUMBER
                            || j.getItemDetailAttributes() == CommonAttributesOfForms.OTHER_CHARACTERS) {
                        for (val d : listAtId) {
                            val sub = vl.getOrDefault(d.getAttendanceItemId(), null);
                            if (sub == null || sub.getValue() == null) continue;
                            val master = j.getItemDetailAttributes() == CommonAttributesOfForms.WORK_TYPE ?
                                    allDataMaster.getOrDefault(WORK_TYPE, null)
                                    : allDataMaster.getOrDefault(WORKING_HOURS, null);

                            val name = master != null ? master.getOrDefault(sub.getValue(), null) : null;
                            if (name == null) continue;
                            character.append("").append(name);
                        }
                        itemValue.add(
                                new DailyValue(
                                        null,
                                        j.getItemDetailAttributes(),
                                        character.toString(),
                                        l
                                ));
                    } else {
                        for (OutputItemDetailAttItem d : listAtId) {
                            val sub = vl.getOrDefault(d.getAttendanceItemId(), null);
                            if (sub == null || sub.getValue() == null) continue;
                            alwaysNull = false;
                            actualValue = actualValue + ((d.getOperator() == OperatorsCommonToForms.ADDITION ? 1 : -1) *
                                    Double.parseDouble(sub.getValue()));
                        }
                        itemValue.add(new DailyValue(
                                alwaysNull ? null : actualValue,
                                j.getItemDetailAttributes(),
                                character.toString(),
                                l
                        ));
                    }
                }
                val total = itemValue.stream().filter(q -> q.getActualValue() != null)
                        .mapToDouble(DailyValue::getActualValue).sum();
                itemOneLines.add(
                        new OutputItemOneLine(
                                total,
                                j.getName().v(),
                                itemValue
                        ));
                item.setOutputItemOneLines(itemOneLines);
            }
            rs.add(item);
        }

        if (rs.isEmpty()) {
            throw new BusinessException("Msg_1816");
        }

        return rs;
    }

    public interface Require {
        //[RQ 588] 社員の指定期間中の所属期間を取得する
        List<StatusOfEmployee> getListAffComHistByListSidAndPeriod(List<String> sid, DatePeriod datePeriod);

        List<AttendanceResultDto> getValueOf(List<String> employeeIds, DatePeriod workingDatePeriod, Collection<Integer> itemIds);

        Map<Integer, Map<String, CodeNameInfoDto>> getAllDataMaster(String companyId, GeneralDate dateReference,
                                                                    List<Integer> lstDivNO);
    }

    public static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
