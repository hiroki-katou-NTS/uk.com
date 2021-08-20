package nts.uk.ctx.at.aggregation.dom.scheduledailytable;

import java.util.List;
import java.util.Optional;

import lombok.Value;
import nts.arc.error.BusinessException;
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
	 * 作る
	 * @param inkanRow 印鑑欄
	 * @param comment コメント
	 * @param personalCounter 個人計
	 * @param workplaceCounter 職場計
	 * @param transferDisplay 異動者表示
	 * @param supporterSchedulePrintMethod 応援者の予定出力方法
	 * @param supporterDailyDataPrintMethod 応援者の実績出力方法
	 * @return
	 */
	public static ScheduleDailyTableItemSetting create(
			ScheduleDailyTableInkanRow inkanRow,
			Optional<ScheduleDailyTableComment> comment,
			List<Integer> personalCounter,
			List<Integer> workplaceCounter,
			NotUseAtr transferDisplay,
			SupporterPrintMethod supporterSchedulePrintMethod,
			SupporterPrintMethod supporterDailyDataPrintMethod) {
		
		if ( personalCounter.size() > 10 ) {
			throw new BusinessException("Msg_2083");
		}
		
		if ( workplaceCounter.size() > 5 ) {
			throw new BusinessException("Msg_2084");
		}
		
		return new ScheduleDailyTableItemSetting(inkanRow, 
				comment, 
				personalCounter, 
				workplaceCounter, 
				transferDisplay, 
				supporterSchedulePrintMethod, 
				supporterDailyDataPrintMethod);
	}
	
	/**
	 * 複製する
	 * @return
	 */
	public ScheduleDailyTableItemSetting reproduct() {
		return new ScheduleDailyTableItemSetting(
						this.inkanRow.reproduce()
					,	this.comment
					,	this.personalCounter
					,	this.workplaceCounter
					,	this.transferDisplay
					,	this.supporterSchedulePrintMethod
					,	this.supporterDailyDataPrintMethod
				);
	}
}
