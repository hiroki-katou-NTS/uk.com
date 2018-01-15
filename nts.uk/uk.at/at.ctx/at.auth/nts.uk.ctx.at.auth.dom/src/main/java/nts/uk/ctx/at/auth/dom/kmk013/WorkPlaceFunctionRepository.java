package nts.uk.ctx.at.auth.dom.kmk013;

import java.util.List;
import java.util.Optional;

public interface WorkPlaceFunctionRepository {
	/**
	 * get all WorkPlaceFunction
	 * @return
	 */
	List<WorkPlaceFunction> getAllWorkPlaceFunction();
	/**
	 * get WorkPlaceFunction by functionNo
	 * @param functionNo
	 * @return
	 */
	Optional<WorkPlaceFunction> getWorkPlaceFunctionById(int functionNo);
}
