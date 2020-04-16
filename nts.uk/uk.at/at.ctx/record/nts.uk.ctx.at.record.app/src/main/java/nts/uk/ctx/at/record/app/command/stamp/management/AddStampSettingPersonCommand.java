package nts.uk.ctx.at.record.app.command.stamp.management;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.stamp.management.ColorSetting;
import nts.uk.ctx.at.record.dom.stamp.management.CorrectionInterval;
import nts.uk.ctx.at.record.dom.stamp.management.HistoryDisplayMethod;
import nts.uk.ctx.at.record.dom.stamp.management.ResultDisplayTime;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPerson;
import nts.uk.ctx.at.record.dom.stamp.management.StampingScreenSet;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author phongtq
 *
 */
@Data
@NoArgsConstructor
public class AddStampSettingPersonCommand {
	

	/** 打刻ボタンを抑制する */
	private boolean buttonEmphasisArt;
	
	/** 打刻履歴表示方法 */
	private int historyDisplayMethod;
	
	/** 打刻画面のサーバー時刻補正間隔 */
	private int correctionInterval;
	
	/** 文字色 */
	private String textColor;
	
	/** 背景色 */
	private String backGroundColor;
	
	/** 打刻結果自動閉じる時間 */
	private int resultDisplayTime;
	
	public StampSettingPerson toDomain(){
		String companyId = AppContexts.user().companyId();
		HistoryDisplayMethod historyDisplayMethods = EnumAdaptor.valueOf(historyDisplayMethod, HistoryDisplayMethod.class);
		CorrectionInterval correctionIntervals = new CorrectionInterval(correctionInterval);
		ColorCode textColors = new ColorCode(textColor);
		ColorCode backGroundColors = new ColorCode(backGroundColor);
		ColorSetting colorSetting = new ColorSetting(textColors, backGroundColors);
		ResultDisplayTime resultDisplayTimes = new ResultDisplayTime(resultDisplayTime);
		StampingScreenSet stampingScreenSet = new StampingScreenSet(historyDisplayMethods, correctionIntervals, colorSetting, resultDisplayTimes);
		
		return new StampSettingPerson(companyId, buttonEmphasisArt, stampingScreenSet);
	}
}
