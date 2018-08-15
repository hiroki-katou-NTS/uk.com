package nts.uk.ctx.sys.assist.app.find.datarestoration;

import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecovery;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecoveryRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class ScreenItemFinder {
	@Inject
	private PerformDataRecoveryRepository finder;

	public ScreenItemDto getTargetById(String dataRecoveryProcessId) {
		Optional<PerformDataRecovery> optPerformData = finder.getPerformDatRecoverById(dataRecoveryProcessId);
		if (optPerformData.isPresent()) {
			return ScreenItemDto.fromDomain(optPerformData.get());
		}
		return null;
	}
}
