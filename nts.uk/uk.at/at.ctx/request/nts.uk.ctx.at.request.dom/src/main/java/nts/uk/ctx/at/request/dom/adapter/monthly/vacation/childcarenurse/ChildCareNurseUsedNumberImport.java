package nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 子の看護介護使用数
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class ChildCareNurseUsedNumberImport {

	/** 使用日数 */
	private  Double usedDays;
	/** 使用時間 */
	private Optional<Integer> usedTime;

	/**
	 * コンストラクタ
	 */
	public ChildCareNurseUsedNumberImport(){
		this.usedDays = new Double(0.0);
		this.usedTime = Optional.empty();
	}

	/**
	 * ファクトリー
	 * @param usedDays　子の看護休暇使用日数
	 * @param usedTime　子の看護休暇使用時間
	 * @return 子の看護介護残数
	*/
	public static ChildCareNurseUsedNumberImport of (
			Double usedDays,
			Optional<Integer> usedTime) {
		ChildCareNurseUsedNumberImport imp = new ChildCareNurseUsedNumberImport();
		imp.usedDays = usedDays;
		imp.usedTime = usedTime;
		return imp;
	}
}

