package nts.uk.ctx.at.shared.pub.remainingnumber.annualleave.empinfo.basicinfo;

import java.util.Optional;

/**
 * 年休付与テーブルを取得する
 * @author shuichi_ishida
 */
public interface GetGrantHdTblSetPub {

	/**
	 * 年休付与テーブルを取得する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @return 年休付与テーブル
	 */
	// RequestList467
	Optional<GrantHdTblSetExport> algorithm(String companyId, String employeeId);
}
