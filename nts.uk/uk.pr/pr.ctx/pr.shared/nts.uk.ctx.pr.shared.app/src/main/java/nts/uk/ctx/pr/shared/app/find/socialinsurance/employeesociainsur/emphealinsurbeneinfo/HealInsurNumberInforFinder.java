package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.HealInsurNumberInforRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
/**
* 健保番号情報: Finder
*/
public class HealInsurNumberInforFinder
{

    @Inject
    private HealInsurNumberInforRepository finder;

    public List<HealInsurNumberInforDto> getAllHealInsurNumberInfor(){
        return finder.getAllHealInsurNumberInfor().stream().map(item -> HealInsurNumberInforDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
