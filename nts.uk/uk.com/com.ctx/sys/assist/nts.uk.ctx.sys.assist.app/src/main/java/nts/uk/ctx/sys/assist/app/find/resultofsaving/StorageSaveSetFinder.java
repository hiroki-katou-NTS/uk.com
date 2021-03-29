package nts.uk.ctx.sys.assist.app.find.resultofsaving;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.app.find.datarestoration.SaveSetDto;
import nts.uk.ctx.sys.assist.dom.storage.ManualSetOfDataSave;
import nts.uk.ctx.sys.assist.dom.storage.ManualSetOfDataSaveRepository;
import nts.uk.ctx.sys.assist.dom.storage.ResultOfSaving;
import nts.uk.ctx.sys.assist.dom.storage.ResultOfSavingRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class StorageSaveSetFinder {
	
	@Inject
	private ResultOfSavingRepository resultOfSavingRepository;
	
	@Inject
	private ManualSetOfDataSaveRepository manualSetOfDataSaveRepository;
	
	public List<SaveSetDto> findSaveSet(GeneralDateTime from, GeneralDateTime to) {
		List<ResultOfSaving> ros = resultOfSavingRepository.getByStartDatetime(from, to);
		return ros.stream().map(r -> {
			Optional<ManualSetOfDataSave> mal = manualSetOfDataSaveRepository.getManualSetOfDataSaveById(r.getStoreProcessingId());
			if (mal.isPresent()) {
				return new SaveSetDto(r.getPatternCode().v(), mal.get().getSaveSetName().v());
			}
			return null;
		})
				.filter(Objects::nonNull)
				.sorted(Comparator.comparing(SaveSetDto::getPatternCode))
				.distinct()
				.collect(Collectors.toList());
	}
}
