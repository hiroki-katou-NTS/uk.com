package nts.uk.screen.at.app.kdl045.query;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.WorkTimezoneCommonSetDto;

@Getter
@Setter
@NoArgsConstructor
public class GetMoreInformationOutput {
	/**
	 * 就業時間帯の共通設定
	 */
	private WorkTimezoneCommonSetDto workTimezoneCommonSet;
	/**
	  　*<Optional>休憩時間
	 */
	private BreakTimeKdl045Dto breakTime;
	/**
	 * 勤務タイプ : 就業時間帯の勤務形態 : 
	 * return Enum WorkTimeForm 
	 */
	private Integer workTimeForm;
	
	/**
	 * 　Optional<出勤休日区分>
	 * return Enum : WorkStyle
	 */
	private Integer workStyle;
	
	public GetMoreInformationOutput(WorkTimezoneCommonSetDto workTimezoneCommonSet, BreakTimeKdl045Dto breakTime,
			Integer workTimeForm,Integer workStyle) {
		super();
		this.workTimezoneCommonSet = workTimezoneCommonSet;
		this.breakTime = breakTime;
		this.workTimeForm = workTimeForm;
		this.workStyle = workStyle;
	}
	
	
}

