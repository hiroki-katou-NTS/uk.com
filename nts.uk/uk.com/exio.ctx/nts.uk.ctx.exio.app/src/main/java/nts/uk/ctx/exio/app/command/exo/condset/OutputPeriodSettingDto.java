package nts.uk.ctx.exio.app.command.exo.condset;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * Dto 出力期間設定
 */
@Data
@Builder
public class OutputPeriodSettingDto {

	/**
	 * 会社ID
	 */
	private String cid;
	
	/**
	 * 期間設定
	 */
	private Integer periodSetting;

	/**
	 * 条件設定コード
	 */
	private String conditionSetCode;
	
	/**
	 * 基準日区分
	 */
	private Integer baseDateClassification;
	
	/**
	 * 基準日指定
	 */
	private GeneralDate baseDateSpecify;
	
	/**
	 * 終了日区分
	 */
	private Integer endDateClassification;
	
	/**
	 * 終了日指定
	 */
	private GeneralDate endDateSpecify;
	
	/**
	 * 終了日調整
	 */
	private Integer endDateAdjustment;
	
	/**
	 * 締め日区分
	 */
	private Integer deadlineClassification;
	
	/**
	 * 開始日区分
	 */
	private Integer startDateClassification;
	
	/**
	 * 開始日指定
	 */
	private GeneralDate startDateSpecify;
	
	/**
	 * 開始日調整
	 */
	private Integer startDateAdjustment;
	
}
