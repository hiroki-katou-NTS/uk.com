package nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.holidayshipment.BreakOutType;
import nts.uk.ctx.at.request.dom.setting.company.displayname.HdAppType;
@Getter
@Setter
@NoArgsConstructor
public class WorkTypeObjAppHoliday {
	public WorkTypeObjAppHoliday(List<String> lstWorkType, ApplicationType appType, boolean workTypeSetDisplayFlg,
			Integer holidayOrPauseType, boolean holidayTypeUseFlg, Integer swingout) {
		this.workTypeList = lstWorkType;
		this.appType = appType;
		this.workTypeSetDisplayFlg = workTypeSetDisplayFlg;
		if(holidayOrPauseType == null) {
			this.holidayAppType = Optional.ofNullable(null);
		}else {
			
			this.holidayAppType = Optional.of(EnumAdaptor.valueOf(holidayOrPauseType, HdAppType.class));
		}
		this.holidayTypeUseFlg = Optional.of(holidayTypeUseFlg);
		if(swingout == null ) {
			this.swingOutAtr = Optional.ofNullable(null);
		}else {
			this.swingOutAtr = Optional.of(EnumAdaptor.valueOf(swingout, BreakOutType.class));
		}
		
	}

	List<String> workTypeList;
	
	//enum 
	private ApplicationType appType;
	
	private Boolean workTypeSetDisplayFlg;
	
	private Optional<HdAppType> holidayAppType;
	
	private Optional<Boolean> holidayTypeUseFlg;
	
	private Optional<BreakOutType> swingOutAtr;
}
