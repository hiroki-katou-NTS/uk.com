package nts.uk.ctx.at.record.app.find.workrecord.erroralarm.schedule;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.FixedExtractionSMonItems;

@Data
@Builder
public class ScheFixItemMonthlyDto {
	
	/**
	 * NO:
	 * 0:スケジュール未作成
	 * 1:勤務種類未登録
	 * 2:就業時間帯未登録
	 * 3:就業時間帯未登録
	 * 4:複数回勤務
	 * 5:特定日出勤
	 * 6:特定日出勤
	 */
	private int fixedCheckDayItems;

	/**
	 * 区分:
	 * 0:エラー
	 * 1:アラーム
	 * 2:その他
	 */
	private int alarmCheckCls;
	
	/**
	 * 日別チェック名称
	 */
	private String dailyCheckName;
	
	/**
	 * 初期メッセージ
	 */
	private String initMsg;
	
	/**
	 * Convert Domain to DTO
	 * @param domain FixedExtractionSDailyItems
	 * @return ScheFixItemDayDto
	 */
	public static ScheFixItemMonthlyDto fromDomain(FixedExtractionSMonItems domain) {
		String initMessage = domain.getInitMsg() != null ? domain.getInitMsg().get().v() : "";
		
		return ScheFixItemMonthlyDto.builder()
				.fixedCheckDayItems(domain.getFixedCheckSMonItems().value)
				.alarmCheckCls(domain.getAlarmCheckCls().value)
				.dailyCheckName(domain.getDailyCheckName().v())
				.initMsg(initMessage)
				.build();
	}

}
