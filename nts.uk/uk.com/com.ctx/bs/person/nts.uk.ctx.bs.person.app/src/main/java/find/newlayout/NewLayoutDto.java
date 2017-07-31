/**
 * 
 */
package find.newlayout;

import lombok.Value;
import nts.uk.ctx.bs.person.dom.person.newlayout.NewLayout;

/**
 * @author laitv
 *
 */
@Value
public class NewLayoutDto {

	String companyId;
	String newLayoutID;
	String layoutCode;
	String layoutName;

	public static NewLayoutDto fromDomain(NewLayout domain) {
		return new NewLayoutDto(domain.getCompanyId(), domain.getNewLayoutID(), domain.getLayoutCode().v(),
				domain.getLayoutName().v());
	}

}
