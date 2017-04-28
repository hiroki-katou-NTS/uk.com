/**
 * author hieult
 */
package nts.uk.ctx.sys.portal.dom.flowmenu;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

@Value
@EqualsAndHashCode(callSuper = false)
public class FlowMenu extends AggregateRoot {

	/** CompanyID */
	private final String companyID;

	/** ToppagePartID */
	private String toppagePartID;

	/** FileID */
	private String fileID;

	/** FileName */
	private FileName fileName;

	/** DefaultClassificationAtribute */
	private DefClassAtr defClassAtr;

	public static FlowMenu createFromJavaType(String companyID, String toppagePartID, String fileID, String fileName, int defClassAtr) {
		return new FlowMenu(
			companyID, toppagePartID, fileID,
			new FileName(fileName),
			EnumAdaptor.valueOf(defClassAtr, DefClassAtr.class)
		);
	}

}