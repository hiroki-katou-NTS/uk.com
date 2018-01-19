package nts.uk.ctx.at.function.app.find.alarm.checkcondition;

import java.util.ArrayList;
import java.util.List;

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

	private String errorAlarmId;
	private String checkName;
	private int fixConWorkRecordNo;
	private String message;
	private boolean useAtr;
	
	public FixedConditionWorkRecordDto(FixedConWorkRecordAdapterDto importDto) {
		super();
		this.errorAlarmId = importDto.getErrorAlarmID();
		this.checkName = "sample name";
		this.fixConWorkRecordNo = importDto.getFixConWorkRecordNo();
		this.message = importDto.getMessage();
		this.useAtr = importDto.isUseAtr();
	}

//	public FixedConditionWorkRecordDto(String checkName, int fixConWorkRecordNo, String message) {
//		super();
//		this.checkName = checkName;
//		this.fixConWorkRecordNo = fixConWorkRecordNo;
//		this.message = message;
//	}
//	
//	public static List<FixedConditionWorkRecordDto> combine(List<FixedConditionWorkRecordDto> bList, List<FixedConditionWorkRecordDto> vList) {
//		List<FixedConditionWorkRecordDto> result = new ArrayList<>();
//		if (bList == null || bList.isEmpty()) return result;
//		result = bList;
//		if (vList != null && !bList.isEmpty()) {
//			for (FixedConditionWorkRecordDto i : result) {
//				for (FixedConditionWorkRecordDto e : vList) {
//					
//				}
//			}
//		}
//		return result;
//	}
	
}
