package nts.uk.ctx.bs.employee.app.find.person;

import java.util.ArrayList;
import java.util.List;

import find.person.info.item.PerInfoItemDefForLayoutDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class PersonQueryResult {
	private List<PerInfoItemDefForLayoutDto> lstPerInfoItemDef;
	private Object dto;
	public PersonQueryResult(){
		this.lstPerInfoItemDef = new ArrayList<>();
		this.dto = new Object();
	}
}
