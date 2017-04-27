package nts.uk.ctx.sys.portal.dom.toppagepart.size;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.DomainObject;

/**
 * @author LamDT
 */
@Value
@EqualsAndHashCode(callSuper = false)
public class Size extends DomainObject {

	/** Width */
	Width width;

	/** Height */
	Height height;

	public static Size createFromJavaType(int width, int height) {
		return new Size(new Width(width), new Height(height));
	}
}