package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ProcessInformationRepository;

@Stateless
/**
* 処理区分基本情報
*/
public class ProcessInformationFinder
{

    @Inject
    private ProcessInformationRepository finder;

    public List<ProcessInformationDto> getAllProcessInformation(){
        return finder.getAllProcessInformation().stream().map(item -> ProcessInformationDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
