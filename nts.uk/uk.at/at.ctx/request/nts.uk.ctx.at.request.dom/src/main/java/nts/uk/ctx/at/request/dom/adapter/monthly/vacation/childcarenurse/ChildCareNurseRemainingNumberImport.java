package nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse;

import lombok.Getter;

import java.util.Optional;

/**
 * 子の看護介護残数
 * @author yuri_tamakoshi
*/
@Getter
public class ChildCareNurseRemainingNumberImport implements Cloneable{

	/** 子の看護休暇使用日数 */
	private  Double usedDays;
	/** 子の看護休暇使用時間 */
	private Optional<Integer> usedTime;

	/**
	 * コンストラクタ
	 */
	public ChildCareNurseRemainingNumberImport(){

		this.usedDays = new Double(0.0);
		this.usedTime = Optional.empty();
	}

	/**
	 * ファクトリー
	 * @param usedDays　子の看護休暇使用日数
	 * @param usedTime　子の看護休暇使用時間
	 * @return 子の看護介護残数
	*/
	public static ChildCareNurseRemainingNumberImport of (
			Double usedDays,
			Optional<Integer> usedTime) {

		ChildCareNurseRemainingNumberImport domain = new ChildCareNurseRemainingNumberImport();
		domain.usedDays = usedDays;
		domain.usedTime = usedTime;
		return domain;
	}
}
