package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainValue;
/**
 * VO: 打刻ボタン
 * path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻設定.打刻入力の前準備.打刻ボタン
 * @author laitv
 *
 */
@Getter
public class StamButton implements DomainValue{
	
	/** ページNO */
	private  PageNo pageNo;
	
	/** ボタン位置NO  */
	private  ButtonPositionNo buttonPositionNo;
	
	// [C-0] 打刻ボタン(ページNO, ボタン位置NO)																							
	public StamButton(PageNo pageNo, ButtonPositionNo buttonPositionNo) {
		this.pageNo = pageNo;
		this.buttonPositionNo = buttonPositionNo;
	}
}
