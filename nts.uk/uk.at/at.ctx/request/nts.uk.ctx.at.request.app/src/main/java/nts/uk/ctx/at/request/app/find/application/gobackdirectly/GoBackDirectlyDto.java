package nts.uk.ctx.at.request.app.find.application.gobackdirectly;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;

@AllArgsConstructor
@Value
public class GoBackDirectlyDto {
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
	int goWorkAtr1;
	/**
	 * 勤務直帰1
	 */
	int backHomeAtr1;
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
	int goWorkAtr2;
	/**
	 * 勤務直帰2
	 */
	int backHomeAtr2;
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
	public static GoBackDirectlyDto convertToDto(GoBackDirectly domain) {
		if(domain==null) return null;
		return new GoBackDirectlyDto(
				domain.getVersion(),
				domain.getCompanyID(), 
				domain.getAppID(), 
				domain.getWorkTypeCD().v(),
				domain.getSiftCD().v(), 
				domain.getWorkChangeAtr().value, 
				domain.getGoWorkAtr1().value,
				domain.getBackHomeAtr1().value, 
				domain.getWorkTimeStart1() == null ? null : domain.getWorkTimeStart1().v(), 
				domain.getWorkTimeEnd1() == null ? null : domain.getWorkTimeEnd1().v(),
				domain.getWorkLocationCD1(), 
				domain.getGoWorkAtr2().value, 
				domain.getBackHomeAtr2().value,
				domain.getWorkTimeStart2() == null ? null : domain.getWorkTimeStart2().v(), 
				domain.getWorkTimeEnd2() == null ? null : domain.getWorkTimeEnd2().v(),
				domain.getWorkLocationCD2());
	}
}
