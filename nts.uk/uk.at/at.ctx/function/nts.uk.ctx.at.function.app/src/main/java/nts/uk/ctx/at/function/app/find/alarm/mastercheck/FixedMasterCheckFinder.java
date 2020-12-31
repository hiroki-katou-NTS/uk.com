package nts.uk.ctx.at.function.app.find.alarm.mastercheck;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.MasterCheckFixedExtractItemRepository;

@Stateless
public class FixedMasterCheckFinder {
	
	@Inject
	MasterCheckFixedExtractItemRepository repo;
	
	public List<MasterCheckFixedExtractItemDto> getAllFixedMasterCheckItem(){
		List<MasterCheckFixedExtractItemDto> data = repo.getAllFixedMasterCheckItem().stream()
				.map(c->MasterCheckFixedExtractItemDto.fromDomain(c)).collect(Collectors.toList());
		if(data.isEmpty())
			return Collections.emptyList();
		return data;
	}
}
