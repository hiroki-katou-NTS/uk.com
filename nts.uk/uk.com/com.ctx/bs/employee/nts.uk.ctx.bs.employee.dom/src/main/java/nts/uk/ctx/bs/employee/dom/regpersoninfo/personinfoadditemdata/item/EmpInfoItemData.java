package nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;

@AllArgsConstructor
@NoArgsConstructor
public class EmpInfoItemData extends AggregateRoot {
	
	private String perInfoDefId;
	private String recordId;

	private String perInfoCtgId;

	private DataState dataState;
}
