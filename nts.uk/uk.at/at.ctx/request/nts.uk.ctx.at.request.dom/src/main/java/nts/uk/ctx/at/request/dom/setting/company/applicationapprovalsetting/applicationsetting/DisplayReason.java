package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting;

import java.util.Optional;

import lombok.Getter;
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
public class DisplayReason implements DomainAggregate {
	
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
	private Optional<HolidayAppType> holidayAppType;
	
}
