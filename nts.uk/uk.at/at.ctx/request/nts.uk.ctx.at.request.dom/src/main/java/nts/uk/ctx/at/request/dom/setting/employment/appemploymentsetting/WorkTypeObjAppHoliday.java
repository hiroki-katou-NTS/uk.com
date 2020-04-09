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
//申請別対象勤務種類
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
//	申請別対象勤務種類
	List<String> workTypeList;
	
//	申請種類
	private ApplicationType appType;
//	表示する勤務種類を設定する
	private Boolean workTypeSetDisplayFlg;
//	休暇申請種類
	
	private Optional<HdAppType> holidayAppType;
//	休暇種類を利用しない
	private Optional<Boolean> holidayTypeUseFlg;
//	振休振出区分
	private Optional<BreakOutType> swingOutAtr;
}
