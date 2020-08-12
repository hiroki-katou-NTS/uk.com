package nts.uk.ctx.at.request.app.command.application.common;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.WorkTypeObjAppHoliday;
@Data
@AllArgsConstructor
public class AppEmploymentSetCommand {
//	会社ID
	private String companyID;
//	雇用区分コード
	private String employmentCode;
//	申請別対象勤務種類
	private List<WorkTypeObjAppHolidayCommand> listWTOAH;
	
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
}
