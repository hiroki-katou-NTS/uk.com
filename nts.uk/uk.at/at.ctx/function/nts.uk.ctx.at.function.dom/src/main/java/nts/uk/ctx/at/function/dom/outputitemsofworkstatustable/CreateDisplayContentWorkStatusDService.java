package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;

import nts.arc.time.calendar.period.DatePeriod;

import javax.ejb.Stateless;
import java.util.List;

/**
 * DomainService: 勤務状況表の表示内容を作成する
 *
 * @author chinh.hm
 */
@Stateless
public class CreateDisplayContentWorkStatusDService {
    public static List<Object> displayContentsOfWorkStatus(DatePeriod datePeriod,
                                                           List<EmployeeInfor> employeeInforList,
                                                           WorkStatusOutputSettings outputSettings,
                                                           List<WorkPlaceInfo> workPlaceInfos)
    {

        return null;
    }
}
