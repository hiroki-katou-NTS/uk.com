package nts.uk.ctx.at.shared.app.find.scherec.taskmanagement.taskassign.taskassignemployee;

import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskassign.taskassingemployee.TaskAssignEmployeeRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class TaskAssignEmployeeFinder {
    @Inject
    private TaskAssignEmployeeRepository repo;

    public List<TaskAssignEmployeeDto> get(int taskFrameNo, String taskCode) {
        String companyId = AppContexts.user().companyId();
        return repo.get(companyId, taskFrameNo, taskCode).stream().map(i -> new TaskAssignEmployeeDto(
                i.getEmployeeId(),
                i.getTaskFrameNo().v(),
                i.getTaskCode().v()
        )).collect(Collectors.toList());
    }

    public List<String> getAlreadySetList(int taskFrameNo) {
        String companyId = AppContexts.user().companyId();
        Set<String> taskCodes = repo.getAll(companyId, taskFrameNo).stream().map(i -> i.getTaskCode().v()).collect(Collectors.toSet());
        return new ArrayList<>(taskCodes);
    }
}
