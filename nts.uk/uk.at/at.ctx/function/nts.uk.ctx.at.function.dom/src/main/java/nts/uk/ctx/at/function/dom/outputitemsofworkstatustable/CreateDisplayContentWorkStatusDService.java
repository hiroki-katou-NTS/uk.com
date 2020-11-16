package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;


import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AttendanceResultDto;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.EmployeeInfor;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.StatusOfEmployee;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.WorkPlaceInfo;
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
                                                                             List<EmployeeInfor> employeeInfoList,
                                                                             WorkStatusOutputSettings outputSettings,
                                                                             List<WorkPlaceInfo> workPlaceInfo) {
        val listSid = employeeInfoList.parallelStream().map(EmployeeInfor::getEmployeeId).collect(Collectors.toList());
        val listEmployeeStatus = require.getListAffComHistByListSidAndPeriod(listSid, datePeriod);

        if (listEmployeeStatus == null) {
            throw new BusinessException("Msg_1816");
        }
        val rs = new ArrayList<DisplayContentWorkStatus>();
        val outputItems = outputSettings.getOutputItem().parallelStream().filter(OutputItem::isPrintTargetFlag)
                .collect(Collectors.toList());
        listEmployeeStatus.parallelStream().forEach(e->{

            val item = new DisplayContentWorkStatus();
            val itemOneLines = new ArrayList<OutputItemOneLine>();
            val eplInfo = employeeInfoList.parallelStream().filter(s -> s.getEmployeeId().equals(e.getEmployeeId())).findFirst();
            if (eplInfo.isPresent()) {
                item.setEmployeeCode(eplInfo.get().getEmployeeCode());
                item.setEmployeeName(eplInfo.get().getEmployeeName());
                val wplInfo = workPlaceInfo.parallelStream().filter(s -> s.getWorkPlaceId().equals(eplInfo.get()
                        .getWorkPlaceId())).findFirst();
                if (wplInfo.isPresent()) {
                    item.setWorkPlaceCode(wplInfo.get().getWorkPlaceCode());
                    item.setWorkPlaceName(wplInfo.get().getWorkPlaceName());
                }else return ;
            } else return;
            for (val j : outputItems) {
                val itemValue = new ArrayList<DailyValue>();
                e.getListPeriod().forEach(i -> i.datesBetween().forEach(l -> {
                    val listAtId = j.getSelectedAttendanceItemList().parallelStream()
                            .map(OutputItemDetailAttItem::getAttendanceItemId)
                            .collect(Collectors.toList());
                    val listAttendances = require.getValueOf(e.getEmployeeId(), l, listAtId);
                    Double actualValue = 0D;
                    String character = "";
                    val attr = j.getItemDetailAttributes();
                    val listItem = j.getSelectedAttendanceItemList();
                    if (j.getItemDetailAttributes() == CommonAttributesOfForms.WORK_TYPE ||
                            j.getItemDetailAttributes() == CommonAttributesOfForms.WORKING_HOURS) {
                        for (val ite : listItem) {
                            val subItem = (listAttendances.getAttendanceItems().parallelStream().
                                    filter(x -> x.getItemId() == ite.getAttendanceItemId()).findFirst());
                            if (subItem.isPresent()) {
                                character += (subItem.get().getValue());
                            }
                        }
                    } else {
                        for (val ite : listItem) {
                            if (ite.getOperator() == OperatorsCommonToForms.ADDITION) {
                                val subItem = (listAttendances.getAttendanceItems().parallelStream().
                                        filter(x -> x.getItemId() == ite.getAttendanceItemId()).findFirst());
                                if (subItem.isPresent())
                                    actualValue += Double.parseDouble(subItem.get().getValue());
                            } else if (ite.getOperator() == OperatorsCommonToForms.SUBTRACTION) {
                                val subItem = (listAttendances.getAttendanceItems().parallelStream().
                                        filter(x -> x.getItemId() == ite.getAttendanceItemId()).findFirst());
                                if (subItem.isPresent())
                                    actualValue -= Double.parseDouble(subItem.get().getValue());
                            }
                        }
                    }
                    itemValue.add(
                            new DailyValue(
                                    actualValue,
                                    attr,
                                    character,
                                    l
                            ));
                }));
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
