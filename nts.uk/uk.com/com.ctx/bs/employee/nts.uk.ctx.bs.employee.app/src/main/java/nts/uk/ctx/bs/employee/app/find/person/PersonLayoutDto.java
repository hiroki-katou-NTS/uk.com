package nts.uk.ctx.bs.employee.app.find.person;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemData;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.PersonInfoItemData;

@Getter
@Setter
@NoArgsConstructor
public class PersonLayoutDto {
	public PersonPeregDto dto;
	public List<PersonInfoItemData> perOptionalData;
	public List<EmpInfoItemData> empOptionalData;
	public PersonLayoutDto(PersonPeregDto dto, List<PersonInfoItemData> perOptionalData){
		this.dto = dto;
		this.perOptionalData = perOptionalData;
	
	}
}
