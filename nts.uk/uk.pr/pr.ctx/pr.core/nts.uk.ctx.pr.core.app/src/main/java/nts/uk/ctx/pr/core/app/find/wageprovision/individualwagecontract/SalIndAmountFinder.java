package nts.uk.ctx.pr.core.app.find.wageprovision.individualwagecontract;

import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountRepository;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
/**
* 給与個人別金額: Finder
*/
public class SalIndAmountFinder
{

    @Inject
    private SalIndAmountRepository finder;

    public List<SalIndAmountDto> getAllSalIndAmount(){
        return finder.getAllSalIndAmount().stream().map(item -> SalIndAmountDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
