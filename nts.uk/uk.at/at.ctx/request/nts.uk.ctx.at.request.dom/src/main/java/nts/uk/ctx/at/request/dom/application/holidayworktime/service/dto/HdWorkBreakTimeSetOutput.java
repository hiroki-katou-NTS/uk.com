package nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;

@AllArgsConstructor
@Getter
@Setter
public class HdWorkBreakTimeSetOutput {
	
	/**
	 * 休憩時間帯表示区分
	 */
	private boolean displayRestTime;
	
	/**
	 * 休憩時間帯設定リスト
	 */
	private List<DeductionTime> deductionTimeLst;
	
}
