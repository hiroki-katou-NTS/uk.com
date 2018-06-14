package nts.uk.ctx.sys.assist.app.find.datarestoration;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecoveryRepository;

@Stateless
public class SurfaceItemFinder {
	@Inject
	private PerformDataRecoveryRepository finder;

	public List<SurfaceItemDto> getAllTableList() {
		return finder.getAllTableList().stream().map(item -> SurfaceItemDto.fromDomain(item))
				.collect(Collectors.toList());
	}

	public SurfaceItemDto getSurfaceItemById(String dataRecoveryProcessId) {
		return SurfaceItemDto.fromDomain(finder.getByRecoveryProcessingId(dataRecoveryProcessId).get(0));
	}
}
