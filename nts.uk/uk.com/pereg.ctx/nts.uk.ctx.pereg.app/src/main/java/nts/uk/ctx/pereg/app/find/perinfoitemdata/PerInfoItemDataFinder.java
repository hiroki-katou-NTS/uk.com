package nts.uk.ctx.pereg.app.find.perinfoitemdata;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.PerInfoItemDataRepository;
import nts.uk.shr.pereg.app.find.PeregPerOptRepository;
import nts.uk.shr.pereg.app.find.dto.OptionalItemDataDto;


@Stateless
public class PerInfoItemDataFinder implements PeregPerOptRepository{

	@Inject
	private PerInfoItemDataRepository perInfoItemDataRepository;
	
	@Override
	public List<OptionalItemDataDto> getData(String recordId) {
		return perInfoItemDataRepository.getAllInfoItemByRecordId(recordId).stream()
				.map(x -> x.genToPeregDto()).collect(Collectors.toList());
	}
	
	/**
	 * phuc vu fix reponse cps003
	 * getData by recordIds
	 * getDatas
	 * @param recordIds
	 * @return
	 */
	@Override
	public Map<String, List<OptionalItemDataDto>> getDatas(List<String> recordIds) {
		List<OptionalItemDataDto>  itemDataLst = perInfoItemDataRepository.getAllInfoItemByRecordId(recordIds).stream()
				.map(c ->  c.genToPeregDto()).collect(Collectors.toList());
		return itemDataLst.stream().collect(Collectors.groupingBy(x -> x.getRecordId()));
	}

}
