package nts.uk.ctx.pr.shared.app.find.payrollgeneralpurposeparameters;

import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenParaValueRepository;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
/**
* 給与汎用パラメータ値: Finder
*/
public class SalGenParaValueFinder
{

    @Inject
    private SalGenParaValueRepository finder;

    public List<SalGenParaValueDto> getAllSalGenParaValue(String hisId){
        return finder.getAllSalGenParaValue().stream().map(item -> SalGenParaValueDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
