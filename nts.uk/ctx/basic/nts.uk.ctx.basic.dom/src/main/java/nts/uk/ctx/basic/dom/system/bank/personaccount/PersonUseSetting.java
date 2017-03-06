package nts.uk.ctx.basic.dom.system.bank.personaccount;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonUseSetting {

	private int useSet;

	private int priorityOrder;

	private int paymentMethod;

	private int partialPaySet;

	private int fixAmountMny;

	private int fixRate;

	private String fromLineBankCd;

	private String toBankCd;

	private String toBranchCd;

	private int accountAtr;

	private String accountNo;

	private String accountHolderKnName;

	private String accountHolderName;

	public static PersonUseSetting createFromJavaType(int useSet, int priorityOrder, int paymentMethod,
			int partialPaySet, int fixAmountMny, int fixRate, String fromLineBankCd, String toBankCd, String toBranchCd,
			int accountAtr, String accountNo, String accountHolderKnName, String accountHolderName) {
		return new PersonUseSetting(useSet, priorityOrder, paymentMethod, partialPaySet, fixAmountMny, fixRate,
				fromLineBankCd, toBankCd, toBranchCd, accountAtr, accountNo, accountHolderKnName, accountHolderName);
	}

	public PersonUseSetting(int useSet, int priorityOrder, int paymentMethod, int partialPaySet, int fixAmountMny,
			int fixRate, String fromLineBankCd, String toBankCd, String toBranchCd, int accountAtr, String accountNo,
			String accountHolderKnName, String accountHolderName) {
		super();
		this.useSet = useSet;
		this.priorityOrder = priorityOrder;
		this.paymentMethod = paymentMethod;
		this.partialPaySet = partialPaySet;
		this.fixAmountMny = fixAmountMny;
		this.fixRate = fixRate;
		this.fromLineBankCd = fromLineBankCd;
		this.toBankCd = toBankCd;
		this.toBranchCd = toBranchCd;
		this.accountAtr = accountAtr;
		this.accountNo = accountNo;
		this.accountHolderKnName = accountHolderKnName;
		this.accountHolderName = accountHolderName;
	}
}
