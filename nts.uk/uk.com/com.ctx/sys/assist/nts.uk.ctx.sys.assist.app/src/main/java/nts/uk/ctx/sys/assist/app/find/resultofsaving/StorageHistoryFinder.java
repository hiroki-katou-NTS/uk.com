package nts.uk.ctx.sys.assist.app.find.resultofsaving;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.storage.ResultOfSavingRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class StorageHistoryFinder {
	
	@Inject
	private ResultOfSavingRepository resultOfSavingRepository;
	
	public List<ResultOfSavingDto> findHistory(List<String> patternCodes) {
		return resultOfSavingRepository.getResultOfSavingBySaveSetCode(patternCodes)
				.stream()
				.map(ResultOfSavingDto::fromDomain)
				.sorted(Comparator.comparing(ResultOfSavingDto::getSaveStartDatetime))
				.collect(Collectors.toList());
	}
}
