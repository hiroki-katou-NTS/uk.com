package nts.uk.ctx.pereg.app.find.common;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;

@Getter
public class ComboBoxParam {

	private ReferenceTypes comboBoxType;
	
	private String categoryId;
	
	private boolean required;
	
	private GeneralDate standardDate;
	
	// code-name
	
	private String typeCode;
	
	// master
	
	private String masterType;
	
	private String employeeId;
	
	private String workplaceId;
	
	private GeneralDate baseDate;
	
}
