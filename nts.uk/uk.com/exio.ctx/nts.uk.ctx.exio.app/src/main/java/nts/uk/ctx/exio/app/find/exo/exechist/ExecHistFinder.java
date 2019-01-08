package nts.uk.ctx.exio.app.find.exo.exechist;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.uk.ctx.exio.app.find.exo.menu.RoleAuthorityDto;
import nts.uk.ctx.exio.dom.exo.exechist.ExecHistResult;
import nts.uk.ctx.exio.dom.exo.exechist.ExecHistService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ExecHistFinder {
	@Inject
	private ExecHistService execHistService;

	public ExecHistResultDto getExecHist(RoleAuthorityDto param) {
		ExecHistResult execHistResult = execHistService.initScreen(param.getInChargeRole(), param.getEmpRole());
		return ExecHistResultDto.fromDomain(execHistResult);
	}

	public List<ExecHistDto> getExOutExecHistSearch(ExecHistSearchParam param) {
		String userId = AppContexts.user().userId();
		Optional<String> condSetCd = StringUtils.isNotEmpty(param.getCondSetCd())
				? Optional.ofNullable(param.getCondSetCd()) : Optional.ofNullable(null);
		return execHistService
				.getExOutExecHistSearch(param.getStartDate(), param.getEndDate(), userId, condSetCd,
						param.getExOutCtgIdList(), param.getInChargeRole())
				.stream().map(domain -> ExecHistDto.fromDomain(domain)).collect(Collectors.toList());
	}
}
