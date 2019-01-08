/**
 * 
 */
package nts.uk.ctx.pereg.app.find.layoutdef;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsDto;
import nts.uk.ctx.pereg.dom.person.layout.NewLayout;

/**
 * @author laitv
 *
 */
@Value
public class NewLayoutDto {
	private String id;
	private String code;
	private String name;
	private String wrkPlaceStartDate;

	private List<LayoutPersonInfoClsDto> itemsClassification;

	public static NewLayoutDto fromDomain(NewLayout domain, List<LayoutPersonInfoClsDto> cls, String wrkPlaceStartDate) {
		
		return new NewLayoutDto(domain.getLayoutID(), domain.getLayoutCode().v(), domain.getLayoutName().v(),wrkPlaceStartDate, cls);
	}
}
