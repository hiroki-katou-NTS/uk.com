package nts.uk.ctx.at.shared.dom.worktime.common.subholtransferset;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.worktime.common.DesignatedTime;

/**
 * 指定時間を取得
 * @author shuichu_ishida
 */
public interface GetDesignatedTime {

	/**
	 * 指定時間を取得
	 * @param companyId 会社ID
	 * @param workTimeCode 就業時間帯コード
	 * @return 指定時間設定
	 */
	Optional<DesignatedTime> get(String companyId, String workTimeCode);
}
