package nts.uk.ctx.exio.app.command.input.transfer;

import lombok.Value;

@Value
public class TransferCanonicalDataCommand {

	/** 会社ID */
	String companyId;
	
	/** 受入設定コード */
	String settingCode;
	
	/** 受入グループID */
	int groupId;
	
	/** 受入モード */
	int mode;
}
