package nts.uk.ctx.at.record.app.find.workrecord.operationsetting;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.DaiPerformanceFunRepository;

@Stateless
/**
* 日別実績の修正の機能
*/
public class DaiPerformanceFunFinder
{

    @Inject
    private DaiPerformanceFunRepository finder;

    public List<DaiPerformanceFunDto> getAllDaiPerformanceFun(){
        return finder.getAllDaiPerformanceFun().stream().map(item -> DaiPerformanceFunDto.fromDomain(item))
                .collect(Collectors.toList());
    }
    
    /**
     * @param cid
     * @return
     */
    public DaiPerformanceFunDto getDaiPerformanceFunById(String cid) {
    	DaiPerformanceFunDto daiPerformanceFunDto = null;
    	if (finder.getDaiPerformanceFunById(cid).isPresent()) {
    		daiPerformanceFunDto = DaiPerformanceFunDto.fromDomain(finder.getDaiPerformanceFunById(cid).get());
    	}
    	
    	return daiPerformanceFunDto;
    }
}
