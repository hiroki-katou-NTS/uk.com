package nts.uk.ctx.at.record.app.find.workrecord.operationsetting;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.MonPerformanceFunRepository;

@Stateless
/**
* 月別実績の修正の機能
*/
public class MonPerformanceFunFinder
{

    @Inject
    private MonPerformanceFunRepository finder;

    public List<MonPerformanceFunDto> getAllMonPerformanceFun(){
        return finder.getAllMonPerformanceFun().stream().map(item -> MonPerformanceFunDto.fromDomain(item))
                .collect(Collectors.toList());
    }

    /**
     * @param cid
     * @return
     */
    public MonPerformanceFunDto getAllMonPerformanceFunById(String cid){
    	MonPerformanceFunDto monPerformanceFunDto = null;
    	if (finder.getMonPerformanceFunById(cid).isPresent()) {
    		monPerformanceFunDto = MonPerformanceFunDto.fromDomain(finder.getMonPerformanceFunById(cid).get());
    	}
    	
    	return monPerformanceFunDto;
    }
}
