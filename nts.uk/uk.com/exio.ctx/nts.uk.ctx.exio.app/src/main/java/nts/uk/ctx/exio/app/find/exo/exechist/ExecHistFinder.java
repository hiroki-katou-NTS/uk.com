package nts.uk.ctx.exio.app.find.exo.exechist;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.exio.dom.exo.exechist.ExecHistResult;
import nts.uk.ctx.exio.dom.exo.exechist.ExecHistService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ExecHistFinder {
	@Inject
	private ExecHistService execHistService;

	public ExecHistResultDto getExecHist() {
		ExecHistResult execHistResult = execHistService.initScreen();
		return ExecHistResultDto.fromDomain(execHistResult);
	}

	public List<ExecHistDto> getExOutExecHistSearch(ExecHistSearchParam param) {
		String userId = AppContexts.user().userId();
		GeneralDate startDate = GeneralDate.min();
		GeneralDate endDate = GeneralDate.max();
		return execHistService
				.getExOutExecHistSearch(param.getStartDate(), param.getEndDate(), userId,
						Optional.ofNullable(param.getCondSetCd()), param.getExOutCtgIdList())
				.stream().map(domain -> ExecHistDto.fromDomain(domain)).collect(Collectors.toList());
	}
}
