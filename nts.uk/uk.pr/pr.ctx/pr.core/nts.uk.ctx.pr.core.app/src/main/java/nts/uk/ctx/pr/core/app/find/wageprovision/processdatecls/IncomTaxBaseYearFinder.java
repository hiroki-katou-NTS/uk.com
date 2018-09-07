package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.IncomTaxBaseYearRepository;

@Stateless
/**
* 所得税基準年月日
*/
public class IncomTaxBaseYearFinder
{

    @Inject
    private IncomTaxBaseYearRepository finder;

    public List<IncomTaxBaseYearDto> getAllIncomTaxBaseYear(){
        return finder.getAllIncomTaxBaseYear().stream().map(item -> IncomTaxBaseYearDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
