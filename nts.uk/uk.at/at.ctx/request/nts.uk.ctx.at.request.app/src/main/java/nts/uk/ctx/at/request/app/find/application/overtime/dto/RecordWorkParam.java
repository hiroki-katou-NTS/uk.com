package nts.uk.ctx.at.request.app.find.application.overtime.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;

@Data
public class RecordWorkParam {
	public String employeeID; 
	public String appDate;
	public String siftCD;
	public int prePostAtr;
	public List<CaculationTime> overtimeHours;
	private String workTypeCode;
	private List<Integer> startTimeRests;
	private List<Integer> endTimeRests;
	private boolean restTimeDisFlg;
	private OvertimeSettingDataDto overtimeSettingDataDto;
	
	public List<Integer> getStartTimeRests() {
		List<Integer> result=  new ArrayList<Integer>();
		startTimeRests.forEach(x->{
			if (x != null) {
				result.add(x);
			}
		});

		return result;
	}
	
	public List<Integer> getEndTimeRests() {
		List<Integer> result=  new ArrayList<Integer>();
		endTimeRests.forEach(x->{
			if (x != null) {
				result.add(x);
			}
		});

		return result;
	}
}
