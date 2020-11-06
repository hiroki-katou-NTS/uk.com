package nts.uk.ctx.sys.assist.app.find.deletedata;

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
import nts.uk.ctx.sys.assist.dom.deletedata.ResultDeletion;
import nts.uk.ctx.sys.assist.dom.deletedata.ResultDeletionRepository;
import nts.uk.ctx.sys.assist.dom.storage.SysEmployeeStorageAdapter;
import nts.uk.ctx.sys.assist.dom.storage.TargetEmployees;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DeletionHistoryFinder {
	
	@Inject
	private ResultDeletionRepository resultDeletionRepository;
	
	@Inject
	private SysEmployeeStorageAdapter sysEmployeeStorageAdapter;
	
	public List<DeletionHistoryDto> findHistory(GetDataHistoryCommand command) {
		List<String> delCodes = command.getObjects()
				.stream()
				.map(FindDataHistoryDto::getPatternCode)
				.collect(Collectors.toList());
		if (!delCodes.isEmpty()) {
			List<DeletionHistoryDto> list = resultDeletionRepository.getByListCodes(delCodes)
					.stream()
					.filter(s -> checkDateTime(s, command.getFrom(), command.getTo()))
					.map(DeletionHistoryDto::fromDomain)
					.map(this::updatePractitioner)
					.sorted(Comparator.comparing(DeletionHistoryDto::getStartDateTimeDel).reversed())
					.collect(Collectors.toList());
			return list;
		} else return null;
	}
	
	private DeletionHistoryDto updatePractitioner(DeletionHistoryDto dto) {
		List<TargetEmployees> list = sysEmployeeStorageAdapter.getByListSid(Collections.singletonList(dto.getPractitioner()));
		if (!list.isEmpty()) {
			dto.setPractitioner(String.format("%s %s", list.get(0).getScd().v(), list.get(0).getBusinessname().v()));
		}
		return dto;
	}
	
	private boolean checkDateTime(ResultDeletion domain, GeneralDateTime from, GeneralDateTime to) {
		return domain.getStartDateTimeDel().after(from)
				&& domain.getStartDateTimeDel().before(to);
	}
}
