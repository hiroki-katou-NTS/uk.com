package nts.uk.ctx.sys.assist.dom.mastercopy;

import java.util.Optional;

public interface MasterCopyDataRepository {

	/**
	 * Find by master copy id.
	 *
	 * @return the optional
	 */
	Optional<MasterCopyData> findByMasterCopyId(String masterCopyId);
}
