package nts.uk.ctx.sys.assist.app.find.resultofrestoration;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.app.find.params.LogDataParams;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryResultRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ResultOfRestorationFinder {
	
	@Inject
	private DataRecoveryResultRepository finder;
	
	public List<ResultOfRestorationDto> getResultOfRestoration (LogDataParams logDataParams) {
		String companyId = AppContexts.user().companyId();
		return finder.getResultOfRestoration(
				companyId,
				logDataParams.getStartDateOperator(),
				logDataParams.getEndDateOperator(),
				logDataParams.getListOperatorEmployeeId()
				).stream()
				.map(item -> ResultOfRestorationDto.fromDomain(item))
				.collect(Collectors.toList());
	}
}
