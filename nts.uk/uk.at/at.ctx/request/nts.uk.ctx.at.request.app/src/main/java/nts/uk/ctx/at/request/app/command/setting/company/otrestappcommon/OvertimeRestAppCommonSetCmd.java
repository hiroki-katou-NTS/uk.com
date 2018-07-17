package nts.uk.ctx.at.request.app.command.setting.company.otrestappcommon;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * 
 * @author yennth
 *
 */
@Data
@AllArgsConstructor
public class OvertimeRestAppCommonSetCmd {
	private int appType;
	/**
	 * 加給時間表示区分
	 */
	private int bonusTimeDisplayAtr;
	
	/**
	 * 乖離理由定型区分
	 */
	private int divergenceReasonFormAtr;
	
	/**
	 * 乖離理由入力区分
	 */
	private int divergenceReasonInputAtr;
	
	/**
	 * 実績表示区分
	 */
	private int performanceDisplayAtr;
	
	/**
	 * 事前表示区分
	 */
	private int preDisplayAtr;
	
	/**
	 * 計算残業時間表示区分
	 */
	private int calculationOvertimeDisplayAtr;
	
	/**
	 * 時間外表示区分
	 */
	private int extratimeDisplayAtr;
	
	/**
	 * 事前超過表示設定
	 */
	private int preExcessDisplaySetting;
	
	/**
	 * 実績超過区分
	 */
	private int performanceExcessAtr;
	
	/**
	 * 時間外超過区分
	 */
	private int extratimeExcessAtr;
	
	/**
	 * 申請日矛盾区分
	 */
	private int appDateContradictionAtr;
}
