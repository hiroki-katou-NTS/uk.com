package nts.uk.ctx.sys.assist.app.find.resultofdeletion;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.uk.ctx.sys.assist.app.find.params.LogDataParams;
import nts.uk.ctx.sys.assist.dom.deletedata.ResultDeletionRepository;
import nts.uk.shr.com.context.AppContexts;


public class ResultOfDeletionFinder {
	@Inject
	private ResultDeletionRepository finder;
	
	public List<ResultOfDeletionDto> getResultOfDeletion (LogDataParams logDataParams) {
		String companyId = AppContexts.user().companyId();
		return finder.getResultOfDeletion(
				companyId,
				logDataParams.getStartDateOperator(),
				logDataParams.getEndDateOperator(),
				logDataParams.getListOperatorEmployeeId()
				).stream()
				.map(item -> ResultOfDeletionDto.fromDomain(item))
				.collect(Collectors.toList());
	}
}
