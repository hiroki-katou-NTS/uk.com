package nts.uk.ctx.sys.assist.dom.datarestoration;

import java.util.Optional;

public interface DataReEmployeeAdapter {
	/**
	 * Gets the sdata mng info.
	 *
	 * @param cid the cid
	 * @param pid the pid
	 * @return the sdata mng info
	 */
	Optional<EmployeeDataReInfoImport> getSdataMngInfo(String sid);
}
