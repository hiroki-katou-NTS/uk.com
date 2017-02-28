package nts.uk.ctx.basic.dom.organization.positionhistory;

import java.util.List;
import java.util.Optional;

public interface PositionHistoryRepository {

	Optional<PositionHistory> findSingle(String companyCode, String historyID);

	void add(PositionHistory positionHistory);

	void update(PositionHistory positionHistory);

	void remove(String companyCode, String historyId);

	List<PositionHistory> findAllByHistory(String companyCode, String historyId);

	boolean isExist(String companyCode, String historyID);

}
