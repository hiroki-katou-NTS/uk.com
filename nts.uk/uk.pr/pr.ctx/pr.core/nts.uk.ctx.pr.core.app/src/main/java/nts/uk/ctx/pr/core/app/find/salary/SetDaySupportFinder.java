package nts.uk.ctx.pr.core.app.find.salary;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.salary.SetDaySupportRepository;

@Stateless
/**
* 給与支払日設定
*/
public class SetDaySupportFinder
{

    @Inject
    private SetDaySupportRepository finder;

    public List<SetDaySupportDto> getAllSetDaySupport(){
        return finder.getAllSetDaySupport().stream().map(item -> SetDaySupportDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
