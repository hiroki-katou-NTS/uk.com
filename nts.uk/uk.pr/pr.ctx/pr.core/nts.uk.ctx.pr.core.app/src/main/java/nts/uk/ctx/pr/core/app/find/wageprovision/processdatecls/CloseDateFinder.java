package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.CloseDateRepository;

@Stateless
/**
* 勤怠締め年月日
*/
public class CloseDateFinder
{

    @Inject
    private CloseDateRepository finder;

    public List<CloseDateDto> getAllCloseDate(){
        return finder.getAllCloseDate().stream().map(item -> CloseDateDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
