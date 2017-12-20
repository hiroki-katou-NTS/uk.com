package nts.uk.ctx.pereg.app.find.processor;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.pereg.dom.person.additemdata.item.EmpInfoItemData;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.PersonInfoItemData;

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
	
	@SuppressWarnings("unchecked")
	public static PeregQueryResult toObject(Object obj){
		PeregQueryResult self = new PeregQueryResult();
		try {
			self.empOptionalData = (List<EmpInfoItemData>)obj.getClass().getDeclaredField("empOptionalData").get(obj);
			self.perOptionalData = (List<PersonInfoItemData>)obj.getClass().getDeclaredField("perOptionalData").get(obj);
			self.dto = obj.getClass().getField("dto").get(obj);
		} catch (Exception e) {
			return null;
		}
		return self;
	}
}
