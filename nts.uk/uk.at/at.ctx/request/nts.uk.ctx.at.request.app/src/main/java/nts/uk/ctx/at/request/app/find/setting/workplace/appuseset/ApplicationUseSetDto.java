package nts.uk.ctx.at.request.app.find.setting.workplace.appuseset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.UseDivision;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.AppUseSetRemark;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApplicationUseSetting;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApplicationUseSetDto {
	
	/**
	 * 利用区分
	 */
	private int useDivision;
	
	/**
	 * 申請種類
	 */
	private int appType;
	
	/**
	 * 備考
	 */
	private String memo;
	
	public static ApplicationUseSetDto fromDomain(ApplicationUseSetting applicationUseSetting) {
		return new ApplicationUseSetDto(
				applicationUseSetting.getUseDivision().value, 
				applicationUseSetting.getAppType().value, 
				applicationUseSetting.getMemo().v());
	}
	
	public ApplicationUseSetting toDomain() {
		return new ApplicationUseSetting(
				EnumAdaptor.valueOf(useDivision, UseDivision.class), 
				EnumAdaptor.valueOf(appType, ApplicationType.class), 
				new AppUseSetRemark(memo));
	}
	
}
