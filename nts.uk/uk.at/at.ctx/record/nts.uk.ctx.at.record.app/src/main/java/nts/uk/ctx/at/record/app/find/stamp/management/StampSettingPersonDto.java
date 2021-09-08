package nts.uk.ctx.at.record.app.find.stamp.management;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSettingPerson;
/**
 * 
 * @author phongtq
 *
 */
@Data
@AllArgsConstructor
public class StampSettingPersonDto {
	private String companyId;

	/** 打刻ボタンを抑制する */
	private boolean buttonEmphasisArt;
	
	/** 打刻履歴表示方法 */
	private int historyDisplayMethod;
	
	/** 打刻画面のサーバー時刻補正間隔 */
	private int correctionInterval;
	
	/** 文字色 */
	private String textColor;
	
	/** 打刻結果自動閉じる時間 */
	private int resultDisplayTime;
	

	public static StampSettingPersonDto fromDomain(StampSettingPerson settingPerson){
		return new StampSettingPersonDto(settingPerson.getCompanyId(),
				settingPerson.isButtonEmphasisArt(),
				settingPerson.getHistoryDisplayMethod().value,
				settingPerson.getStampingScreenSet().getCorrectionInterval().v(),
				settingPerson.getStampingScreenSet().getSettingDateTimeColor().getTextColor().v(),
				settingPerson.getStampingScreenSet().getResultDisplayTime().v());
	}
}
