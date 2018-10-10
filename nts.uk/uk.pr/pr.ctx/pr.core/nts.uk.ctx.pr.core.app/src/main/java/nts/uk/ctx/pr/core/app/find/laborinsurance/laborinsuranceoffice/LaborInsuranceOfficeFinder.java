package nts.uk.ctx.pr.core.app.find.laborinsurance.laborinsuranceoffice;

import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.LaborInsuranceOfficeRepository;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
/**
* 労働保険事業所: Finder
*/
public class LaborInsuranceOfficeFinder
{

    @Inject
    private LaborInsuranceOfficeRepository finder;

    public List<LaborInsuranceOfficeDto> getAllLaborInsuranceOffice(){
        return finder.getAllLaborInsuranceOffice().stream().map(item -> LaborInsuranceOfficeDto.fromDomainToDto(item))
                .collect(Collectors.toList());
    }

    public LaborInsuranceOfficeDto getLaborInsuranceOffice(String laborOfficeCode){
        return finder.getLaborInsuranceOfficeById(laborOfficeCode).map(item -> LaborInsuranceOfficeDto.fromDomainToDto(item)).orElse(null);
    }

}
