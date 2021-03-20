package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareNurseUpperLimit;

/**
 * 子の看護介護計算残数
 * @author yuri_tamakoshi
 */
@Getter
@Setter
public class ChildCareNurseRemainingNumberCalcWork {
	/** 残数 */
	private  ChildCareNurseRemainingNumber remainNumber;
	/** 上限日数 */
	private ChildCareNurseUpperLimit upperLimit;

	/**
	 * コンストラクタ
	 */
	public ChildCareNurseRemainingNumberCalcWork(){

		this.remainNumber = new ChildCareNurseRemainingNumber();
		this.upperLimit = new ChildCareNurseUpperLimit(0.0);
	}

	/**
	 * ファクトリー
	 * @param remainNumber 残数
	 * @param upperLimit 上限日数
	 * @return 子の看護介護計算残数数
	*/
	public static ChildCareNurseRemainingNumberCalcWork of (
			ChildCareNurseRemainingNumber remainNumber,
			ChildCareNurseUpperLimit upperLimit) {

		ChildCareNurseRemainingNumberCalcWork domain = new ChildCareNurseRemainingNumberCalcWork();
		domain.remainNumber = remainNumber;
		domain.upperLimit = upperLimit;
		return domain;
	}
}
