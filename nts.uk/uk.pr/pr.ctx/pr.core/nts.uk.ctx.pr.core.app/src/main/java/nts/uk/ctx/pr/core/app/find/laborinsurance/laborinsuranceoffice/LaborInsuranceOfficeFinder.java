package nts.uk.ctx.pr.core.app.find.労働保険.労働保険事業所;

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
        return finder.getAllLaborInsuranceOffice().stream().map(item -> LaborInsuranceOfficeDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
