package nts.uk.screen.at.app.query.kmp.kmp001.b;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.pub.person.IPersonInfoPub;
import nts.uk.ctx.bs.employee.pub.person.PersonInfoExport;

/**
 * 選択した社員情報を取得する
 * @author chungnt
 *
 */
@Stateless
public class InformationEmployee {
	
	@Inject
	private IPersonInfoPub IPersonInfoPub;
	
	public InformationEmployeeDto get(String sid) {
		PersonInfoExport personInfoExport =  IPersonInfoPub.getPersonInfo(sid);
		
		if (personInfoExport == null) {
			throw new RuntimeException("Not found");
		}

		InformationEmployeeDto dto = new InformationEmployeeDto(
				personInfoExport.getPid() == null ? "" : personInfoExport.getPid(),
				personInfoExport.getBusinessName() == null ? "" : personInfoExport.getBusinessName(),
				personInfoExport.getEntryDate(), personInfoExport.getGender(),
				personInfoExport.getBirthDay(),
				personInfoExport.getEmployeeId() == null ? sid : personInfoExport.getEmployeeId(),
				personInfoExport.getEmployeeCode() == null ? "" : personInfoExport.getEmployeeCode(),
				personInfoExport.getRetiredDate());
		
		return dto;
	}
}
