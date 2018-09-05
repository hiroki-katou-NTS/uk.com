package nts.uk.ctx.sys.assist.app.find.salary;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.sys.assist.dom.salary.SociInsuStanDateRepository;

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
