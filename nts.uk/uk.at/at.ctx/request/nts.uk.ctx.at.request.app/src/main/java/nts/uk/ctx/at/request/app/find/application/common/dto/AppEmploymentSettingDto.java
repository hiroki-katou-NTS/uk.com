package nts.uk.ctx.at.request.app.find.application.common.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmployWorkType;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;

@Data
@AllArgsConstructor
public class AppEmploymentSettingDto {

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
	 * 休暇種類を利用しない
	 */
	private boolean holidayTypeUseFlg;
	/**
	 * 表示する勤務種類を設定する
	 */
	private boolean displayFlag;
	/**
	 * 申請別対象勤務種類-勤務種類リスト
	 */
	List<AppEmployWorkTypeDto> lstWorkType;
	
	public static AppEmploymentSettingDto fromDomain(AppEmploymentSetting domain){
		return new AppEmploymentSettingDto(domain.getCompanyID(), 
				domain.getEmploymentCode(), 
				domain.getAppType().value, 
				domain.getHolidayOrPauseType(), 
				domain.getHolidayTypeUseFlg(), 
				domain.isDisplayFlag(), 
				domain.getLstWorkType()
					.stream()
					.map(item -> AppEmployWorkTypeDto.fromDomain(item))
					.collect(Collectors.toList())
				);
	}
	
	public AppEmploymentSetting toDomain() {
		return new AppEmploymentSetting(
				companyID, 
				employmentCode, 
				EnumAdaptor.valueOf(appType, ApplicationType.class), 
				holidayOrPauseType, 
				holidayTypeUseFlg, 
				displayFlag, 
				lstWorkType.stream().map(x -> AppEmployWorkType.createSimpleFromJavaType(
						x.getCompanyID(), 
						x.getEmploymentCode(), 
						x.getAppType(), 
						x.getHolidayOrPauseType(), 
						x.getWorkTypeCode())).collect(Collectors.toList()));
	}
}
