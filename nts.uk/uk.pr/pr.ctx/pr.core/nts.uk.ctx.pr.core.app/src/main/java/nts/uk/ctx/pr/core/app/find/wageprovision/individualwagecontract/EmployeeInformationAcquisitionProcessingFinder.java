package nts.uk.ctx.pr.core.app.find.wageprovision.individualwagecontract;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.CurrProcessDateDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.CurrProcessDateFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.SetDaySupportDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.SetDaySupportFinder;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.*;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;

@Stateless
public class EmployeeInformationAcquisitionProcessingFinder {

    @Inject
    private SetDaySupportRepository setDaySupportRepository;

    @Inject
    private PerProcessClsSetRepository perProcessClsSetRepository;

    @Inject
    private CurrProcessDateRepository currProcessDateRepository;

    private static final int DEFAULT_PROCESS_CATE_NO = 1;


    public ReferenceDateDto getEmpExtRefDate() {
        String cid = AppContexts.user().companyId();
        String uid = AppContexts.user().userId();
        Optional<PerProcessClsSet> perProcessClsSet = perProcessClsSetRepository.getPerProcessClsSetByUIDAndCID(uid, cid);
        int processCateNo = perProcessClsSet.map(PerProcessClsSet::getProcessCateNo).orElse(DEFAULT_PROCESS_CATE_NO);
        Optional<CurrProcessDate> currProcessDate = currProcessDateRepository.getCurrProcessDateByIdAndProcessCateNo(cid, processCateNo);
        int processDate = currProcessDate.map(currProcessDate1 -> currProcessDate1.getGiveCurrTreatYear().v()).orElse(GeneralDate.today().yearMonth().v());
        Optional<SetDaySupport> setDaySupport = setDaySupportRepository.getSetDaySupportByIdAndProcessDate(cid, processCateNo, processDate);
        return new ReferenceDateDto(
                currProcessDate.isPresent() ? currProcessDate.get().getGiveCurrTreatYear().v() : GeneralDate.today().yearMonth().v(),
                setDaySupport.isPresent()? setDaySupport.get().getEmpExtraRefeDate() : GeneralDate.today());
    }
}
