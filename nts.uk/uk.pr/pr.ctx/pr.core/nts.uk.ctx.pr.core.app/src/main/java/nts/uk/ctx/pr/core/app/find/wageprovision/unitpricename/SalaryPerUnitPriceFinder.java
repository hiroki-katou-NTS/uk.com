package nts.uk.ctx.pr.core.app.find.wageprovision.unitpricename;

import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPriceRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
/**
* 給与個人単価名称: Finder
*/
public class SalaryPerUnitPriceFinder
{

    @Inject
    private SalaryPerUnitPriceRepository finder;

    public List<SalaryPerUnitPriceNameDto> getAllSalaryPerUnitPriceName(){
        return finder.getAllSalaryPerUnitPrice().stream().map(item -> SalaryPerUnitPriceNameDto.fromDomain(item.getSalaryPerUnitPriceName()))
                .collect(Collectors.toList());
    }

    public Optional<SalaryPerUnitPriceDataDto> getSalaryPerUnitPriceById(String cid, String code){
        return finder.getSalaryPerUnitPriceById(cid, code).map(item -> new SalaryPerUnitPriceDataDto(item));
    }

}
