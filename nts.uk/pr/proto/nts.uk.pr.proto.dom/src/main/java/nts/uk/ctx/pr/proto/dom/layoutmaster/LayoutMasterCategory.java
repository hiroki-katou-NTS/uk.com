package nts.uk.ctx.pr.proto.dom.layoutmaster;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * LayoutMasterCategory valueObject
 * @author lamvt
 *
 */
public class LayoutMasterCategory extends DomainObject {

	@Getter
	private CategoryAtr categoryAttribute;
	
	private CategoryPosition categoryPosition;
}
