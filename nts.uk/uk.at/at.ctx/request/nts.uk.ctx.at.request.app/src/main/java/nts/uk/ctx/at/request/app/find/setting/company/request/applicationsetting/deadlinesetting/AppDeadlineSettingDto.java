package nts.uk.ctx.at.request.app.find.setting.company.request.applicationsetting.deadlinesetting;

import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.deadlinesetting.AppDeadlineSetting;

public class AppDeadlineSettingDto {
	/**
	 * 会社ID
	 */
	public String companyId;
	
	/**
	 * 締めＩＤ
	 */
	public int closureId;
	
	/**
	 * 利用区分
	 */
	public int userAtr;
	
	/**
	 * 締切日数
	 */
	public int deadline;
	
	/**
	 * 締切基準
	 */
	public int deadlineCriteria;
	
	public static AppDeadlineSettingDto fromDomain(AppDeadlineSetting appDeadlineSetting) {
		AppDeadlineSettingDto appDeadlineSettingDto = new AppDeadlineSettingDto();
		appDeadlineSettingDto.companyId = appDeadlineSetting.getCompanyId();
		appDeadlineSettingDto.closureId = appDeadlineSetting.getClosureId();
		appDeadlineSettingDto.userAtr = appDeadlineSetting.getUserAtr().value;
		appDeadlineSettingDto.deadline = appDeadlineSetting.getDeadline().v();
		// appDeadlineSettingDto.deadlineCriteria = appDeadlineSetting.getDeadlineCriteria().value;
		return appDeadlineSettingDto;
	}
	
	public AppDeadlineSetting toDomain() {
		return AppDeadlineSetting.createSimpleFromJavaType(
				companyId, 
				closureId, 
				userAtr, 
				deadline, 
				deadlineCriteria);
	}
}
