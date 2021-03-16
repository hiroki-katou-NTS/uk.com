package nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol;

import java.util.List;
import java.util.Optional;

public interface ScheModifyAuthCtrlByWorkPlaceRepository {
	
	/**
	 * @param domain スケジュール修正職場別の権限制御
	 */
	void insert(ScheModifyAuthCtrlByWorkplace domain);
	
	/**
	 * @param domain スケジュール修正職場別の権限制御
	 */
	void update(ScheModifyAuthCtrlByWorkplace domain);
	
	/**
	 * @param companyId 会社ID
	 * @param roleId ロールID
	 * @param functionNo 機能NO
	 * @return
	 */
	Optional<ScheModifyAuthCtrlByWorkplace> get(String companyId, String roleId, int functionNo);
	
	/**
	 * @param companyId 会社ID
	 * @param roleId ロールID
	 * @return
	 */
	List<ScheModifyAuthCtrlByWorkplace> getAllByRoleId(String companyId, String roleId);

}
