package nts.uk.ctx.pereg.app.find.person;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefForLayoutDto;

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
