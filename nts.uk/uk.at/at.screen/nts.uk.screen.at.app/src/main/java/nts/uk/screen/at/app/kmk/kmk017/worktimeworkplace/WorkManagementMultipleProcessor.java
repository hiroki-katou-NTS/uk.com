package nts.uk.screen.at.app.kmk.kmk017.worktimeworkplace;

import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultiple;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultipleRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class WorkManagementMultipleProcessor {

    @Inject
    private WorkManagementMultipleRepository workMultipleRepository;

    public WorkManagementMultipleDto find() {

        //1: get(ログイン会社ID) : 複数回勤務管理
        Optional<WorkManagementMultiple> workMultiple = workMultipleRepository.findByCode(AppContexts.user().companyId());

        return new WorkManagementMultipleDto(workMultiple.isPresent()? workMultiple.get().getUseATR().value : null);
    }
}
