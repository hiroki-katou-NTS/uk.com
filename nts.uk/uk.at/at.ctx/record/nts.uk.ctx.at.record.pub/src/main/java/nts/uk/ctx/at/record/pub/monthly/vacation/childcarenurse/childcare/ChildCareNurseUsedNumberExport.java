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

	/** 日数 */
	private Double usedDay;
	/** 時間 */
	private Optional<Integer> usedTimes;

	/**
	 * コンストラクタ
	 */
	public ChildCareNurseUsedNumberExport(){
		this.usedDay = new Double(0.0);
		this.usedTimes = Optional.empty();
	}

	/**
	 * ファクトリー
	 * @param usedDay 日数
	 * @param usedTimes 時間
	 * @return 子の看護介護使用数
	*/
	public static ChildCareNurseUsedNumberExport of(
			Double usedDay,
			Optional<Integer> usedTimes){

		ChildCareNurseUsedNumberExport domain = new ChildCareNurseUsedNumberExport();
		domain.usedDay = usedDay;
		domain.usedTimes = usedTimes;
		return domain;
	}

}