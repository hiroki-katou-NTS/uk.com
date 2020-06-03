package nts.uk.ctx.at.request.app.find.application.common.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.WorkTypeObjAppHoliday;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppEmploymentSettingDto {
	
//	会社ID
	private String companyID;
	
//	雇用区分コード
	private String employmentCode;
	
//	申請別対象勤務種類
	private List<WorkTypeObjAppHolidayDto> listWTOAH;
	
	public static List<AppEmploymentSettingDto> fromDomain(List<AppEmploymentSetting> domainList){
		List<AppEmploymentSettingDto> listDto = new ArrayList<>();

		if(!CollectionUtil.isEmpty(domainList)) {
			// create AppEmploymentSettingDto List
			listDto = domainList.stream().map(i -> new AppEmploymentSettingDto(i.getCompanyID(), i.getEmploymentCode(), 
					CollectionUtil.isEmpty(i.getListWTOAH()) ? 
							null 
							: 
							i.getListWTOAH().stream().map(
									x -> 
							new WorkTypeObjAppHolidayDto(x.getWorkTypeList(), x.getAppType().value, x.getWorkTypeSetDisplayFlg(), 
									x.getHolidayAppType().isPresent() ? x.getHolidayAppType().get().value : 9, 
									x.getHolidayTypeUseFlg().isPresent()? x.getHolidayTypeUseFlg().get() : false, 
									x.getSwingOutAtr().isPresent()? x.getSwingOutAtr().get().value : 9)
							).collect(Collectors.toList())
							)).collect(Collectors.toList());
		}
		return listDto;
	}
	
	
	public static AppEmploymentSettingDto fromDomain(AppEmploymentSetting domain){
		if(domain !=null) {
			return new AppEmploymentSettingDto(domain.getCompanyID(), domain.getEmploymentCode(), 
					CollectionUtil.isEmpty(domain.getListWTOAH()) ? 
							null 
							: 
							domain.getListWTOAH().stream().map(
									x -> 
							new WorkTypeObjAppHolidayDto(x.getWorkTypeList(), x.getAppType().value, x.getWorkTypeSetDisplayFlg(), 
									x.getHolidayAppType().isPresent() ? x.getHolidayAppType().get().value : 9, 
									x.getHolidayTypeUseFlg().isPresent()? x.getHolidayTypeUseFlg().get() : false, 
									x.getSwingOutAtr().isPresent()? x.getSwingOutAtr().get().value : 9)
							).collect(Collectors.toList())
							);
		}
		else {
			return new AppEmploymentSettingDto();
		}
		
	}
	
	public static AppEmploymentSettingDto fromDomain(Optional<AppEmploymentSetting> domainOptional){
		
		AppEmploymentSetting domain = null;
		if(domainOptional.isPresent()) {
			domain = domainOptional.get();
		}
		if(domain != null) {
			return new AppEmploymentSettingDto(domain.getCompanyID(), domain.getEmploymentCode(), 
					CollectionUtil.isEmpty(domain.getListWTOAH()) ? 
							null 
							: 
							domain.getListWTOAH().stream().map(
									x -> 
							new WorkTypeObjAppHolidayDto(x.getWorkTypeList(), x.getAppType().value, x.getWorkTypeSetDisplayFlg(), 
									x.getHolidayAppType().isPresent() ? x.getHolidayAppType().get().value : 9, 
									x.getHolidayTypeUseFlg().isPresent()? x.getHolidayTypeUseFlg().get() : false, 
									x.getSwingOutAtr().isPresent()? x.getSwingOutAtr().get().value : 9)
							).collect(Collectors.toList())
							);
		}else {
			return new AppEmploymentSettingDto();
		}
		
	}
	
	
	public AppEmploymentSetting toDomain() {
		return new AppEmploymentSetting(companyID, employmentCode, 
				CollectionUtil.isEmpty(listWTOAH) ? null :
					
					listWTOAH.stream().map(x -> new WorkTypeObjAppHoliday(
							x.getWorkTypeList(),
							EnumAdaptor.valueOf(x.getAppType(), ApplicationType.class),
							x.getWorkTypeSetDisplayFlg(),
							x.getHolidayAppType() == 9 ? null : x.getHolidayAppType(),
							x.getHolidayTypeUseFlg(),
							x.getSwingOutAtr() == 9 ? null : x.getSwingOutAtr()))
					.collect(Collectors.toList())
				);
	}
	
	public Optional<AppEmploymentSetting> toDomainOptional() {	
		return Optional.ofNullable(new AppEmploymentSetting(companyID, employmentCode, 
				CollectionUtil.isEmpty(listWTOAH) ? null :
					
					listWTOAH.stream().map(x -> new WorkTypeObjAppHoliday(
							x.getWorkTypeList(),
							EnumAdaptor.valueOf(x.getAppType(), ApplicationType.class),
							x.getWorkTypeSetDisplayFlg(),
							x.getHolidayAppType() == 9 ? null : x.getHolidayAppType(),
							x.getHolidayTypeUseFlg(),
							x.getSwingOutAtr() == 9 ? null : x.getSwingOutAtr()))
					.collect(Collectors.toList())
				));
	}

}
