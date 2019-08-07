package .app.find.ドメインモデル.nittsusystem.universalk.salary.shared.socialinsurance.employeesociainsur.empbenepenpeninfor;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

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
