package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ProcessInformation;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ProcessInformationRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
/**
 * 処理区分基本情報
 */
public class ProcessInformationFinder {

	@Inject
	private ProcessInformationRepository finder;

	public List<ProcessInformationDto> getAllProcessInformation() {
		return finder.getAllProcessInformation().stream().map(item -> ProcessInformationDto.fromDomain(item))
				.collect(Collectors.toList());
	}

	


    public ProcessInformationDto getProcessInformation(int processCateNo){
        String cid = AppContexts.user().companyId();
         Optional<ProcessInformation> processInformation=finder.getProcessInformationById(cid,processCateNo);


         return processInformation.map(
                 x->new ProcessInformationDto(
                         x.getCid(),x.getProcessCateNo(),
                         x.getDeprecatCate().value,
                         x.getProcessCls().v())
         ).orElse(null);

    }



}
