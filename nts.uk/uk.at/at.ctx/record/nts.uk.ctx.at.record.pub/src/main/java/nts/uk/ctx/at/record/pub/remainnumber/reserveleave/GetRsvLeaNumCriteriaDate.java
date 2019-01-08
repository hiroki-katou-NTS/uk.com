package nts.uk.ctx.at.record.pub.remainnumber.reserveleave;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * 基準日時点の積立年休残数を取得する
 * @author shuichi_ishida
 */
public interface GetRsvLeaNumCriteriaDate {

	/**
	 * 基準日時点の積立年休残数を取得する
	 * @param employeeId 社員ID
	 * @param criteria 基準日
	 * @return 基準日時点積立年休残数リスト
	 */
	// RequestList201
	Optional<RsvLeaNumByCriteriaDate> algorithm(String employeeId, GeneralDate criteria);
}
