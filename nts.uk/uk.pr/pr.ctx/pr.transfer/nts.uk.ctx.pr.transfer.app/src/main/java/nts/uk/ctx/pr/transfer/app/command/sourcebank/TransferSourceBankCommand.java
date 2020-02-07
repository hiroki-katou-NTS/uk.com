package nts.uk.ctx.pr.transfer.app.command.sourcebank;

import java.util.ArrayList;
import java.util.List;

import lombok.Value;
import nts.uk.ctx.pr.transfer.dom.sourcebank.EntrustorInfor;
import nts.uk.ctx.pr.transfer.dom.sourcebank.TransferSourceBank;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT
 *
 */
@Value
public class TransferSourceBankCommand {

	private String code;
	private String name;
	private String branchId;
	private int accountType;
	private String accountNumber;
	private String transferRequesterName;
	private String memo;
	private String entrustorInforCode1;
	private String entrustorInforUse1;
	private String entrustorInforCode2;
	private String entrustorInforUse2;
	private String entrustorInforCode3;
	private String entrustorInforUse3;
	private String entrustorInforCode4;
	private String entrustorInforUse4;
	private String entrustorInforCode5;
	private String entrustorInforUse5;
	private boolean updateMode;

	public TransferSourceBank toDomain() {
		List<EntrustorInfor> entrustors = new ArrayList<>();
		if ((this.entrustorInforCode1 != null && !this.entrustorInforCode1.isEmpty())
				|| (this.entrustorInforUse1 != null && !this.entrustorInforUse1.isEmpty())) {
			entrustors.add(new EntrustorInfor(this.entrustorInforCode1, 1, this.entrustorInforUse1));
		}
		if ((this.entrustorInforCode2 != null && !this.entrustorInforCode2.isEmpty())
				|| (this.entrustorInforUse2 != null && !this.entrustorInforUse2.isEmpty())) {
			entrustors.add(new EntrustorInfor(this.entrustorInforCode2, 2, this.entrustorInforUse2));
		}
		if ((this.entrustorInforCode3 != null && !this.entrustorInforCode3.isEmpty())
				|| (this.entrustorInforUse3 != null && !this.entrustorInforUse3.isEmpty())) {
			entrustors.add(new EntrustorInfor(this.entrustorInforCode3, 3, this.entrustorInforUse3));
		}
		if ((this.entrustorInforCode4 != null && !this.entrustorInforCode4.isEmpty())
				|| (this.entrustorInforUse4 != null && !this.entrustorInforUse4.isEmpty())) {
			entrustors.add(new EntrustorInfor(this.entrustorInforCode4, 4, this.entrustorInforUse4));
		}
		if ((this.entrustorInforCode5 != null && !this.entrustorInforCode5.isEmpty())
				|| (this.entrustorInforUse5 != null && !this.entrustorInforUse5.isEmpty())) {
			entrustors.add(new EntrustorInfor(this.entrustorInforCode5, 5, this.entrustorInforUse5));
		}
		return new TransferSourceBank(AppContexts.user().companyId(), this.code, this.name, this.branchId,
				this.accountNumber, this.accountType, entrustors, this.transferRequesterName, this.memo);
	}

}
