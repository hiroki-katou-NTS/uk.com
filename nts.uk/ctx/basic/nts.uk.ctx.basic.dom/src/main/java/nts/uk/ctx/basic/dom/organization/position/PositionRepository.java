package nts.uk.ctx.basic.dom.organization.position;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface PositionRepository {

	/**
	 * 
	 * 
	 * @param companyCode
	 * @return
	 */
	List<Position> findAll(String companyCode);

	/**
	 * get All Item Master   
	 * 
	 * @param companyCode
	 * @param jobCode
	 * @param startDate
	 * @return list Position
	 */
	List<Position> findAllByJobCode(String companyCode, String jobCode ,GeneralDate startDate);

	/**
	 * get Item Master
	 * 
	 * @param companyCode
	 * @param jobCode
	 * @param startDate
	 * @return list Position
	 */
	Optional<Position> getPosition(String companyCode, String jobCode ,GeneralDate startDate);
	
	/**
	 * Find item master
	 * @param companyCode 
	 * @param jobCode 
	 * @param startDate 
	 * @return Position
	 */
	Optional<Position> find(String companyCode, String jobCode ,GeneralDate startDate);
}
