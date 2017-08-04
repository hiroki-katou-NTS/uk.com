/**
 * 
 */
package find.newlayout;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.bs.person.dom.person.newlayout.NewLayout;

/**
 * @author laitv
 *
 */
@Value
public class NewLayoutDto {
	private String id;
	private String code;
	private String name;

	private List<Object> classifications;

	public static NewLayoutDto fromDomain(NewLayout domain, List<Object> cls) {

		return new NewLayoutDto(domain.getLayoutID(), domain.getLayoutCode().v(), domain.getLayoutName().v(), cls);
	}
}
