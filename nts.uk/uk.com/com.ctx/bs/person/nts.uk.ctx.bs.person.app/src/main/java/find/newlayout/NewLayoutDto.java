/**
 * 
 */
package find.newlayout;

import java.util.List;

import find.layoutitemclassification.LayoutPersonInfoClsDto;
import lombok.Value;
import nts.uk.ctx.bs.person.dom.person.layout.NewLayout;

/**
 * @author laitv
 *
 */
@Value
public class NewLayoutDto {
	private String id;
	private String code;
	private String name;

	private List<LayoutPersonInfoClsDto> listItemClsDto;

	public static NewLayoutDto fromDomain(NewLayout domain, List<LayoutPersonInfoClsDto> cls) {
		return new NewLayoutDto(domain.getLayoutID(), domain.getLayoutCode().v(), domain.getLayoutName().v(), cls);
	}
}
