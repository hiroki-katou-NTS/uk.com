package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class DivergenceTimeErrorCancelMethod.
 */
//乖離時間のエラーの解除方法
//UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.乖離時間.乖離時間枠
@Getter
@Setter
public class DivergenceTimeErrorCancelMethod {

	/** The reason inputed. */
	//乖離理由が入力された場合、エラーを解除する
	private boolean reasonInputed;
	

	/** The reason selected. */
	//乖離理由が選択された場合、エラーを解除する
	private boolean reasonSelected;

	/**
	 * Instantiates a new divergence time error cancel method.
	 *
	 * @param reasonInputed
	 *            the reason inputed
	 * @param reasonSelected
	 *            the reason selected
	 */
	public DivergenceTimeErrorCancelMethod(int reasonInputed, int reasonSelected) {

		this.reasonInputed = reasonInputed == 1;

		this.reasonSelected = reasonSelected == 1;

	}

	/**
	 * Instantiates a new divergence time error cancel method.
	 */
	public DivergenceTimeErrorCancelMethod() {

	}

}
