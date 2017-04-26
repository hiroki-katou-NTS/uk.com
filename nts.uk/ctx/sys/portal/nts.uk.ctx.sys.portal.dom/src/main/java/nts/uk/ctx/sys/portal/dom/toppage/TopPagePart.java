package nts.uk.ctx.sys.portal.dom.toppage;


import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.sys.portal.dom.enums.TopPagePartType;
import nts.uk.ctx.sys.portal.dom.toppagepart.primitive.ToppagePartCode;
import nts.uk.ctx.sys.portal.dom.toppagepart.primitive.ToppagePartName;

/**
 * The Class TopPagePart.
 */
@Getter
public class TopPagePart extends DomainObject {
	
	/** The top page part code. */
	private ToppagePartCode topPagePartCode;

	/** The top page part name. */
	private ToppagePartName topPagePartName;

	/** The top page part type. */
	private TopPagePartType topPagePartType;

	/** The size. */
	private Size size;
}
