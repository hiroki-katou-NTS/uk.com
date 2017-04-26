package nts.uk.ctx.sys.portal.dom.layout;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.sys.portal.dom.layout.enums.PGType;

/**
 * @author LamDT
 */
@Value
@EqualsAndHashCode(callSuper = false)
public class Layout extends AggregateRoot {

	/** Company ID */
	String companyID;

	/** Layout GUID */
	String layoutID;

	/** Enum PG Type */
	PGType pgType;

	public static Layout createFromJavaType(String companyID, int pgType) {
		String layoutID = IdentifierUtil.randomUniqueId();
		return new Layout(companyID, layoutID, EnumAdaptor.valueOf(pgType, PGType.class));
	}

}