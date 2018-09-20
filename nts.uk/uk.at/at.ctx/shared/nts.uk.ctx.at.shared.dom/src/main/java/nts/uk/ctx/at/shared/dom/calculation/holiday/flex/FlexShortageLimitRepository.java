package nts.uk.ctx.at.shared.dom.calculation.holiday.flex;

import java.util.Optional;

/**
 * リポジトリ：フレックス不足の繰越上限時間
 * @author shuichi_ishida
 */
public interface FlexShortageLimitRepository {

	/**
	 * 取得
	 * @param companyId 会社ID
	 * @return フレックス不足の繰越上限時間
	 */
	Optional<FlexShortageLimit> get(String companyId);
}
