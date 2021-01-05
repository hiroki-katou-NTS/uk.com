package nts.uk.screen.at.app.ksu003.start.dto;

import lombok.Value;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.TimeSpanForCalcDto;
/**
 * 勤務NOごとの変更可能な勤務時間帯
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.就業時間帯.勤務NOごとの変更可能な勤務時間帯
 * 
 * @author phongtq
 *
 */
@Value
public class ChangeableWorkingTimeDto {

	/** 勤務NO **/
	private final Integer workNo;

	/** 開始時刻の変更可能な時間帯 **/
	private final TimeSpanForCalcDto forStart;

	/** 終了時刻の変更可能な時間帯 **/
	private final TimeSpanForCalcDto forEnd;
}
