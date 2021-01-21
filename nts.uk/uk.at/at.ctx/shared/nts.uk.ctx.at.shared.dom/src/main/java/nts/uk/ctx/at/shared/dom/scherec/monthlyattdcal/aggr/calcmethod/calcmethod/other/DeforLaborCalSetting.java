package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other;

import java.io.Serializable;

import lombok.Getter;

/**
 * The Class DeforLaborCalSetting.
 */
@Getter
// 変形労働計算の設定
// TODO: Update CalcSettingOfIrregular
public class DeforLaborCalSetting implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	// 基準時間未満の残業時間を変形基準内残業とする
	private boolean isOtTransCriteria;

	/**
	 * Instantiates a new defor labor cal setting.
	 *
	 * @param isOtTransCriteria
	 *            the is ot trans criteria
	 */
	public DeforLaborCalSetting(boolean isOtTransCriteria) {
		super();
		this.isOtTransCriteria = isOtTransCriteria;
	}

}
