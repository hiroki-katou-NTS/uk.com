package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.SocialInsurAcquisiInforRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;


@Stateless
/**
* 社会保険取得時情報: Finder
*/
public class SocialInsurAcquisiInforFinder
{

    @Inject
    private SocialInsurAcquisiInforRepository finder;

    public List<SocialInsurAcquisiInforDto> getAllSocialInsurAcquisiInfor(){
        return finder.getAllSocialInsurAcquisiInfor().stream().map(item -> SocialInsurAcquisiInforDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
