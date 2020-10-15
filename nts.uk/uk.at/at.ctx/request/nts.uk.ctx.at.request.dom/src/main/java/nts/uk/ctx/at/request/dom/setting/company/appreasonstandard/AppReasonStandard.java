package nts.uk.ctx.at.request.dom.setting.company.appreasonstandard;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請定型理由.申請定型理由
 * @author Doan Duy Hung
 *
 */
@Getter
public class AppReasonStandard implements DomainAggregate {
	
	/**
	 * 会社ID
	 */
	private String companyID;
	
	/**
	 * 申請種類
	 */
	private ApplicationType applicationType;
	
	/**
	 * 定型理由項目
	 */
	private List<ReasonTypeItem> reasonTypeItemLst;
	
	/**
	 * 休暇申請の種類
	 */
	private Optional<HolidayAppType> opHolidayAppType;
	
	public AppReasonStandard(String companyID, ApplicationType applicationType,
			List<ReasonTypeItem> reasonTypeItemLst, Optional<HolidayAppType> opHolidayAppType) {
		this.companyID = companyID;
		this.applicationType = applicationType;
		this.reasonTypeItemLst = reasonTypeItemLst;
		this.opHolidayAppType = opHolidayAppType;
	}
	
}
