package nts.uk.ctx.exio.dom.exo.condset;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.外部入出力.外部出力.出力条件設定.出力条件設定.出力期間設定
 */
@Getter
@Builder
public class OutputPeriodSetting {

	/**
	 * 会社ID
	 */
	@NonNull
	private String cid;
	
	/**
	 * 期間設定
	 */
	@NonNull
	private NotUseAtr periodSetting;

	/**
	 * 条件設定コード
	 */
	@NonNull
	private ExternalOutputConditionCode conditionSetCode;
	
	/**
	 * 締め日区分
	 * ClosureId
	 */
	@NonNull
	private Optional<Integer> deadlineClassification;
	
	/**
	 * 基準日区分
	 */
	@NonNull
	private Optional<BaseDateClassificationCode> baseDateClassification;
	
	/**
	 * 基準日指定
	 */
	@NonNull
	private Optional<GeneralDate> baseDateSpecify;
	
	/**
	 * 終了日区分
	 */
	@NonNull
	private Optional<EndDateClassificationCode> endDateClassification;
	
	/**
	 * 終了日指定
	 */
	@NonNull
	private Optional<GeneralDate> endDateSpecify;
	
	/**
	 * 終了日調整
	 */
	@NonNull
	private Optional<DateAdjustment> endDateAdjustment;
	
	/**
	 * 開始日区分
	 */
	@NonNull
	private Optional<StartDateClassificationCode> startDateClassification;
	
	/**
	 * 開始日指定
	 */
	@NonNull
	private Optional<GeneralDate> startDateSpecify;
	
	/**
	 * 開始日調整
	 */
	@NonNull
	private Optional<DateAdjustment> startDateAdjustment;
	
}
