package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.*;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Stateless
/**
 * 給与支払日設定
 */
public class SetDaySupportFinder {

    @Inject
    private SetDaySupportRepository setDaySupportRepository;

    @Inject
    private PerProcessClsSetRepository perProcessClsSetRepository;

    @Inject
    private CurrProcessDateRepository currProcessDateRepository;


    public List<SetDaySupportDto> getAllSetDaySupport() {
        return setDaySupportRepository.getAllSetDaySupport().stream().map(item -> SetDaySupportDto.fromDomain(item))
                .collect(Collectors.toList());
    }

    public List<SetDaySupportDto> getListByCateNoAndCid(int processCateNo) {
        String cid = AppContexts.user().companyId();
        return setDaySupportRepository.getSetDaySupportById(cid, processCateNo).stream().map(item -> SetDaySupportDto.fromDomain(item)).collect(Collectors.toList());
    }

    public SetDaySupportDto getEmployeeExtractionReferenceDateByIdAndProcessDate(int processCateNo, int processDate){
        String cid = AppContexts.user().companyId();
        return SetDaySupportDto.fromDomain(setDaySupportRepository.getSetDaySupportByIdAndProcessDate(cid,processCateNo,processDate).orElse(null));
    }

    public GeneralDate getEmployeeExtractionReferenceDateByUIdAndCId() {
        String cid = AppContexts.user().companyId();
        String uid = AppContexts.user().userId();
        Optional<PerProcessClsSet> perProcessClsSet = perProcessClsSetRepository.getPerProcessClsSetByUIDAndCID(uid, cid);
        int processCateNo = perProcessClsSet.map(PerProcessClsSet::getProcessCateNo).orElse(1);
        Optional<CurrProcessDate> currProcessDate = currProcessDateRepository.getCurrProcessDateByIdAndProcessCateNo(cid, processCateNo);
        int processDate = currProcessDate.map(currProcessDate1 -> currProcessDate1.getGiveCurrTreatYear().v()).orElse(GeneralDate.today().yearMonth().v());
        Optional<SetDaySupport> setDaySupport = setDaySupportRepository.getSetDaySupportByIdAndProcessDate(cid, processCateNo, processDate);
        return setDaySupport.isPresent() ? setDaySupport.get().getEmpExtraRefeDate() : null;
    }
}
