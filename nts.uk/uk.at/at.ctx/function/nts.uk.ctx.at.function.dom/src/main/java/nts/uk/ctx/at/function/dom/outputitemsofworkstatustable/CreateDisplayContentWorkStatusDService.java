package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;


import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AttendanceResultDto;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.OperatorsCommonToForms;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
        if (listSid == null) {
            throw new BusinessException("Msg_1816");
        }
        val listEmployeeStatus = require.getListAffComHistByListSidAndPeriod(listSid, datePeriod);
        val outputItems = outputSettings.getOutputItem().stream().filter(OutputItem::isPrintTargetFlag).collect(Collectors.toList());
        val rs = new ArrayList<DisplayContentWorkStatus>();

        listEmployeeStatus.forEach(e -> {

            val item = new DisplayContentWorkStatus();
            val itemOneLines = new ArrayList<OutputItemOneLine>();
            val eplInfo = employeeInforList.stream().filter(s -> s.employeeId.equals(e.getEmployeeId())).findFirst().get();
            val wplInfo = workPlaceInfos.stream().filter(s -> s.getWorkPlaceId().equals(eplInfo.getWorkPlaceId())).findFirst().get();
            item.setInfor(new DisplayContentedEmployeeInfo(
                    eplInfo.employeeCode,
                    eplInfo.employeeName,
                    wplInfo.getWorkPlaceCode(),
                    wplInfo.getWorkPlaceName()
            ));
            outputItems.forEach(j -> {
                val itemValue = new ArrayList<DailyValue>();
                e.getListPeriod().forEach(i -> i.datesBetween().forEach(l -> {
                    val listAttendances = require.getValueOf(e.getEmployeeId(), l,
                            j.getSelectedAttendanceItemList().stream().map(OutputItemDetailSelectionAttendanceItem::getAttendanceItemId)
                                    .collect(Collectors.toList()));
                    Double actualValue = null;
                    StringBuilder character = new StringBuilder();
                    val attr = j.getItemDetailAttributes();
                    val date = l;

                    val listItem = j.getSelectedAttendanceItemList();
                    if (j.getItemDetailAttributes() == CommonAttributesOfForms.WORK_TYPE ||
                            j.getItemDetailAttributes() == CommonAttributesOfForms.WORKING_HOURS) {
                        for (OutputItemDetailSelectionAttendanceItem ite : listItem) {
                            character.append(listAttendances.getAttendanceItems().stream().
                                    filter(x -> x.getItemId() == ite.getAttendanceItemId()).findFirst().get().getValue());
                        }
                    } else {
                        for (OutputItemDetailSelectionAttendanceItem ite : listItem) {
                            if (ite.getOperator() == OperatorsCommonToForms.ADDITION)
                                actualValue += Double.parseDouble(listAttendances.getAttendanceItems().stream().
                                        filter(x -> x.getItemId() == ite.getAttendanceItemId()).findFirst().get().getValue());

                            else if (ite.getOperator() == OperatorsCommonToForms.SUBTRACTION) {
                                actualValue -= Double.parseDouble(listAttendances.getAttendanceItems().stream().
                                        filter(x -> x.getItemId() == ite.getAttendanceItemId()).findFirst().get().getValue());
                            }
                        }
                    }

                    itemValue.add(
                            new DailyValue(
                                    actualValue,
                                    attr,
                                    character.toString(),
                                    date
                            ));
                }));
                val total = itemValue.stream().mapToDouble(DailyValue::getActualValue).sum();
                itemOneLines.add(
                        new OutputItemOneLine(
                                total,
                                j.getName().v(),
                                itemValue
                        ));
                item.setOutputItemOneLines(itemOneLines);
            });
            rs.add(item);
        });
        return rs;
    }

    public interface Require {
        //[RQ 588] 社員の指定期間中の所属期間を取得する
        List<StatusOfEmployee> getListAffComHistByListSidAndPeriod(List<String> sid, DatePeriod datePeriod);

        AttendanceResultDto getValueOf(String employeeId, GeneralDate workingDate, Collection<Integer> itemIds);

    }
}
