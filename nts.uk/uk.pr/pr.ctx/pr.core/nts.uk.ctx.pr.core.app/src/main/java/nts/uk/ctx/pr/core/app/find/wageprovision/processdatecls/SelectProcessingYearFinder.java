package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SetDaySupport;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SetDaySupportRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SpecPrintYmSetRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ValPayDateSetRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class SelectProcessingYearFinder {

    @Inject
    private SetDaySupportRepository setDaySupportRepository;

    @Inject
    private SpecPrintYmSetRepository specPrintYmSetRepository;


    public SelectProcessingYearDto getByCateNoAndCid(int processCateNo, int year){
        String cid = AppContexts.user().companyId();
        List<SetDaySupportDto> setDaySupportDtos = setDaySupportRepository.getSetDaySupportByIdAndYear(cid, processCateNo, year).stream().map(item -> SetDaySupportDto.fromDomain(item)).collect(Collectors.toList());
        List<SpecPrintYmSetDto> specPrintYmSetDtos = specPrintYmSetRepository.getSpecPrintYmSetByIdAndYear(cid, processCateNo, year).stream().map(item -> SpecPrintYmSetDto.fromDomain(item)).collect(Collectors.toList());
        return new SelectProcessingYearDto(setDaySupportDtos, specPrintYmSetDtos);
    }

}
