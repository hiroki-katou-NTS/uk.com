package nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.displaysetting.DisplayAtr;

/**
 * 申請理由表示
 * @author Doan Duy Hung
 * 
 * 
 *
 */
@Getter
@AllArgsConstructor
public class DisplayReason extends AggregateRoot {
	/**
	 * 会社ID
	 */
	private String companyId;
	
	/**
	 * 休暇申請の種類
	 */
	private HolidayAppType typeOfLeaveApp;
	
	/**
	 * 定型理由の表示
	 */
	private DisplayAtr displayFixedReason;
	
	/**
	 * 申請理由の表示
	 */
	private DisplayAtr displayAppReason;
	
	public static DisplayReason toDomain(String companyId, int typeOfLeaveApp, 
			int displayFixedReason, int displayAppReason){
		return new DisplayReason(companyId,
				EnumAdaptor.valueOf(typeOfLeaveApp, HolidayAppType.class), 
				EnumAdaptor.valueOf(displayFixedReason, DisplayAtr.class), 
				EnumAdaptor.valueOf(displayAppReason, DisplayAtr.class));
	}
}
