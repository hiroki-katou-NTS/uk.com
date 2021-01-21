package nts.uk.ctx.exio.app.command.exo.condset;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.exio.dom.exo.condset.OutputPeriodSetting;

/**
 * Command 出力期間設定
 */
@Data
public class SaveOutputPeriodSetCommand implements OutputPeriodSetting.MementoGetter {
	
	private Boolean isNew;
	
	/**
	 * 会社ID
	 */
	private String cid;
	
	/**
	 * 期間設定
	 */
	private int periodSetting;

	/**
	 * 条件設定コード
	 */
	private String conditionSetCode;
	
	/**
	 * 締め日区分
	 */
	private Integer closureDayAtr;
	
	/**
	 * 基準日区分
	 */
	private Integer baseDateClassification;
	
	/**
	 * 基準日指定
	 */
	private GeneralDate baseDateSpecify;
	
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
}
