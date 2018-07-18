package nts.uk.ctx.exio.app.find.exo.exechist;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.app.find.exo.condset.CondSetDto;
import nts.uk.ctx.exio.dom.exo.exechist.CondSetAndExecHist;
import nts.uk.ctx.exio.dom.exo.exechist.ExecHistService;

@Stateless
public class ExecHistFinder {
	@Inject
	private ExecHistService execHistService;

	public CondSetAndExecHistDto getExOutCondSetAndExecHist() {
		CondSetAndExecHistDto result = new CondSetAndExecHistDto();
		CondSetAndExecHist condSetAndExecHist = execHistService.initScreen();
		result.setCondSetList(condSetAndExecHist.getCondSetList().stream().map(domain -> CondSetDto.fromDomain(domain))
				.collect(Collectors.toList()));
		result.setExecHistList(condSetAndExecHist.getExecHistList().stream()
				.map(domain -> ExecHistDto.fromDomain(domain)).collect(Collectors.toList()));
		return result;
	}
}
