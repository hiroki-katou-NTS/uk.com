package nts.uk.ctx.sys.assist.app.find.datarestoration;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryResultRepository;

@Stateless
public class DataRecoveryResultFinder {
	@Inject
	private DataRecoveryResultRepository dataRecoveryResultRepository;
	
	public List<SaveSetDto> getDataRecoveryResultByStartDatetime(GeneralDateTime from, GeneralDateTime to) {
		return dataRecoveryResultRepository.getDataRecoveryResultByStartDatetime(from, to)
											.parallelStream()
											.map(SaveSetDto::fromDomain)
											.collect(Collectors.toList());
	}
}
