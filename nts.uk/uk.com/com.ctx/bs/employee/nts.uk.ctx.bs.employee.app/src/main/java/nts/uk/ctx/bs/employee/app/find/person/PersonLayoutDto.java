package nts.uk.ctx.bs.employee.app.find.person;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.PersonInfoItemData;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonLayoutDto {
	private PersonPeregDto dto;
	private List<PersonInfoItemData> perOptionalData;
}
