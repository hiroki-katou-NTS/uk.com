package nts.uk.ctx.sys.portal.dom.toppage;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.sys.portal.dom.primitive.CompanyId;
import nts.uk.ctx.sys.portal.dom.primitive.PGType;
import nts.uk.ctx.sys.portal.dom.toppage.primitive.LayoutId;

/**
 * The Class Layout.
 */
@Getter
public class Layout extends DomainObject{

	/** The layout id. */
	private LayoutId layoutId;

	/** The pg area. */
	private PGType pgType;

	/** The company id. */
	private CompanyId companyId;

	/** The Layout code. */
	private Placement placement;
}
