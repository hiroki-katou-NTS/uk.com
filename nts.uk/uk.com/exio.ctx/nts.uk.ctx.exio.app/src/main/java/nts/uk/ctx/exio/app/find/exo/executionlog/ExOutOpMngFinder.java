package nts.uk.ctx.exio.app.find.exo.executionlog;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.executionlog.ExOutOpMngRepository;

@Stateless
/**
 * 外部出力動作管理
 */
public class ExOutOpMngFinder {

	@Inject
	private ExOutOpMngRepository finder;

	public List<ExOutOpMngDto> getAllExOutOpMng() {
		return finder.getAllExOutOpMng().stream().map(item -> ExOutOpMngDto.fromDomain(item))
				.collect(Collectors.toList());
	}

	public ExOutOpMngDto getExOutOpMngById(String storeProcessingId) {
		return finder.getExOutOpMngById(storeProcessingId).map(item -> ExOutOpMngDto.fromDomain(item)).orElse(null);
	}

}
