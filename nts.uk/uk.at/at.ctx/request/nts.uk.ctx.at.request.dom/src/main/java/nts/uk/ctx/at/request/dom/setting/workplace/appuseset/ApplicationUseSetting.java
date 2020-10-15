package nts.uk.ctx.at.request.dom.setting.workplace.appuseset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.UseDivision;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.職場別設定.申請利用設定.申請利用設定
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
public class ApplicationUseSetting {
	
	/**
	 * 利用区分
	 */
	private UseDivision useDivision;
	
	/**
	 * 申請種類
	 */
	private ApplicationType appType;
	
	/**
	 * 備考
	 */
	private AppUseSetRemark memo;
	
	public static ApplicationUseSetting createNew(int useDivision, int appType, String memo) {
		return new ApplicationUseSetting(
				EnumAdaptor.valueOf(useDivision, UseDivision.class), 
				EnumAdaptor.valueOf(appType, ApplicationType.class), 
				new AppUseSetRemark(memo));
	}
	
}
