/**
 * author hieult
 */
package nts.uk.ctx.sys.portal.dom.flowmenu.deprecated;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.uk.ctx.sys.portal.dom.enums.TopPagePartType;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePart;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartCode;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartName;
import nts.uk.ctx.sys.portal.dom.toppagepart.size.Size;

@Getter
public class FlowMenu extends TopPagePart {

	/** FileID */
	@Setter
	private String fileID;

	/** DefaultClassificationAtribute */
	private DefClassAtr defClassAtr;
	
	/** All Agrs constructor */
	public FlowMenu(String companyID, String toppagePartID,
			TopPagePartCode code, TopPagePartName name,
			TopPagePartType type, Size size,
			String fileID, DefClassAtr defClassAtr) {
		super(companyID, toppagePartID, code, name, type, size);
		this.fileID = fileID;
		this.defClassAtr = defClassAtr;
		
	}
	
	/** Create from Java type */
	public static FlowMenu createFromJavaType(String companyID, String toppagePartID,
			String code, String name,
			int type, int width, int height,
			String fileID,  int defClassAtr ) {
		return new FlowMenu(companyID, toppagePartID, 
			new TopPagePartCode(code), new TopPagePartName(name),
			EnumAdaptor.valueOf(type, TopPagePartType.class), Size.createFromJavaType(width, height),
			fileID,
			EnumAdaptor.valueOf(defClassAtr, DefClassAtr.class
			)
		);
	}

	/** Set FlowMenu DefClassAtr */
	public void setDefClassAtr(int defClassAtr) {
		this.defClassAtr = EnumAdaptor.valueOf(defClassAtr, DefClassAtr.class);
	}
	
	@Override
	public void validate() {
		super.validate();
		if (this.getWidth().v() < 3 || this.getHeight().v() < 3)
			throw new BusinessException("Msg_1004");
	}

}