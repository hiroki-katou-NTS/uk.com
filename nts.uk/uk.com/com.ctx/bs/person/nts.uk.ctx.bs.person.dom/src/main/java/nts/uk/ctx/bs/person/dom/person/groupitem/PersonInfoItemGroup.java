/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.groupitem;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.DisPOrder;

/**
 * @author laitv
 *
 */
public class PersonInfoItemGroup extends AggregateRoot {
	@Getter
	private String personInfoItemGroupID;
	@Getter
	private String companyId;
	@Getter
	private FieldGroupName fieldGroupName;
	@Getter
	private DisPOrder disPOrder;

	/**
	 * 
	 * @param personInfoItemGroupID
	 * @param companyId
	 * @param fieldGroupName
	 * @param disPOrder
	 */
	public PersonInfoItemGroup(String personInfoItemGroupID, String companyId, FieldGroupName fieldGroupName,
			DisPOrder disPOrder) {
		super();
		this.personInfoItemGroupID = personInfoItemGroupID;
		this.companyId = companyId;
		this.fieldGroupName = fieldGroupName;
		this.disPOrder = disPOrder;
	}

	public static PersonInfoItemGroup createFromJavaType(String personInfoItemGroupID, String companyId,
			String fieldGroupName, int disPOrder) {
		return new PersonInfoItemGroup(personInfoItemGroupID, companyId, new FieldGroupName(fieldGroupName),
				new DisPOrder(new BigDecimal(disPOrder)));
	}

}
