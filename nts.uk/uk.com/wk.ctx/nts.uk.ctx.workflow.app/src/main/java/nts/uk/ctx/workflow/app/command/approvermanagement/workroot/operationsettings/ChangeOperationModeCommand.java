package nts.uk.ctx.workflow.app.command.approvermanagement.workroot.operationsettings;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ItemNameInformation;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.OperationMode;

@Getter
public class ChangeOperationModeCommand {
	
	/** 変更後の運用モード */
	private Integer opeMode;
	
	/** 項目の名称情報 */
	private ItemNameInformationDto itemNameInfor;
	
	public OperationMode getOperationMode() {
		return EnumAdaptor.valueOf(this.opeMode, OperationMode.class);
	}
	
	public ItemNameInformation getItemNameInformation() {
		return this.itemNameInfor.toValueObject();
	}
	
}
