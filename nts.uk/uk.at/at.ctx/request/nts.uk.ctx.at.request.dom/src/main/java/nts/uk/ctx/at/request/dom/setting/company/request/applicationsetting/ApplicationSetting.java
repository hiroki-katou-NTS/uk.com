package nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.applimitset.AppLimitSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.AppTypeSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.ReceptionRestrictionSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.deadlinesetting.AppDeadlineSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.displaysetting.AppDisplaySetting;

/**
 * 申請設定
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
public class ApplicationSetting extends DomainObject {
	
	/**
	 * 承認ルートの基準日
	 */
	private RecordDate recordDate;
	
	/**
	 * 申請表示設定
	 */
	private AppDisplaySetting appDisplaySetting;
	
	/**
	 * 受付制限設定
	 */
	private List<ReceptionRestrictionSetting> receptionRestrictionSetting;
	
	/**
	 * 申請種類別設定
	 */
	private List<AppTypeSetting> appTypeSetting;
	
	/**
	 * 申請制限設定
	 */
	private AppLimitSetting appLimitSetting;
	
	/**
	 * 申請締切設定
	 */
	private List<AppDeadlineSetting> listAppDeadlineSetting;
	
}
