package nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 子の看護介護使用数
 * @author yuri_tamakoshi
 */
@Getter
@Setter
@AllArgsConstructor
public class ChildCareNurseUsedNumberExport {

	/** 使用日数 */
	private  Double usedDays;
	/** 使用時間 */
	private Optional<Integer> usedTime;

	/**
	 * コンストラクタ
	 */
	public ChildCareNurseUsedNumberExport(){
		this.usedDays = new Double(0.0);
		this.usedTime = Optional.empty();
	}

	/**
	 * ファクトリー
	 * @param usedDays　使用日数
	 * @param usedTime　使用時間
	 * @return 子の看護介護使用数
	*/
	public static ChildCareNurseUsedNumberExport of (
			Double usedDays,
			Optional<Integer> usedTime) {
		ChildCareNurseUsedNumberExport exp = new ChildCareNurseUsedNumberExport();
		exp.usedDays = usedDays;
		exp.usedTime = usedTime;
		return exp;
	}
}