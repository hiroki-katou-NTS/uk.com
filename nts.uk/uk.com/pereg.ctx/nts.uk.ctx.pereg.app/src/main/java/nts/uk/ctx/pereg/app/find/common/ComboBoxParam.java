package nts.uk.ctx.pereg.app.find.common;

import java.util.Date;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;

@Getter
public class ComboBoxParam {

	private ReferenceTypes comboBoxType;
	
	private boolean required;
	
	private Date standardDate;
	
	// code-name
	
	private String typeCode;
	
	// master
	
	private String masterType;
	
	private String employeeId;
	
	private boolean cps002;
	
	private String workplaceId;
	
}
