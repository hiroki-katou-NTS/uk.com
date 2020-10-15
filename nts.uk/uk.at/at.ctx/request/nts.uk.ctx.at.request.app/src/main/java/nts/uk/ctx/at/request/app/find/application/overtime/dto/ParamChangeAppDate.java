package nts.uk.ctx.at.request.app.find.application.overtime.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.request.app.command.application.holidaywork.AppHdWorkDispInfoCmd;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.OvertimeColorCheck;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;

@Value
public class ParamChangeAppDate {
	/**
	 * appDate
	 */
	private String appDate;
	/**
	 * prePostAtr
	 */
	private int prePostAtr;
	/**
	 * siftCD
	 */
	private String siftCD;
	
	/**
	 * overtimeHours
	 */
	private List<CaculationTime> overtimeHours;
	private String workTypeCode;
	private Integer startTime;
	private Integer endTime;
	private List<Integer> startTimeRests;
	private List<Integer> endTimeRests;
	private String changeEmployee;
	
	private int overtimeAtr;
	
	private OvertimeSettingDataDto overtimeSettingDataDto;
	private ApplicationDto opAppBefore; 
	private boolean beforeAppStatus; 
	private int actualStatus; 
	private List<OvertimeColorCheck> actualLst; 
	
	private AppHdWorkDispInfoCmd appHdWorkDispInfoCmd;
	
	public List<Integer> getStartTimeRests() {

		List<Integer> result = new ArrayList<>();
		startTimeRests.forEach(x -> {
			if (x != null) {
				result.add(x);
			}
		});

		return result;
	}
	
	public List<Integer> getEndTimeRests() {

		List<Integer> result = new ArrayList<>();
		endTimeRests.forEach(x -> {
			if (x != null) {
				result.add(x);
			}
		});

		return result;
	}

}
