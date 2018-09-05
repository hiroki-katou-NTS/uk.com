package nts.uk.ctx.sys.assist.app.find.salary;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.sys.assist.dom.salary.CloseDateRepository;

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
