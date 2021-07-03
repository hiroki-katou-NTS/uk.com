package nts.uk.ctx.at.shared.app.command.scherec.workregistration.find;

import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskmaster.TaskingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class WorkInformationFinder {
    @Inject
    TaskingRepository repo;

//    public List<WorkInformationDto> get(TaskFrameNo frameNo, TaskCode code) {
//        String cid = AppContexts.user().companyId();
//        return repo.getListTask(cid, frameNo, code).stream().map(e -> new WorkInformationDto(
//                cid,
//                e.getTaskFrameNo().v(),
//                e.getCode().v()
//        )).collect(Collectors.toList());
//    }
}
