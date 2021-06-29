package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
/**
 * VO: 打刻画面の日時の色設定
 * path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻設定.打刻入力の前準備.打刻画面の日時の色設定
 * @author laitv
 *
 */
@Getter
@AllArgsConstructor
public class SettingDateTimeColorOfStampScreen implements DomainValue{
	
	/** 文字色 */
	private  ColorCode textColor;
	
	// [C-0] 打刻画面の日時の色設定(文字色, 背景色)																							
}
