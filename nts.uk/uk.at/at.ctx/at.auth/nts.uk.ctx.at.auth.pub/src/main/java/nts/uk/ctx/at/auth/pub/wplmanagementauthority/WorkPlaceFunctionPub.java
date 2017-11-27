package nts.uk.ctx.at.auth.pub.wplmanagementauthority;

import java.util.List;
import java.util.Optional;


public interface WorkPlaceFunctionPub {
	/**
	 * get all WorkPlaceFunction
	 * @return
	 */
	List<WorkPlaceFunctionExport> getAllWorkPlaceFunction();
	/**
	 * get WorkPlaceFunction by functionNo
	 * @param functionNo
	 * @return
	 */
	Optional<WorkPlaceFunctionExport> getWorkPlaceFunctionById(int functionNo);
}
