package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empcomofficehis;

import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.AffOfficeInformationRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
/**
* 所属事業所情報: Finder
*/
public class AffOfficeInformationFinder
{

    @Inject
    private AffOfficeInformationRepository finder;

    public List<AffOfficeInformationDto> getAllAffOfficeInformation(){
        return finder.getAllAffOfficeInformation().stream().map(item -> AffOfficeInformationDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
