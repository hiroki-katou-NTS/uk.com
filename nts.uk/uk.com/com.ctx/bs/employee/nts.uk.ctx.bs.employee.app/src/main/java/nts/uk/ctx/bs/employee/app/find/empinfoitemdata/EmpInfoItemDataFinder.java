package nts.uk.ctx.bs.employee.app.find.empinfoitemdata;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemDataRepository;
import nts.uk.shr.pereg.app.find.PeregEmpUserDefFinderRepository;
import nts.uk.shr.pereg.app.find.dto.EmpOptionalDto;

@Stateless
public class EmpInfoItemDataFinder implements PeregEmpUserDefFinderRepository{

	@Inject
	private EmpInfoItemDataRepository empInfoItemDataRepository;
	
	@Override
	public List<EmpOptionalDto> getEmpOptionalDto(String recordId) {
		return empInfoItemDataRepository.getAllInfoItemByRecordId(recordId).stream()
				.map(x -> x.genToPeregDto()).collect(Collectors.toList());
	}

}
