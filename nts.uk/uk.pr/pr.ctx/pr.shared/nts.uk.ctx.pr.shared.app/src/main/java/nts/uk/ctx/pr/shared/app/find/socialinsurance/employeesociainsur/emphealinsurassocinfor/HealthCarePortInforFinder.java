package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.emphealinsurassocinfor;

import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor.HealthCarePortInforRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
/**
* 健保組合情報: Finder
*/
public class HealthCarePortInforFinder
{

    @Inject
    private HealthCarePortInforRepository finder;

    public List<HealthCarePortInforDto> getAllHealthCarePortInfor(){
        return finder.getAllHealthCarePortInfor().stream().map(item -> HealthCarePortInforDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
