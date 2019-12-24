package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SociInsuStanDateRepository;

@Stateless
/**
* 社会保険基準年月日
*/
public class SociInsuStanDateFinder
{

    @Inject
    private SociInsuStanDateRepository finder;

    public List<SociInsuStanDateDto> getAllSociInsuStanDate(){
        return finder.getAllSociInsuStanDate().stream().map(item -> SociInsuStanDateDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
