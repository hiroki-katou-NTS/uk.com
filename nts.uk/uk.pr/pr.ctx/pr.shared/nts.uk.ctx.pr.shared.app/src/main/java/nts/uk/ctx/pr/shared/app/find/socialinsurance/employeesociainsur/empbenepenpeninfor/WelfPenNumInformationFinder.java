package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empbenepenpeninfor;

import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.WelfPenNumInformationRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
/**
* 厚生年金番号情報: Finder
*/
public class WelfPenNumInformationFinder
{

    @Inject
    private WelfPenNumInformationRepository finder;

    public List<WelfPenNumInformationDto> getAllWelfPenNumInformation(){
        return finder.getAllWelfPenNumInformation().stream().map(item -> WelfPenNumInformationDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
