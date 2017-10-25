package nts.uk.ctx.at.record.dom.workrecord.log;

import java.util.List;
import java.util.Optional;

public interface TargetPersonRepository {
	/**
	 * get All target person
	 * @param employeeID
	 * @return
	 */
	List<TargetPerson> getAllTargetPerson(String employeeID);
	/**
	 * get target person by ID
	 * @param employeeID
	 * @param empCalAndSumExecLogId
	 * @return
	 */
	Optional<TargetPerson> getTargetPersonByID(String employeeID,String empCalAndSumExecLogId);
}
