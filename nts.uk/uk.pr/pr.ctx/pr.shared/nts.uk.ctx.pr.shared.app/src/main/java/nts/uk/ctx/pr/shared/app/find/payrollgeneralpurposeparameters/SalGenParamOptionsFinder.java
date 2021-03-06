package nts.uk.ctx.pr.shared.app.find.payrollgeneralpurposeparameters;

import nts.uk.ctx.pr.shared.dom.salgenpurposeparam.SalGenParamOptionsRepository;
import nts.uk.shr.com.context.AppContexts;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
/**
* 給与汎用パラメータ選択肢: Finder
*/
public class SalGenParamOptionsFinder
{

    @Inject
    private SalGenParamOptionsRepository finder;

    public List<SalGenParamOptionsDto> getAllSalGenParamOptions(String paraNo){
        String cId = AppContexts.user().companyId();
        return finder.getAllSalGenParamOptions(paraNo,cId).stream().map(item -> SalGenParamOptionsDto.fromDomain(item))
                .collect(Collectors.toList());
    }


}
