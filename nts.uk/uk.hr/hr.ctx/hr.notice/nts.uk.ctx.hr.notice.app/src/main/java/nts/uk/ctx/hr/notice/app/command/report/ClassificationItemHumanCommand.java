package nts.uk.ctx.hr.notice.app.command.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClassificationItemHumanCommand {
	private int dispOrder;
	private String personInfoItemDefinitionID;
	private String itemCd;
	private String itemName;
	private  boolean abolition;
	// chua dung
	//private int reflectId;
}
