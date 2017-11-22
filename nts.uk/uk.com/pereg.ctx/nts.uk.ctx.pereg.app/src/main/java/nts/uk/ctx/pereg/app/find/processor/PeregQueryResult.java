package nts.uk.ctx.pereg.app.find.processor;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemData;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.PersonInfoItemData;

@Data
public class PeregQueryResult {
	/**
	 * List<emp Optional data>
	 */
	private List<EmpInfoItemData> empOptionalData;
	
	/**
	 * List<per Optional data>
	 */
	private List<PersonInfoItemData> perOptionalData;
	
	/**
	 * Object data by each domain
	 */
	private Object dto;
}
