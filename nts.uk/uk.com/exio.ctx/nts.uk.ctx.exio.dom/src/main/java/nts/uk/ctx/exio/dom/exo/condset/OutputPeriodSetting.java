package nts.uk.ctx.exio.dom.exo.condset;

import java.util.Optional;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/*
 * 出力期間設定
 */
@Getter
public class OutputPeriodSetting {

	/*
	 * 期間設定
	 */
	private NotUseAtr periodSetting;
	
	/*
	 * 基準日区分
	 */
	private Optional<BaseDateClassificationCode> baseDateClassification;
	
	/*
	 * 基準日指定
	 */
	private Optional<GeneralDate> baseDateSpecify;
	
	/*
	 * 終了日区分
	 */
	private Optional<EndDateClassificationCode> endDateClassification;
	
	/*
	 * 終了日指定
	 */
	private Optional<GeneralDate> endDateSpecify;
	
	/*
	 * 終了日調整
	 */
	private Optional<Integer> endDateAdjustment;
	
	/*
	 * 締め日区分
	 */
	private Optional<String> deadlineClassification;
	
	/*
	 * 開始日区分
	 */
	private Optional<StartDateClassificationCode> startDateClassification;
	
	/*
	 * 開始日指定
	 */
	private Optional<GeneralDate> startDateSpecify;
	
	/*
	 * 開始日調整
	 */
	private Optional<Integer> startDateAdjustment;
	
	public OutputPeriodSetting(
			NotUseAtr periodSetting, 
			BaseDateClassificationCode baseDateClassification, 
			GeneralDate baseDateSpecify, 
			EndDateClassificationCode endDateClassification, 
			GeneralDate endDateSpecify,
			Integer endDateAdjustment, 
			String deadlineClassification, 
			StartDateClassificationCode startDateClassification,
			GeneralDate startDateSpecify, 
			Integer startDateAdjustment) {
		super();
		this.periodSetting = periodSetting;
		this.baseDateClassification = Optional.ofNullable(baseDateClassification);
		this.baseDateSpecify = Optional.ofNullable(baseDateSpecify);
		this.endDateClassification = Optional.ofNullable(endDateClassification);
		this.endDateSpecify = Optional.ofNullable(endDateSpecify);
		this.endDateAdjustment = Optional.ofNullable(endDateAdjustment);
		this.deadlineClassification = Optional.ofNullable(deadlineClassification);
		this.startDateClassification = Optional.ofNullable(startDateClassification);
		this.startDateSpecify = Optional.ofNullable(startDateSpecify);
		this.startDateAdjustment = Optional.ofNullable(startDateAdjustment);
	}
	
}
