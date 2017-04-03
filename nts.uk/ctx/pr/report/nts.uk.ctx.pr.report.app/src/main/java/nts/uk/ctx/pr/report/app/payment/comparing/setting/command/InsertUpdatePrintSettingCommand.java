package nts.uk.ctx.pr.report.app.payment.comparing.setting.command;

import lombok.Data;

@Data
public class InsertUpdatePrintSettingCommand {
	private String plushBackColor;
	private String minusBackColor;
	private int showItemIfCfWithNull;
	private int showItemIfSameValue;
	private int showPayment;
	private int totalSet;
	private int sumEachDeprtSet;
	private int sumDepHrchyIndexSet;
	private int hrchyIndex1;
	private int hrchyIndex2;
	private int hrchyIndex3;
	private int hrchyIndex4;
	private int hrchyIndex5;
}
