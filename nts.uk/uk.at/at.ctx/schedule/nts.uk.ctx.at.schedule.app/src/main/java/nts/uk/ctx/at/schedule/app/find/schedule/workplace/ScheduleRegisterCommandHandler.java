package nts.uk.ctx.at.schedule.app.find.schedule.workplace;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.schedule.app.command.schedule.workplace.ScheduleRegisterCommand;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.CreateWorkScheduleService;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ResultOfRegisteringWorkSchedule;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;

/**
 * @author anhnm
 *
 */
@Stateless
public class ScheduleRegisterCommandHandler {
    
    @Inject
    private CreateWorkScheduleService createWorkScheduleService;
    
    @Inject
    private EmpEmployeeAdapter empEmployeeAdapter;

    public List<RegisterWorkScheduleOutput> register(ScheduleRegisterCommand command) {
        List<RegisterWorkScheduleOutput> outputs = new ArrayList<RegisterWorkScheduleOutput>();
        
        // 1: 作る(Require, 社員ID, 年月日, シフトマスタ取り込みコード, boolean)
        List<ResultOfRegisteringWorkSchedule> resultOfRegisteringWorkSchedule = createWorkScheduleService.register(command.toDomain());
        
        // 2: List<勤務予定の登録処理結果> : anyMatch $.エラーがあるか == true 社員IDを指定して社員を取得する(List<社員ID>)
        List<String> employeeIds = new ArrayList<String>();
        resultOfRegisteringWorkSchedule.stream().forEach(x -> {
            if (x.isHasError()) {
                employeeIds.add(x.getErrorInformation().get(0).getEmployeeId());
            }
        });
        List<EmployeeImport> employeeImports = empEmployeeAdapter.findByEmpId(employeeIds);
        employeeImports.stream().forEach(x -> {
            ResultOfRegisteringWorkSchedule result = resultOfRegisteringWorkSchedule.stream()
                    .filter(y -> y.getErrorInformation().get(0).getEmployeeId().equals(x.getEmployeeId())).findFirst().get();
            RegisterWorkScheduleOutput output = new RegisterWorkScheduleOutput(
                    x.getEmployeeCode(), 
                    x.getEmployeeName(), 
                    result.getErrorInformation().get(0).getDate().toString("yyyy/MM/dd"), 
                    result.getErrorInformation().get(0).getAttendanceItemId().isPresent() ? result.getErrorInformation().get(0).getAttendanceItemId().get() : null, 
                    result.getErrorInformation().get(0).getErrorMessage());
            
            outputs.add(output);
        });
        
        if (outputs.size() > 0) {
            return outputs;
        }
        // 3: <<call>>
        resultOfRegisteringWorkSchedule.forEach(result -> {
            Optional<AtomTask> atomTaskOpt = result.getAtomTask();
            
            if (atomTaskOpt.isPresent()) {
                atomTaskOpt.get().run();
            }
        });
        
        return outputs;
    }
}
