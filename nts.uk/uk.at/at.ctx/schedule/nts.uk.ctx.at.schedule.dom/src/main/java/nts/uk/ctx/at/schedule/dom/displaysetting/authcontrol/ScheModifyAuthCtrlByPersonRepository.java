package nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol;

import java.util.List;
import java.util.Optional;

public interface ScheModifyAuthCtrlByPersonRepository {
	
	/**
	 * @param domain スケジュール修正個人別の権限制御
	 */
	void insert(ScheModifyAuthCtrlByPerson domain);
	
	/**
	 * @param domain スケジュール修正個人別の権限制御
	 */
	void update(ScheModifyAuthCtrlByPerson domain);
	
	/**
	 * @param companyId 会社ID
	 * @param roleId ロールID
	 * @param functionNo 機能NO
	 * @return
	 */
	Optional<ScheModifyAuthCtrlByPerson> get(String companyId, String roleId, int functionNo);
	
	/**
	 * @param companyId 会社ID
	 * @param roleId ロールID
	 * @return
	 */
	List<ScheModifyAuthCtrlByPerson> getAllByRoleId(String companyId, String roleId);

}
