package nts.uk.ctx.at.function.ac.person;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.person.EmployeeInfoFunAdapter;
import nts.uk.ctx.at.function.dom.adapter.person.EmployeeInfoFunAdapterDto;
import nts.uk.ctx.bs.employee.pub.person.IPersonInfoPub;
import nts.uk.ctx.bs.employee.pub.person.PersonInfoExport;

@Stateless
public class EmployeeInfoFunAcFinder implements EmployeeInfoFunAdapter {

	@Inject
	private IPersonInfoPub personInfoPub;
	
	@Override
	public List<EmployeeInfoFunAdapterDto> getListPersonInfor(List<String> listSID) {
		List<EmployeeInfoFunAdapterDto> data = personInfoPub.listPersonInfor(listSID)
				.stream().map(c->convertToExport(c)).collect(Collectors.toList());
		if(data.isEmpty())
			return Collections.emptyList();
		return data;
	}
	

	private EmployeeInfoFunAdapterDto convertToExport( PersonInfoExport export) {
		return new EmployeeInfoFunAdapterDto(
				export.getPid(),
				export.getBusinessName(),
				export.getEntryDate(),
				export.getGender(),
				export.getBirthDay(),
				export.getEmployeeId(),
				export.getEmployeeCode(),
				export.getRetiredDate()
				);
		
	}
}
