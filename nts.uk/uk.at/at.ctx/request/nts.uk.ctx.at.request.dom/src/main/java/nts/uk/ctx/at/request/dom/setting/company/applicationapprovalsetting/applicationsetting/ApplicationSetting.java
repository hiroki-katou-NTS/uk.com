package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.appdeadlineset.AppDeadlineSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.appdispset.AppDisplaySetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.AppLimitSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.ReceptionRestrictionSetting;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.申請設定.申請設定
 * @author Doan Duy Hung
 *
 */
@Getter
public class ApplicationSetting implements DomainAggregate {
	
	/**
	 * 会社ID
	 */
	private String companyID;
	
	/**
	 * 申請制限設定
	 */
	private AppLimitSetting appLimitSetting;
	
	/**
	 * 種類別設定
	 */
	private AppTypeSetting appTypeSetting;
	
	/**
	 * 代行申請で利用できる申請設定
	 */
	private AppSetForProxyApp appSetForProxyApp;
	
	/**
	 * 締切設定
	 */
	private List<AppDeadlineSetting> appDeadlineSetLst;
	
	/**
	 * 申請表示設定
	 */
	private AppDisplaySetting appDisplaySetting;
	
	/**
	 * 受付制限設定
	 */
	private ReceptionRestrictionSetting receptionRestrictionSetting;
	
	/**
	 * 承認ルートの基準日
	 */
	private RecordDate recordDate;
	
	public ApplicationSetting(String companyID, AppLimitSetting appLimitSetting,
			AppTypeSetting appTypeSetting, AppSetForProxyApp appSetForProxyApp,
			List<AppDeadlineSetting> appDeadlineSetLst, AppDisplaySetting appDisplaySetting,
			ReceptionRestrictionSetting receptionRestrictionSetting, RecordDate recordDate) {
		this.companyID = companyID;
		this.appLimitSetting = appLimitSetting;
		this.appTypeSetting = appTypeSetting;
		this.appSetForProxyApp = appSetForProxyApp;
		this.appDeadlineSetLst = appDeadlineSetLst;
		this.appDisplaySetting = appDisplaySetting;
		this.receptionRestrictionSetting = receptionRestrictionSetting;
		this.recordDate = recordDate;
	}
	
	/**
	 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.申請設定.アルゴリズム.基準日として扱う日の判定.基準日として扱う日の取得
	 * @param date
	 * @return
	 */
	public GeneralDate getBaseDate(Optional<GeneralDate> date) {
		// ドメインモデル「申請設定」．承認ルートの基準日をチェックする ( Domain model "application setting". Check base date of approval root )
		if(recordDate == RecordDate.APP_DATE){
			// 申請対象日のパラメータがあるかチェックする ( Check if there is a parameter on the application target date )
			if(date.isPresent()){
				// 基準日 = 申請対象日 ( Base date = application target date )
				return date.get();
			} else {
				// 基準日 = システム日付 ( Base date = system date )
				return GeneralDate.today();
			}
		} else {
			// 基準日 = システム日付 ( Base date = system date )
			return GeneralDate.today();
		}
	}
	
}
