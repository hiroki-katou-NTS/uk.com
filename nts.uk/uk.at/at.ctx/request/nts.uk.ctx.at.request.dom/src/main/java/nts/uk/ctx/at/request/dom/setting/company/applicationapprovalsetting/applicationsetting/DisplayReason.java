package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.setting.DisplayAtr;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.申請設定.申請理由表示
 * @author Doan Duy Hung
 *
 */
@Getter
public class DisplayReason extends AggregateRoot {
	
	/**
	 * 会社ID
	 */
	private String companyID;
	
	/**
	 * 定型理由の表示
	 */
	private DisplayAtr displayFixedReason;
	
	/**
	 * 申請理由の表示
	 */
	private DisplayAtr displayAppReason;
	
	/**
	 * 申請種類
	 */
	private ApplicationType appType;
	
	/**
	 * 休暇申請の種類
	 */
	private Optional<HolidayAppType> opHolidayAppType;
	
	public DisplayReason(String companyID, DisplayAtr displayFixedReason,
			DisplayAtr displayAppReason, ApplicationType appType,
			Optional<HolidayAppType> opHolidayAppType) {
		this.companyID = companyID;
		this.displayFixedReason = displayFixedReason;
		this.displayAppReason = displayAppReason;
		this.appType = appType;
		this.opHolidayAppType = opHolidayAppType;
	}
	
	public static DisplayReason createAppDisplayReason(String companyID, int displayFixedReason,
			int displayAppReason, int appType) {
		return new DisplayReason(
				companyID, 
				EnumAdaptor.valueOf(displayFixedReason, DisplayAtr.class), 
				EnumAdaptor.valueOf(displayAppReason, DisplayAtr.class), 
				EnumAdaptor.valueOf(appType, ApplicationType.class), 
				Optional.empty());
	}
	
	public static DisplayReason createHolidayAppDisplayReason(String companyID, int displayFixedReason,
			int displayAppReason, int holidayAppType) {
		return new DisplayReason(
				companyID, 
				EnumAdaptor.valueOf(displayFixedReason, DisplayAtr.class), 
				EnumAdaptor.valueOf(displayAppReason, DisplayAtr.class), 
				ApplicationType.ABSENCE_APPLICATION, 
				Optional.of(EnumAdaptor.valueOf(holidayAppType, HolidayAppType.class)));
	}

	public int getHolidayAppType() {
		return opHolidayAppType.map(i -> i.value).orElse(0);
	}
	
}
