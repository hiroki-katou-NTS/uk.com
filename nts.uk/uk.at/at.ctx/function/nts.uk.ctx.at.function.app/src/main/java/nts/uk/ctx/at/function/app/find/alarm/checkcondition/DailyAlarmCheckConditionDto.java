package nts.uk.ctx.at.function.app.find.alarm.checkcondition;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.adapter.WorkRecordExtraConAdapterDto;


/**
 * 
 * @author HungTT
 *
 */

@Data
@AllArgsConstructor
public class DailyAlarmCheckConditionDto {

	private boolean addApplication;
	
	private int conditionToExtractDaily;
	
	private List<String> listErrorAlarmCode;
	
	private List<DailyErrorAlarmCheckDto> listErrorAlarmCheck;
	
	private List<WorkRecordExtraConAdapterDto> listExtractConditionWorkRecork;
	
	private List<FixedConditionWorkRecordDto> listFixedExtractConditionWorkRecord;
	
}
