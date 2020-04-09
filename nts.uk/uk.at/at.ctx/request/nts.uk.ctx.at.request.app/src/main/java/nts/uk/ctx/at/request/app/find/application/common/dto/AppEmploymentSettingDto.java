package nts.uk.ctx.at.request.app.find.application.common.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmployWorkType;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.WorkTypeObjAppHoliday;

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
	
//	public static AppEmploymentSettingDto fromDomain(AppEmploymentSetting domain){
//		return new AppEmploymentSettingDto(domain.getCompanyID(), 
//				domain.getEmploymentCode(), 
//				domain.getAppType().value, 
//				domain.getHolidayOrPauseType(), 
//				domain.getHolidayTypeUseFlg(), 
//				domain.isDisplayFlag(), 
//				domain.getLstWorkType()
//					.stream()
//					.map(item -> AppEmployWorkTypeDto.fromDomain(item))
//					.collect(Collectors.toList())
//				);
//	}
	public static List<AppEmploymentSettingDto> fromDomain(List<AppEmploymentSetting> domainList){
		List<AppEmploymentSettingDto> list = new ArrayList<AppEmploymentSettingDto>();
		domainList.stream().forEach(x -> {
			List<WorkTypeObjAppHoliday> listWTOAH = x.getListWTOAH();
			if (!x.getListWTOAH().isEmpty()) {
				 
				listWTOAH.stream().forEach(y ->{
					AppEmploymentSettingDto item = new AppEmploymentSettingDto(x.getCompanyID(), x.getEmploymentCode(),
							y.getAppType().value, y.getSwingOutAtr().isPresent() ? y.getSwingOutAtr().get().value : y.getHolidayAppType().isPresent() ? y.getHolidayAppType().get().value : 9, y.getHolidayTypeUseFlg().get(), y.getWorkTypeSetDisplayFlg(),
							y.getWorkTypeList() == null ? null : y.getWorkTypeList().stream().map(a -> new AppEmployWorkTypeDto(x.getCompanyID(),x.getEmploymentCode(),
									y.getAppType().value,y.getSwingOutAtr().isPresent() ? y.getSwingOutAtr().get().value : y.getHolidayAppType().isPresent() ? y.getHolidayAppType().get().value : 9, a,"")).collect(Collectors.toList())
							);
					list.add(item);
				});
			}
		});
		return list;
	}
	public static AppEmploymentSettingDto fromDomain(AppEmploymentSetting domain){
		AppEmploymentSettingDto item = new AppEmploymentSettingDto();
		
			List<WorkTypeObjAppHoliday> listWTOAH = domain.getListWTOAH();
			if (!domain.getListWTOAH().isEmpty()) {
				 
					WorkTypeObjAppHoliday y = domain.getListWTOAH().get(0);
					AppEmploymentSettingDto i = new AppEmploymentSettingDto(domain.getCompanyID(), domain.getEmploymentCode(),
							y.getAppType().value, y.getSwingOutAtr().isPresent() ? y.getSwingOutAtr().get().value : y.getHolidayAppType().isPresent() ? y.getHolidayAppType().get().value : 9, y.getHolidayTypeUseFlg().get(), y.getWorkTypeSetDisplayFlg(),
							y.getWorkTypeList() == null ? null : y.getWorkTypeList().stream().map(a -> new AppEmployWorkTypeDto(domain.getCompanyID(),domain.getEmploymentCode(),
									y.getAppType().value,y.getSwingOutAtr().isPresent() ? y.getSwingOutAtr().get().value : y.getHolidayAppType().isPresent() ? y.getHolidayAppType().get().value : 9, a,"")).collect(Collectors.toList())
							);
					
				
			}
		
		return item;
	}
	public AppEmploymentSettingDto() {
	}
	
	public AppEmploymentSetting toDomain() {
//		return new AppEmploymentSetting(
//				companyID, 
//				employmentCode, 
//				EnumAdaptor.valueOf(appType, ApplicationType.class), 
//				holidayOrPauseType, 
//				holidayTypeUseFlg, 
//				displayFlag, 
//				lstWorkType.stream().map(x -> AppEmployWorkType.createSimpleFromJavaType(
//						x.getCompanyID(), 
//						x.getEmploymentCode(), 
//						x.getAppType(), 
//						x.getHolidayOrPauseType(), 
//						x.getWorkTypeCode())).collect(Collectors.toList()));
		List<WorkTypeObjAppHoliday> list = new ArrayList<>();
		WorkTypeObjAppHoliday workTypeObjAppHoliday = new WorkTypeObjAppHoliday(
				CollectionUtil.isEmpty(lstWorkType) ?  null : lstWorkType.stream().map(x -> x.getWorkTypeCode()).collect(Collectors.toList()),
				EnumAdaptor.valueOf(appType, ApplicationType.class),
				displayFlag,
				appType == 1 ? holidayOrPauseType : null,
				holidayTypeUseFlg,
				appType == 10 ? holidayOrPauseType : null);
		list.add(workTypeObjAppHoliday);
		return new AppEmploymentSetting(companyID, employmentCode, list);
	}
}
