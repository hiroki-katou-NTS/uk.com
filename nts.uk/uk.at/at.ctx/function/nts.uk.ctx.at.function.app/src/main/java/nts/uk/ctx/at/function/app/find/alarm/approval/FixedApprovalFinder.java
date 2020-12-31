package nts.uk.ctx.at.function.app.find.alarm.approval;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.app.find.alarm.checkcondition.AppApprovalFixedExtractItemDto;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.AppApprovalFixedExtractItemRepository;

@Stateless
public class FixedApprovalFinder {

	@Inject
	AppApprovalFixedExtractItemRepository repository;
	
	public List<AppApprovalFixedExtractItemDto> findAll() {
		List<AppApprovalFixedExtractItemDto> data = repository.findAll().stream()
				.map(e -> new AppApprovalFixedExtractItemDto(e.getNo(), e.getInitMessage().v(), e.getErAlAtr().value, e.getName().name))
				.collect(Collectors.toList());
		if(data.isEmpty())
			return Collections.emptyList();
		return data;
	}	
}
