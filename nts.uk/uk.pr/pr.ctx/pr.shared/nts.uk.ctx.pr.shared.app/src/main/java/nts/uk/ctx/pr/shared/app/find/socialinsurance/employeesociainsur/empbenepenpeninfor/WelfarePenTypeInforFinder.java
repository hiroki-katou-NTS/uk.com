package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empbenepenpeninfor;

import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.WelfarePenTypeInforRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;


@Stateless
/**
* 厚生年金種別情報: Finder
*/
public class WelfarePenTypeInforFinder
{

    @Inject
    private WelfarePenTypeInforRepository finder;

    public List<WelfarePenTypeInforDto> getAllWelfarePenTypeInfor(){
        return finder.getAllWelfarePenTypeInfor().stream().map(item -> WelfarePenTypeInforDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
