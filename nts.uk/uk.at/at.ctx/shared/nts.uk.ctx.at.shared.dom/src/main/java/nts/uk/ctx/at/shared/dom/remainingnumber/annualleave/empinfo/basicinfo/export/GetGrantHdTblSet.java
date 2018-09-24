package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.export;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;

/**
 * 年休付与テーブルを取得する
 * @author shuichi_ishida
 */
public interface GetGrantHdTblSet {

	/**
	 * 年休付与テーブルを取得する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @return 年休付与テーブル
	 */
	// RequestList467
	Optional<GrantHdTblSet> algorithm(String companyId, String employeeId);
}
