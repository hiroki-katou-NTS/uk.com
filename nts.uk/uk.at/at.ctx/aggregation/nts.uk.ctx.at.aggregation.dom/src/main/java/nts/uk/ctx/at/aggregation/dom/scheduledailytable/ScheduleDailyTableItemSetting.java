package nts.uk.ctx.at.aggregation.dom.scheduledailytable;

import java.util.List;
import java.util.Optional;

import lombok.Value;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 勤務計画実施表の項目設定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.勤務計画実施表.勤務計画実施表の項目設定
 * @author dan_pv
 *
 */
@Value
public class ScheduleDailyTableItemSetting {

	/**
	 * 印鑑欄
	 */
	private final ScheduleDailyTableInkanRow inkanRow;
	
	/**
	 * コメント
	 */
	private final Optional<ScheduleDailyTableComment> comment;
	
	/**
	 * 個人計
	 */
	private final List<Integer> personalCounter;
	
	/**
	 * 職場計
	 */
	private final List<Integer> workplaceCounter;
	
	/**
	 * 異動者表示
	 */
	private final NotUseAtr transferDisplay;
	
	/**
	 * 応援者の予定出力方法
	 */
	private final SupporterPrintMethod supporterSchedulePrintMethod;
	
	/**
	 * 応援者の実績出力方法
	 */
	private final SupporterPrintMethod supporterDailyDataPrintMethod;
	
	/**
	 * 複製する
	 * @return
	 */
	public ScheduleDailyTableItemSetting clone() {
		return new ScheduleDailyTableItemSetting(
						this.inkanRow.clone()
					,	this.comment
					,	this.personalCounter
					,	this.workplaceCounter
					,	this.transferDisplay
					,	this.supporterSchedulePrintMethod
					,	this.supporterDailyDataPrintMethod
				);
	}
}
