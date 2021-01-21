package nts.uk.ctx.sys.assist.app.find.resultofsaving;

import java.util.Collections;
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
import nts.uk.ctx.sys.assist.dom.storage.SysEmployeeStorageAdapter;
import nts.uk.ctx.sys.assist.dom.storage.TargetEmployees;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class StorageHistoryFinder {
	
	@Inject
	private ResultOfSavingRepository resultOfSavingRepository;
	
	@Inject
	private SysEmployeeStorageAdapter sysEmployeeStorageAdapter;
	
	public List<ResultOfSavingDto> findHistory(GetDataHistoryCommand command) {
		List<String> patternCodes = command.getObjects().stream()
					.map(FindDataHistoryDto::getPatternCode).collect(Collectors.toList());
		if (!patternCodes.isEmpty()) {
			List<ResultOfSavingDto> list = resultOfSavingRepository.getResultOfSavingBySaveSetCode(patternCodes)
					.stream()
					.filter(s -> checkDateTime(s, command.getFrom(), command.getTo()))
					.map(ResultOfSavingDto::fromDomain)
					.map(this::updatePractitioner)
					.sorted(Comparator.comparing(ResultOfSavingDto::getSaveStartDatetime))
					.collect(Collectors.toList());
			return list;
		} else return null;
	}
	
	private ResultOfSavingDto updatePractitioner(ResultOfSavingDto dto) {
		List<TargetEmployees> list = sysEmployeeStorageAdapter.getByListSid(Collections.singletonList(dto.getPractitioner()));
		if (!list.isEmpty()) {
			dto.setPractitioner(String.format("%s %s", list.get(0).getScd().v(), list.get(0).getBusinessname().v()));
		}
		return dto;
	}
	
	private boolean checkDateTime(ResultOfSaving domain, GeneralDateTime from, GeneralDateTime to) {
		return domain.getSaveStartDatetime().isPresent() ? domain.getSaveStartDatetime().get().after(from) : true
				&& domain.getSaveEndDatetime().isPresent() ? domain.getSaveEndDatetime().get().before(to) : true;
	}
}
