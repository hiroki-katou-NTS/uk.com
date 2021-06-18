package nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare;

import java.util.Optional;
import lombok.Getter;

/**
 * 子の看護介護残数
 * @author yuri_tamakoshi
*/
@Getter
public class ChildCareNurseRemainingNumberExport implements Cloneable{

	/** 日数 */
	private  Double days;
	/** 時間 */
	private Optional<Integer> time;

	/**
	 * コンストラクタ
	 */
	public ChildCareNurseRemainingNumberExport(){

		this.days = new Double(0.0);
		this.time = Optional.empty();
	}

	/**
	 * ファクトリー
	 * @param usedDays　残日数
	 * @param usedTime　残時間
	 * @return 子の看護介護残数
	*/
	public static ChildCareNurseRemainingNumberExport of (
			Double usedDays,
			Optional<Integer> usedTime) {

		ChildCareNurseRemainingNumberExport domain = new ChildCareNurseRemainingNumberExport();
		domain.days = usedDays;
		domain.time = usedTime;
		return domain;
	}
}
