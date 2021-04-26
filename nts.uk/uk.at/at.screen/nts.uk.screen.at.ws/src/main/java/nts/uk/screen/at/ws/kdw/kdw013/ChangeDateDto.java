package nts.uk.screen.at.ws.kdw.kdw013;

import java.util.List;

import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.screen.at.app.kdw013.a.ConfirmerDto;

/**
 * 
 * @author tutt
 *
 */
@Setter
public class ChangeDateDto {
	//1
	private List<WorkGroupDto> WorkGroupDtos;
	
	//2
	private List<IntegrationOfDailyDto> lstIntegrationOfDailyDto;
	 
	//3
	private GeneralDate workCorrectionStartDate;
	
	//4
	private List<ConfirmerDto> lstComfirmerDto;
	
	//5
	private List<WorkRecordDetailDto> lstWorkRecordDetailDto;
}
