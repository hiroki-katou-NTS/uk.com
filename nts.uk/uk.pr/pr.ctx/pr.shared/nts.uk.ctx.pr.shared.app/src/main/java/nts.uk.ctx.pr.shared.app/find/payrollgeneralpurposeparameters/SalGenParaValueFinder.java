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

    public SalGenParaValueDto getAllSalGenParaValue(String hisId){
        return SalGenParaValueDto.fromDomain(finder.getSalGenParaValueById(hisId).get());
    }

}
