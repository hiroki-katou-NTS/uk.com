package nts.uk.ctx.bs.employee.app.find.person.item;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.person.dom.person.info.item.IsRequired;

@Data
@AllArgsConstructor
public class ItemEmpInfoItemDataDto {
	private String perInfoDefId; 
	private String recordId; 
	private String perInfoCtgId;
	private String itemName;
	private int isRequired;
	private int dataStateType;
	private String stringValue;
	private BigDecimal intValue;
	private GeneralDate dateValue;
}
