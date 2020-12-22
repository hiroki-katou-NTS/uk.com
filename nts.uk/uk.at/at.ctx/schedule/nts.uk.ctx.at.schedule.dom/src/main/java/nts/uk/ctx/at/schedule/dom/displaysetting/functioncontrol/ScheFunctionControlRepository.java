package nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol;

import java.util.Optional;

public interface ScheFunctionControlRepository {
	
	/**
	 * get
	 * @param companyId
	 * @return
	 */
	Optional<ScheFunctionControl> get (String companyId);
	
	/**
	 * insert(勤務予定の表示設定)
	 * @param funcCtrl
	 */
	void insert (String companyId, ScheFunctionControl funcCtrl);

	/**
	 * update(勤務予定の表示設定)
	 * @param funcCtrl
	 */
	void update (String companyId, ScheFunctionControl funcCtrl);
}
