/**
 * author hieult
 */
package nts.uk.ctx.sys.portal.dom.titlemenu;

import lombok.EqualsAndHashCode;

import lombok.Value;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.sys.portal.dom.titlemenu.primitive.Name;
import nts.uk.ctx.sys.portal.dom.titlemenu.primitive.TitleMenuCD;

@Value
@EqualsAndHashCode(callSuper = false)
public class TitleMenu extends AggregateRoot {
	/** Company ID */
	private final String companyID;

	/** TitleMenuCD */
	private TitleMenuCD titleMenuCD;
	
	/** LayoutID */
	private String layoutID;

	/** TitleMenuName */
	private Name name;

	public static TitleMenu createFromJavaType(String companyID, String titleMenuCD, String layoutID, String name) {
		return new TitleMenu(companyID, new TitleMenuCD(titleMenuCD), layoutID, new Name(name));
	}
}