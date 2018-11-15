package nts.uk.ctx.pr.core.app.find.wageprovision.salaryindividualamountname;

import nts.uk.ctx.pr.core.dom.wageprovision.salaryindividualamountname.SalIndAmountName;
import nts.uk.ctx.pr.core.dom.wageprovision.salaryindividualamountname.SalIndAmountNameRepository;
import nts.uk.shr.com.context.AppContexts;

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

    public List<SalIndAmountNameDto> getAllSalIndAmountNameByCateIndi(int cateIndicator){
        String cid = AppContexts.user().companyId();
        return finder.getAllSalIndAmountNameByCateIndi(cid, cateIndicator).stream().map(item -> SalIndAmountNameDto.fromDomain(item)).collect(Collectors.toList());
    }
}
