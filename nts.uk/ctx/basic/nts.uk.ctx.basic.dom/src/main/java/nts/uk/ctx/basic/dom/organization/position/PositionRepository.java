package nts.uk.ctx.basic.dom.organization.position;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;




public interface PositionRepository {

	/**
	 * 
	 * 
	 * @param companyCode
	 * @return
	 */
	List<Position> getPositions(String companyCode);

	/**
	 * get All Item Master   
	 * 
	 * @param companyCode
	 * @param jobCode
	 * @param historyID
	 * @return list Position
	 */

	Optional<Position> getPosition(String companyCode, String jobCode ,String historyID );
	

	void add(Position position);

	void update(Position position);

	void remove(String companyCode);

	void remove(List<Position> details);
	boolean isExist(String companyCode, LocalDate startDate);
	List<Position> findAll(String companyCode);

	Optional<Position> findSingle(String companyCode, String historyID, JobCode jobCode);

	
}
