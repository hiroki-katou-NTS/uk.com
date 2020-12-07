package nts.uk.screen.at.app.kdl045.query;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.WorkTimezoneCommonSetDto;

@Getter
@Setter
@NoArgsConstructor
public class GetInformationStartupOutput {
	/**
	 * 就業時間帯の共通設定
	 */
	private WorkTimezoneCommonSetDto workTimezoneCommonSet;
	
	/**
	 * Map<時間休暇種類, 合計使用時間>
	 */
	private List<UsageTimeAndType> listUsageTimeAndType = new ArrayList<>();
	
	/**
	 * 希望を表示するか、 (1:true,0:false)
	 */
	private Integer showYourDesire;
	
	/**
	 * 一日分の勤務希望の表示情報
	 */
	private WorkAvailabilityOfOneDayDto workAvaiOfOneDayDto;

	public GetInformationStartupOutput(WorkTimezoneCommonSetDto workTimezoneCommonSet,
			List<UsageTimeAndType> listUsageTimeAndType, Integer showYourDesire,
			WorkAvailabilityOfOneDayDto workAvaiOfOneDayDto) {
		super();
		this.workTimezoneCommonSet = workTimezoneCommonSet;
		this.listUsageTimeAndType = listUsageTimeAndType;
		this.showYourDesire = showYourDesire;
		this.workAvaiOfOneDayDto = workAvaiOfOneDayDto;
	}
	
}
