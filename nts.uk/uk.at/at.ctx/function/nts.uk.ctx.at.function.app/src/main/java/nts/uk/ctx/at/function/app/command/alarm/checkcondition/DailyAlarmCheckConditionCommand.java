package nts.uk.ctx.at.function.app.command.alarm.checkcondition;


import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.adapter.ErrorAlarmWorkRecordAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.FixedConWorkRecordAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.WorkRecordExtraConAdapterDto;

/**
 * 
 * @author HungTT
 *
 */

@Getter
@Setter
@NoArgsConstructor
public class DailyAlarmCheckConditionCommand {

	private int conditionToExtractDaily;
	
	private boolean addApplication;
	//tab 2
	private List<String> listErrorAlarmCode;
	//tab 3
	private List<WorkRecordExtraConAdapterDto> listExtractConditionWorkRecork;
	//tab 4
	private List<FixedConWorkRecordAdapterDto> listFixedExtractConditionWorkRecord;
	
	public DailyAlarmCheckConditionCommand(int conditionToExtractDaily, boolean addApplication,
			List<String> listErrorAlarmCode, List<WorkRecordExtraConAdapterDto> listExtractConditionWorkRecork,
			List<FixedConWorkRecordAdapterDto> listFixedExtractConditionWorkRecord) {
		super();
		this.conditionToExtractDaily = conditionToExtractDaily;
		this.addApplication = addApplication;
		this.listErrorAlarmCode = listErrorAlarmCode;
		this.listExtractConditionWorkRecork = listExtractConditionWorkRecork;
		this.listFixedExtractConditionWorkRecord = listFixedExtractConditionWorkRecord;
	}
	
}
