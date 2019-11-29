package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SalaryInsuColMonRepository;

@Stateless
/**
* 給与社会保険徴収月
*/
public class SalaryInsuColMonFinder
{

    @Inject
    private SalaryInsuColMonRepository finder;

    public List<SalaryInsuColMonDto> getAllSalaryInsuColMon(){
        return finder.getAllSalaryInsuColMon().stream().map(item -> SalaryInsuColMonDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
