package nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol;

import java.util.Optional;

public interface ScheModifyWorkplaceFunctionCtrlRepository {

	/**
	 * @param companyId 会社ID
	 * @return
	 */
	Optional<ScheModifyWorkplaceFunctionCtrl> get(String companyId);
	
	/**
	 * @param companyId 会社ID
	 * @param domain スケジュール修正職場別の機能制御
	 */
	void insert(String companyId, ScheModifyWorkplaceFunctionCtrl domain);
	
	/**
	 * @param companyId 会社ID
	 * @param domain スケジュール修正職場別の機能制御
	 */
	void update(String companyId, ScheModifyWorkplaceFunctionCtrl domain);
	
}
