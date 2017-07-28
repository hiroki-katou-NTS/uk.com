/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.layoutitemclassification;

import java.math.BigDecimal;

import javax.persistence.EnumType;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.bs.person.dom.person.role.auth.category.PersonInfoAuthType;

/**
 * @author laitv
 *
 */
public class LayoutPersonInfo extends AggregateRoot {

	@Getter
	private String layoutID;
	@Getter
	private DisPOrder disPOrder;
	@Getter
	private String personInfoCategoryID;
	@Getter
	private LayoutItemType layoutItemType;

	/**
	 * @param layoutID
	 * @param disPOrder
	 * @param personInfoCategoryID
	 * @param layoutItemType
	 */
	public LayoutPersonInfo(String layoutID, DisPOrder disPOrder, String personInfoCategoryID,
			LayoutItemType layoutItemType) {
		super();
		this.layoutID = layoutID;
		this.disPOrder = disPOrder;
		this.personInfoCategoryID = personInfoCategoryID;
		this.layoutItemType = layoutItemType;
	}

	public static LayoutPersonInfo createFromJaveType(String layoutID, int disPOrder, String personInfoCategoryID,
			int layoutItemType) {
		return new LayoutPersonInfo(layoutID, new DisPOrder(new BigDecimal(disPOrder)), personInfoCategoryID,
				EnumAdaptor.valueOf(layoutItemType, LayoutItemType.class));
	}
}
