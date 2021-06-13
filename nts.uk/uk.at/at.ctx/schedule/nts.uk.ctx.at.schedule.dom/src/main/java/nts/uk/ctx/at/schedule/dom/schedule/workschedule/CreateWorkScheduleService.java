package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

/**
 * @author anhnm
 *
 */
@Stateless
public class CreateWorkScheduleService {
    
    public List<ResultOfRegisteringWorkSchedule> register(Require require, ScheduleRegister param) {
        List<ResultOfRegisteringWorkSchedule> resultOfRegisteringWorkSchedule = new ArrayList<ResultOfRegisteringWorkSchedule>();
        // 作る(Require, 社員ID, 年月日, シフトマスタ取り込みコード, boolean)
        param.getTargets().forEach(x -> {
            resultOfRegisteringWorkSchedule.add(CreateWorkScheduleByImportCode.create(
                    require, 
                    x.getEmployeeId(), 
                    x.getDate(), 
                    x.getImportCode(), 
                    param.isOverWrite()));
        });
        
        return resultOfRegisteringWorkSchedule;
    }
    

    public interface Require extends CreateWorkScheduleByImportCode.Require {
        
    };
}
