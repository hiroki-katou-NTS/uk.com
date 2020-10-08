package nts.uk.ctx.sys.assist.app.find.resultofsaving;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.app.command.datarestoration.FindDataHistoryDto;
import nts.uk.ctx.sys.assist.app.command.datarestoration.GetDataHistoryCommand;
import nts.uk.ctx.sys.assist.dom.storage.ResultOfSaving;
import nts.uk.ctx.sys.assist.dom.storage.ResultOfSavingRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class StorageHistoryFinder {
	
	@Inject
	private ResultOfSavingRepository resultOfSavingRepository;
	
	public List<ResultOfSavingDto> findHistory(GetDataHistoryCommand command) {
		List<String> patternCodes = command.getObjects().stream()
					.map(FindDataHistoryDto::getPatternCode).collect(Collectors.toList());
		List<ResultOfSavingDto> list = resultOfSavingRepository.getResultOfSavingBySaveSetCode(patternCodes)
				.stream()
				.filter(s -> checkDateTime(s, command.getFrom(), command.getTo()))
				.map(ResultOfSavingDto::fromDomain)
				.sorted(Comparator.comparing(ResultOfSavingDto::getSaveStartDatetime))
				.collect(Collectors.toList());
		return list;
	}
	
	private boolean checkDateTime(ResultOfSaving domain, GeneralDateTime from, GeneralDateTime to) {
		return domain.getSaveStartDatetime().isPresent() ? domain.getSaveStartDatetime().get().after(from) : true
				&& domain.getSaveEndDatetime().isPresent() ? domain.getSaveEndDatetime().get().before(to) : true;
	}
}
