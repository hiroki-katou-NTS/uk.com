package nts.uk.ctx.basic.dom.organization.position;


import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.organization.positionhistory.PositionHistory;
import nts.uk.ctx.basic.dom.organization.positionreference.PositionReference;





public interface PositionRepository {

//	Optional<Position> find(String companyCode, String jobCode, String historyID);
	
	List<Position> findAllPosition(String companyCode, String historyID);
	
	/*
	 * add,update,delete position
	 */
	void add(Position position);
	
	void update (Position position);
	
	void delete (String companyCode,JobCode jobCode,String historyId);
	
	/*
	 * add, update, delete history
	 */
	
	void addHistory(PositionHistory history);
	
	void updateHistory(PositionHistory history);
	
	void deleteHist(String companyCode, String historyID);

	List<PositionHistory> getAllHistory(String companyCode);
	
	List<PositionReference> findAllJobTitleRef(String companyCode, String historyID);

	Optional<Position> findSingle(String companyCode, String historyID, JobCode jobCode);

	Optional<PositionHistory> findSingleHistory(String companyCode, String historyID);

	boolean isExisted(String companyCode, JobCode jobCode,String historyID);

	boolean isExist(String companyCode, GeneralDate startDate);



}
