package nts.uk.ctx.pereg.app.find.empinfoitemdata;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.dom.person.additemdata.item.EmpInfoItemDataRepository;
import nts.uk.shr.pereg.app.find.PeregEmpOptRepository;
import nts.uk.shr.pereg.app.find.dto.OptionalItemDataDto;

@Stateless
public class EmpUserDefDataFinder implements PeregEmpOptRepository{

	@Inject
	private EmpInfoItemDataRepository empInfoItemDataRepository;
	
	@Override
	public List<OptionalItemDataDto> getData(String recordId) {
		return empInfoItemDataRepository.getAllInfoItemByRecordId(recordId).stream()
				.map(x -> x.genToPeregDto()).collect(Collectors.toList());
	}

}
