package nts.uk.ctx.sys.portal.dom.toppage;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.sys.portal.dom.toppagepart.size.Height;
import nts.uk.ctx.sys.portal.dom.toppagepart.size.Width;

/**
 * The Class Size.
 */
@Getter
public class Size extends DomainObject{
	private Width width;

	private Height height;
}
