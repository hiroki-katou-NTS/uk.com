package nts.uk.ctx.exio.app.command.input.transfer;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.canonicalize.ImportingMode;

@Value
public class TransferCanonicalDataCommand {

	/** 会社ID */
	String companyId;
	
	/** 受入設定コード */
	String settingCode;
	
	/** 受入グループID */
	int groupId;
	
	/** 受入モード */
	ImportingMode mode;
}
