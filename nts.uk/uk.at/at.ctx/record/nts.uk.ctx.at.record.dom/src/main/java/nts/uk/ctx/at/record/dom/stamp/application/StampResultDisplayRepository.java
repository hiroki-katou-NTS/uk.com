package nts.uk.ctx.at.record.dom.stamp.application;

import java.util.List;
import java.util.Optional;

public interface StampResultDisplayRepository{
	/**
	 * insert 打刻後の実績表示
	 * @param application
	 */
	void insert(StampResultDisplay application);
	
	/**
	 * insert 打刻後の実績表示
	 * @param application
	 */
	void update(StampResultDisplay application);
	
	/**
	 * find 打刻後の実績表示
	 * @param companyId
	 * @return
	 */
	Optional<StampResultDisplay> getStampSet (String companyId);

	/**
	 * find all 打刻後の実績表示
	 * @param companyId
	 * @return
	 */
	List<StampAttenDisplay> getStampSets(String companyId);

	/**
	 * delete 打刻後の実績表示
	 */
	void delete();
}
