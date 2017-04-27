package nts.uk.ctx.sys.portal.dom.toppage;


import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.sys.portal.dom.enums.TopPagePartType;
import nts.uk.ctx.sys.portal.dom.toppagepart.primitive.TopPagePartCode;
import nts.uk.ctx.sys.portal.dom.toppagepart.primitive.TopPagePartName;

/**
 * The Class TopPagePart.
 */
@Getter
public class TopPagePart extends DomainObject {
	
	/** The top page part code. */
	private TopPagePartCode topPagePartCode;

	/** The top page part name. */
	private TopPagePartName topPagePartName;

	/** The top page part type. */
	private TopPagePartType topPagePartType;

	/** The size. */
	private Size size;
}
