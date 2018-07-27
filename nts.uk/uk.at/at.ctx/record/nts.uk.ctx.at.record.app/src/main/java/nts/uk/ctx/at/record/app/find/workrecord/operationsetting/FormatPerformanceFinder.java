package nts.uk.ctx.at.record.app.find.workrecord.operationsetting;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.FormatPerformanceRepository;

@Stateless
/**
* 実績修正画面で利用するフォーマット
*/
public class FormatPerformanceFinder
{

    @Inject
    private FormatPerformanceRepository finder;

    public List<FormatPerformanceDto> getAllFormatPerformance(){
        return finder.getAllFormatPerformance().stream().map(item -> FormatPerformanceDto.fromDomain(item))
                .collect(Collectors.toList());
    }
    
    /**
     * @param cid
     * @return
     */
    public FormatPerformanceDto getAllFormatPerformanceById(String cid){
    	FormatPerformanceDto formatPerformanceDto = null;
    	if (finder.getFormatPerformanceById(cid).isPresent()) {
    		formatPerformanceDto = FormatPerformanceDto.fromDomain(finder.getFormatPerformanceById(cid).get());
    	}
    	
    	return formatPerformanceDto;
    }
}
