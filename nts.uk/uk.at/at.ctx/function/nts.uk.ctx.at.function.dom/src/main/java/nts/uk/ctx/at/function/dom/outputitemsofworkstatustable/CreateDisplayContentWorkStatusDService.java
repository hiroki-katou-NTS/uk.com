package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;

import lombok.val;
import nts.arc.time.calendar.period.DatePeriod;

import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * DomainService: 勤務状況表の表示内容を作成する
 *
 * @author chinh.hm
 */
@Stateless
public class CreateDisplayContentWorkStatusDService {
    public static List<DisplayContentWorkStatus> displayContentsOfWorkStatus(Require require,DatePeriod datePeriod,
                                                           List<EmployeeInfor> employeeInforList,
                                                           WorkStatusOutputSettings outputSettings,
                                                           List<WorkPlaceInfo> workPlaceInfos)
    {
        val listSid = employeeInforList.stream().map(e->e.employeeId).collect(Collectors.toList());
        val listEmployeeStatus = require.getListAffComHistByListSidAndPeriod(listSid,datePeriod);
        val attendanceItemId = outputSettings.getOutputItem().stream().filter(OutputItem::isPrintTargetFlag).collect(Collectors.toList());
        listEmployeeStatus.forEach(e->{

        });

        return null;
    }
    public interface Require{
        //[RQ 588] 社員の指定期間中の所属期間を取得する
        List<StatusOfEmployee> getListAffComHistByListSidAndPeriod(List<String> sid, DatePeriod datePeriod);
    }
}
