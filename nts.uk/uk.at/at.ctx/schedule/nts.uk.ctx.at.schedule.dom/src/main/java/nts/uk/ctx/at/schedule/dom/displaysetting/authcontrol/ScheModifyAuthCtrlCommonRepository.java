package nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol;

import java.util.List;
import java.util.Optional;

public interface ScheModifyAuthCtrlCommonRepository {
	
	/**
	 * @param domain スケジュール修正共通の権限制御
	 */
	void insert(ScheModifyAuthCtrlCommon domain);
	
	/**
	 * @param domain スケジュール修正共通の権限制御
	 */
	void update(ScheModifyAuthCtrlCommon domain);
	
	/**
	 * @param companyId 会社ID
	 * @param roleId ロールID
	 * @param functionNo 機能NO
	 * @return
	 */
	Optional<ScheModifyAuthCtrlCommon> get(String companyId, String roleId, int functionNo);
	
	/**
	 * @param companyId 会社ID
	 * @param roleId ロールID
	 * @return
	 */
	List<ScheModifyAuthCtrlCommon> getAllByRoleId(String companyId, String roleId);

}
