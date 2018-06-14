package nts.uk.ctx.sys.assist.app.find.datarestoration;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecoveryRepository;

@Stateless
public class ScreenItemFinder {
	@Inject
	private PerformDataRecoveryRepository finder;

	public ScreenItemDto getTargetById(String dataRecoveryProcessId) {
		return ScreenItemDto.fromDomain(finder.getPerformDatRecoverById(dataRecoveryProcessId).get());
	}
}
