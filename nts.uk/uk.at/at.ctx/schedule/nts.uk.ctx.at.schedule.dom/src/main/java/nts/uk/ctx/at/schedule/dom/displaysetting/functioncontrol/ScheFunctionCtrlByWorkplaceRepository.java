package nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol;

import java.util.Optional;

public interface ScheFunctionCtrlByWorkplaceRepository {

	/**
	 * @param companyId 会社ID
	 * @return
	 */
	Optional<ScheFunctionCtrlByWorkplace> get(String companyId);
	
	/**
	 * @param companyId 会社ID
	 * @param domain スケジュール修正職場別の機能制御
	 */
	void insert(String companyId, ScheFunctionCtrlByWorkplace domain);
	
	/**
	 * @param companyId 会社ID
	 * @param domain スケジュール修正職場別の機能制御
	 */
	void update(String companyId, ScheFunctionCtrlByWorkplace domain);
	
}
