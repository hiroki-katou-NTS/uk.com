package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

import java.util.Optional;

/**
 * リポジトリ：労働時間の加算設定
 * @author shuichi_ishida
 */
public interface AddSettingOfWorkingTimeRepository {
	Optional<AddSettingOfWorkingTime> findByCIDAndLabor(String companyId, int laborSystemAtr);
}
