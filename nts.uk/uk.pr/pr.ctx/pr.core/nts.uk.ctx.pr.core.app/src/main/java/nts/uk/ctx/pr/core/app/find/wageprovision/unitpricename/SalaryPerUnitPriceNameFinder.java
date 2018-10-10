package nts.uk.ctx.pr.core.app.find.wageprovision.unitpricename;

import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPriceNameRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
/**
* 給与個人単価名称: Finder
*/
public class SalaryPerUnitPriceNameFinder
{

    @Inject
    private SalaryPerUnitPriceNameRepository finder;

    public List<SalaryPerUnitPriceNameDto> getAllSalaryPerUnitPriceName(){
        return finder.getAllSalaryPerUnitPriceName().stream().map(item -> SalaryPerUnitPriceNameDto.fromDomain(item))
                .collect(Collectors.toList());
    }

    public Optional<SalaryPerUnitPriceNameDto> getSalaryPerUnitPriceNameById(String cid, String code){
        return finder.getSalaryPerUnitPriceNameById(cid, code).map(item -> SalaryPerUnitPriceNameDto.fromDomain(item));
    }

}
