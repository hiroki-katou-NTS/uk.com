package nts.uk.ctx.at.function.app.query.outputworkstatustable;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.auth.dom.initswitchsetting.InitDisplayPeriodSwitchSetRepo;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class GetInitDateLoginEmployeeQuery {
    @Inject
    private InitDisplayPeriodSwitchSetRepo repo;
    @Inject
    private ClosureRepository closureRepo;
    @Inject
    private ClosureEmploymentRepository closureEmploymentRepo;
    @Inject
    private ShareEmploymentAdapter shareEmploymentAdapter;
    public YearMonth getInitDateLoginEmployee(){
        val sid = AppContexts.user().employeeId();
        GeneralDate systemDate = GeneralDate.today();
        val require = ClosureService.createRequireM3(closureRepo, closureEmploymentRepo, shareEmploymentAdapter);
        // 社員に対応する処理締めを取得する
        Closure closure = ClosureService.getClosureDataByEmployee(
                require, new CacheCarrier(), sid, systemDate);
        if(closure == null){
            return systemDate.yearMonth();
        }else {
            return closure.getClosureMonth().getProcessingYm();
        }
    }
}
