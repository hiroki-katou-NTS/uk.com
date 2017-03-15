package nts.uk.ctx.basic.dom.organization.position;
import java.util.List;
import java.util.Optional;

public interface PositionRepository {
	
//	Optional<Position> find(String companyCode, String jobCode, String historyID);
	
	List<JobTitle> findAllPosition(String companyCode, String historyId);
	
	/*
	 * add,update,delete position
	 */
	void add(JobTitle jobTitle);
	
	void update (JobTitle jobTitle);
	
	void delete (String companyCode,JobCode jobCode,String historyId);
	
	/*
	 * add, update, delete history
	 */
	
	void addHistory(JobHistory history);
	
	void updateHistory(JobHistory history);
	
	void deleteHist(String companyCode, String historyId);

	List<JobHistory> getAllHistory(String companyCode);
	
	List<JobTitleRef> findAllJobTitleRef(String companyCode, String historyId);
	
	

	Optional<JobTitle> findSingle(String companyCode, String historyId, JobCode jobCode);

	Optional<JobHistory> findSingleHistory(String companyCode, String historyId);

	List<JobTitle> findAll(String companyCode);
	Optional<JobHistory> getHistoryByEdate(String companyCode, String endDate);

	}
