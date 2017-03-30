package nts.uk.ctx.basic.dom.organization.position;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

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
	void deleteJobTitleByHisId(String companyCode, String historyId);
	
	void addHistory(JobHistory history);
	
	void updateHistory(JobHistory history);
	
	void deleteHist(String companyCode, String historyId);

	List<JobHistory> getAllHistory(String companyCode);
	
	List<JobTitleRef> findAllJobTitleRef(String companyCode,String historyId, String jobCode);

	Optional<AuthorizationLevel> findAllAuth(String companyCode, String authScopeAtr,String authCode);


	Optional<JobTitle> findSingle(String companyCode, String historyId, JobCode jobCode);

	Optional<JobHistory> findSingleHistory(String companyCode, String historyId);

	List<JobTitle> findAll(String companyCode);
	
	boolean ExistedHistoryBydate(String companyCode, GeneralDate startDate);
	
	Optional<JobHistory> getHistoryBySdate(String companyCode, GeneralDate startDate);
	
	boolean CheckUpdateHistory (String historyId, GeneralDate startDate);

	Optional<JobHistory> getHistoryByEdate(String historyId, String endDate);

	boolean ExistedHistory(String historyId);

	void addJobTitleRef(JobTitleRef jobTitleRef);
	Optional<JobTitle> getJobTitleByCode(String companyCode, String historyId, String jobCode);

	}
