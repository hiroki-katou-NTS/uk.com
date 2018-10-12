package nts.uk.ctx.pr.core.app.find.wageprovision.salaryindividualamountname;

import nts.uk.ctx.pr.core.dom.wageprovision.salaryindividualamountname.SalIndAmountNameRepository;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
/**
* 給与個人別金額名称: Finder
*/
public class SalIndAmountNameFinder
{

    @Inject
    private SalIndAmountNameRepository finder;

    public List<SalIndAmountNameDto> getAllSalIndAmountName(){
        return finder.getAllSalIndAmountName().stream().map(item -> SalIndAmountNameDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
