package nts.uk.ctx.sys.assist.app.find.datarestoration;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecovery;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecoveryRepository;

@Stateless
public class ScreenItemFinder {
	@Inject
	private PerformDataRecoveryRepository finder;

	public List<ScreenItemDto> getTargetById(String dataRecoveryProcessId) {
		List<PerformDataRecovery> optPerformData = finder.getPerformDataByRecoveryProcessingId(dataRecoveryProcessId);
		if (!optPerformData.isEmpty()) {
			return optPerformData.stream().map(c -> ScreenItemDto.fromDomain(c)).collect(Collectors.toList());
		}
		return null;
	}

}
