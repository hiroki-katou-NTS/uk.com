package nts.uk.ctx.sys.assist.app.find.salary;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;





import nts.uk.ctx.sys.assist.dom.salary.ProcessInformationRepository;

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
