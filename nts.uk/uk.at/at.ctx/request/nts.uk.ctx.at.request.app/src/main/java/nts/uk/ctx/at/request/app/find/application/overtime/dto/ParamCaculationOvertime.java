package nts.uk.ctx.at.request.app.find.application.overtime.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.OvertimeColorCheck;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;

@Value
public class ParamCaculationOvertime {
	/**
	 * OvertimeInputDtos
	 */
	private List<CaculationTime> overtimeHours;
	
	/**
	 * bonusTimes
	 */
	private List<CaculationTime> bonusTimes;
	/**
	 * prePostAtr
	 */
	private int prePostAtr;
	/**
	 * appDate
	 */
	private String appDate;
	
	private String siftCD;
	
	private String inputDate;
	private String workTypeCode;
	private Integer startTime;
	private Integer endTime;
	private List<Integer> startTimeRests;
	private List<Integer> endTimeRests;
	private boolean displayCaculationTime;
	private boolean isFromStepOne;
	public ApplicationDto opAppBefore;
	public boolean beforeAppStatus;
	public int actualStatus;
	public List<OvertimeColorCheck> actualLst;
	/**
	 * 申請共通設定
	 */
	public OvertimeSettingDataDto overtimeSettingDataDto;
	
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
