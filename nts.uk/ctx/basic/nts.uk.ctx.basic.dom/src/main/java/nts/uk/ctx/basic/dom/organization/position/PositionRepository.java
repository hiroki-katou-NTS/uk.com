package nts.uk.ctx.basic.dom.organization.position;

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
}
