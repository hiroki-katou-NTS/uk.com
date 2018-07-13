package nts.uk.ctx.exio.app.find.exo.exechist;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.app.find.exo.category.ExOutCtgDto;
import nts.uk.ctx.exio.app.find.exo.condset.CondSetDto;
import nts.uk.ctx.exio.dom.exo.exechist.CondSetAndCtg;
import nts.uk.ctx.exio.dom.exo.exechist.ExecHistService;

@Stateless
public class ExecHistFinder {
	@Inject
	private ExecHistService execHistService;

	public CondSetAndCtgDto getExOutCondSetList() {
		CondSetAndCtgDto result = new CondSetAndCtgDto();
		CondSetAndCtg condSetAndCtg = execHistService.getExOutCondSetList();
		result.setCondSetList(condSetAndCtg.getCondSetList().stream().map(domain -> CondSetDto.fromDomain(domain))
				.collect(Collectors.toList()));
		result.setExOutCtgList(condSetAndCtg.getExOutCtgList().stream().map(domain -> ExOutCtgDto.fromDomain(domain))
				.collect(Collectors.toList()));
		return result;
	}
}
