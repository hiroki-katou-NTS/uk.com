package nts.uk.ctx.basic.dom.organization.position;
import java.util.List;

public interface PositionRepository {
	
//	Optional<Position> find(String companyCode, String jobCode, String historyID);
	
	List<JobTitle> findAllPosition(String companyCode, String historyId);
	
	/*
	 * add,update,delete position
	 */
	void add(JobTitle position);
	
	void update (JobTitle position);
	
	void delete (String companyCode,JobCode jobCode,String historyId);
	
	/*
	 * add, update, delete history
	 */
	
	void addHistory(JobHistory history);
	
	void updateHistory(JobHistory history);
	
	void deleteHist(String companyCode, HistoryId historyId);

	List<JobHistory> getAllHistory(String companyCode);
	
	List<JobTitleRef> findAllJobTitleRef(String companyCode, String historyId);


	}
