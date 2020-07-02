package nts.uk.ctx.at.request.dom.setting.workplace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.AtWorkAtr;

/**
 * 申請詳細設定
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
public class ApplicationDetailSetting extends DomainObject {
	
	/**
	 * 休憩入力欄を表示する
	 */
	private Boolean breakInputFieldDisp;
	 
	/**
	 * 休憩時間を表示する
	 */
	private Boolean breakTimeDisp;	
	 
	/**
	 * 出退勤時刻初期表示区分
	 */
	private AtWorkAtr atworkTimeBeginDisp;	
	 
	/**
	 * 実績から外出を初期表示する
	 */
	private Boolean goOutTimeBeginDisp;
	
	/**
	 * 指示が必須
	 */
	private Boolean requiredInstruction; 
	
	/**
	 * 時刻計算利用区分
	 */
	private UseAtr timeCalUse;
	 
	/**
	 * 時間入力利用区分
	 */
	private UseAtr timeInputUse;
	
	/**
	 * 退勤時刻初期表示区分
	 */
	private DisplayBreakTime timeEndDispFlg;
	
}
