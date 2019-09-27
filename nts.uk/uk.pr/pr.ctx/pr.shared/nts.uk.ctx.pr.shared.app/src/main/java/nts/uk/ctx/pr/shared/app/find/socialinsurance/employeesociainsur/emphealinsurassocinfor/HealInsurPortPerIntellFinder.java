package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.emphealinsurassocinfor;

import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor.HealInsurPortPerIntellRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
/**
* 健保組合加入期間情報: Finder
*/
public class HealInsurPortPerIntellFinder
{

    @Inject
    private HealInsurPortPerIntellRepository finder;

    public List<HealInsurPortPerIntellDto> getAllHealInsurPortPerIntell(){
        return finder.getAllHealInsurPortPerIntell().stream().map(item -> HealInsurPortPerIntellDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
