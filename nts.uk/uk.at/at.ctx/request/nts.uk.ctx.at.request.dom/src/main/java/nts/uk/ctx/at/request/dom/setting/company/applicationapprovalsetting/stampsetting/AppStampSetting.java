package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.request.dom.setting.DisplayAtr;
import nts.uk.ctx.at.request.dom.setting.UseDivision;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.打刻申請設定.打刻申請設定
 * @author Doan Duy Hung
 */
//打刻申請設定
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppStampSetting extends AggregateRoot {
	
	/**
	 * 会社ID
	 */
	private String companyID;
	
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

	/** 
	 * 場所の選択を利用する
	 */
	private NotUseAtr useLocationSelection;

	/**
	 * 職場の選択を利用する
	 */
	private NotUseAtr wkpDisAtr;

	@Override
	public void validate() {
		super.validate();
		if (this.goOutTypeDispControl.stream().allMatch(displayControl -> displayControl.getDisplay() == DisplayAtr.NOT_DISPLAY)) {
			throw new BusinessException("Msg_1704");
		}
	}

}
