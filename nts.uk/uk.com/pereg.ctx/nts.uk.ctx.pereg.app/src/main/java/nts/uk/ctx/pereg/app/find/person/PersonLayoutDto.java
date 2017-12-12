package nts.uk.ctx.pereg.app.find.person;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.pereg.dom.person.additemdata.item.EmpInfoItemData;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.PersonInfoItemData;

@Getter
@Setter
@NoArgsConstructor
public class PersonLayoutDto {
	
	public PeregPersonDto dto;
	
	public List<PersonInfoItemData> perOptionalData;
	
	public List<EmpInfoItemData> empOptionalData;
	
	public PersonLayoutDto(PeregPersonDto dto, List<PersonInfoItemData> perOptionalData){
		this.dto = dto;
		this.perOptionalData = perOptionalData;
	
	}
}
