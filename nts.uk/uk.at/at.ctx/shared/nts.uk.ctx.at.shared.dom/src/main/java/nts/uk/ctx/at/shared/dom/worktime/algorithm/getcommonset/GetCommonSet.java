package nts.uk.ctx.at.shared.dom.worktime.algorithm.getcommonset;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;

/**
 * 就業時間帯：共通設定の取得
 * @author shuichu_ishida
 */
public interface GetCommonSet {

	/**
	 * 共通設定の取得
	 * @param companyId 会社ID
	 * @param workTimeCode 就業時間帯コード
	 * @return 就業時間帯の共通設定
	 */
	Optional<WorkTimezoneCommonSet> get(String companyId, String workTimeCode);
}
