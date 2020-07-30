package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.request.dom.setting.UseDivision;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.打刻申請設定.打刻申請設定
 * @author Doan Duy Hung
 *
 */
//打刻申請設定
@Getter
@Setter
public class AppStampSetting implements DomainAggregate {
	
	/**
	 * 会社ID
	 */
	private String companyID;
	
	/**
	 * 応援枠の表示件数
	 */
	private SupportFrameDispNO supportFrameDispNO;
	
	/**
	 * 取消の機能の使用する
	 */
	private UseDivision useCancelFunction;
	
	/**
	 * 各種類の設定
	 */
	private List<SettingForEachType> settingForEachTypeLst;
	
	/**
	 * 外出種類の表示制御
	 */
	private List<GoOutTypeDispControl> goOutTypeDispControl;

}
