package nts.uk.ctx.basic.dom.organization.jobtitle;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;




public interface JobTitleRepository {

	/**
	 * 
	 * 
	 * @param companyCode
	 * @return
	 */
	List<JobTitle> getPositions(String companyCode);

	/**
	 * get All Item Master   
	 * 
	 * @param companyCode
	 * @param jobCode
	 * @param historyID
	 * @return list Position
	 */

	Optional<JobTitle> getPosition(String companyCode, String jobCode ,String historyID );
	

	void add(JobTitle position);

	void update(JobTitle position);

	void remove(String companyCode);

	void remove(List<JobTitle> details);
	boolean isExist(String companyCode, LocalDate startDate);
	List<JobTitle> findAll(String companyCode);

	Optional<JobTitle> findSingle(String companyCode, String historyID, JobCode jobCode);

	boolean isExisted(String companyCode, JobCode jobCode);

	void remove(String companyCode, JobCode jobCode);

	
}
