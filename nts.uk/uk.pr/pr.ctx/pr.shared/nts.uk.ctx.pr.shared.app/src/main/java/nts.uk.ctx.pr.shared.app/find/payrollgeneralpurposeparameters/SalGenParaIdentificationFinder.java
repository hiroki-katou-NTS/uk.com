package nts.uk.ctx.pr.shared.app.find.payrollgeneralpurposeparameters;

import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenParaIdentificationRepository;
import nts.uk.shr.com.context.AppContexts;

import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
/**
* 給与汎用パラメータ識別: Finder
*/
public class SalGenParaIdentificationFinder
{

    @Inject
    private SalGenParaIdentificationRepository finder;

    public List<SalGenParaIdentificationDto> getAllSalGenParaIdentification(){
        String cId = AppContexts.user().companyId();
        return finder.getAllSalGenParaIdentification(cId).stream().map(item -> SalGenParaIdentificationDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
