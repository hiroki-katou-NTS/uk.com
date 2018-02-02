package nts.uk.ctx.at.request.app.find.application.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmployWorkType;

@Data
@AllArgsConstructor
public class AppEmployWorkTypeDto {
	/**
	 * 会社ID
	 * companyID
	 */
	private String companyID;
	
	/**
	 * 雇用コード
	 */
	private String employmentCode;
	/**
	 * 申請種類
	 */
	private int appType;
	/**
	 * 休暇申請種類, 振休振出区分
	 */
	private int holidayOrPauseType;
	/**
	 * 表示する勤務種類を設定する
	 */
	private String workTypeCode;
	/**
	 * 勤務種類名称
	 */
	private String workTypeName;
	
	public static AppEmployWorkTypeDto fromDomain(AppEmployWorkType domain){
		return new AppEmployWorkTypeDto(domain.getCompanyID(), 
				domain.getEmploymentCode(), domain.getAppType().value, domain.getHolidayOrPauseType(), domain.getWorkTypeCode(), "");
	}
}
