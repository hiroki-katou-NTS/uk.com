package nts.uk.ctx.sys.assist.app.find.mastercopy;

import java.util.Optional;

import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyData;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataRepository;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyTarget;

/**
 * The Class MasterCopyDataFinder.
 */
public class MasterCopyDataFinder {

	/** The repository. */
	@Inject
	private MasterCopyDataRepository repository;

	/**
	 * Find by master copy data.
	 *
	 * @param masterCopyId the master copy id
	 * @return the master copy data find dto
	 */
	public MasterCopyDataFindDto findByMasterCopyData(String masterCopyId) {
		Optional<MasterCopyData> opt = repository.findByMasterCopyId(masterCopyId);
		if (opt.isPresent()) {
			MasterCopyDataFindDto dto = new MasterCopyDataFindDto(opt.get().getMasterCopyId(),
					opt.get().getMasterCopyTarget());
			return dto;
		}
		return null;
	}

}
