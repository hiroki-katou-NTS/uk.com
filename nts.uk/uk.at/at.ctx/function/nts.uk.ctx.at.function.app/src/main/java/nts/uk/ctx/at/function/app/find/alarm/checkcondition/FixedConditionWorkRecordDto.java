package nts.uk.ctx.at.function.app.find.alarm.checkcondition;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.adapter.FixedConWorkRecordAdapterDto;

/**
 * 
 * @author HungTT
 *
 */

@Data
@AllArgsConstructor
public class FixedConditionWorkRecordDto {

	private String dailyAlarmConID;
	private String checkName;
	private int fixConWorkRecordNo;
	private String message;
	private boolean useAtr;
	private Integer eralarmAtr;
	
	public FixedConditionWorkRecordDto(FixedConWorkRecordAdapterDto importDto) {
		super();
		this.dailyAlarmConID = importDto.getDailyAlarmConID();
		this.checkName = "sample name";
		this.fixConWorkRecordNo = importDto.getFixConWorkRecordNo();
		this.message = importDto.getMessage();
		this.useAtr = importDto.isUseAtr();
	}

}
