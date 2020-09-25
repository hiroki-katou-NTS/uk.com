package nts.uk.ctx.at.request.app.find.application.gobackdirectly;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly_Old;

@AllArgsConstructor
@Value
public class GoBackDirectlyDto_Old {
	/**
	 * version
	 */
	Long version;
	/**
	 * 会社ID
	 */
	String companyID;
	/**
	 * 申請ID
	 */
	String appID;
	/**
	 * 勤務種類
	 */
	String workTypeCD;
	/**
	 * 就業時間帯
	 */
	String siftCD;
	/**
	 * 勤務を変更する
	 */
	Integer workChangeAtr;
	/**
	 * 勤務直行1
	 */
	Integer goWorkAtr1;
	/**
	 * 勤務直帰1
	 */
	Integer backHomeAtr1;
	/**
	 * 勤務時間開始1
	 */
	Integer workTimeStart1;
	/**
	 * 勤務時間終了1
	 */
	Integer workTimeEnd1;
	/**
	 * 勤務場所選択1
	 */
	String workLocationCD1;
	/**
	 * 勤務直行2
	 */
	Integer goWorkAtr2;
	/**
	 * 勤務直帰2
	 */
	Integer backHomeAtr2;
	/**
	 * 勤務時間開始2
	 */
	Integer workTimeStart2;
	/**
	 * 勤務時間終了2
	 */
	Integer workTimeEnd2;
	/**
	 * 勤務場所選択２
	 */
	String workLocationCD2;
	
	/**
	 * Convert to GoBackDirectlyDto
	 */
	public static GoBackDirectlyDto_Old convertToDto(GoBackDirectly_Old domain) {
		if(domain==null) return null;
		return new GoBackDirectlyDto_Old(
				domain.getVersion(),
				domain.getCompanyID(), 
				domain.getAppID(), 
				domain.getWorkTypeCD().map(x -> x.v()).orElse(null),
				domain.getSiftCD().map(x -> x.v()).orElse(null), 
				domain.getWorkChangeAtr().map(x -> x.value).orElse(null),
				domain.getGoWorkAtr1().value,
				domain.getBackHomeAtr1().value, 
				domain.getWorkTimeStart1().map(x -> x.v()).orElse(null),
				domain.getWorkTimeEnd1().map(x -> x.v()).orElse(null),
				domain.getWorkLocationCD1().map(x -> x).orElse(null),
				domain.getGoWorkAtr2().map(x -> x.value).orElse(null),
				domain.getBackHomeAtr2().map(x -> x.value).orElse(null),
				domain.getWorkTimeStart2().map(x -> x.v()).orElse(null),
				domain.getWorkTimeEnd2().map(x -> x.v()).orElse(null),
				domain.getWorkLocationCD2().map(x -> x).orElse(null));
	}
}
