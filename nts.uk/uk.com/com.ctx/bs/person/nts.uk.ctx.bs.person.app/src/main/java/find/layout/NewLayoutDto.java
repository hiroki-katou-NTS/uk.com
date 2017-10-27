/**
 * 
 */
package find.layout;

import java.util.List;

import find.layout.classification.LayoutPersonInfoClsDto;
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

	private List<LayoutPersonInfoClsDto> itemsClassification;

	public static NewLayoutDto fromDomain(NewLayout domain, List<LayoutPersonInfoClsDto> cls) {
		return new NewLayoutDto(domain.getLayoutID(), domain.getLayoutCode().v(), domain.getLayoutName().v(), cls);
	}
}
