package nts.uk.ctx.pr.transfer.app.find.sourcebank;

import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.transfer.dom.bank.Bank;
import nts.uk.ctx.pr.transfer.dom.bank.BankBranch;
import nts.uk.ctx.pr.transfer.dom.sourcebank.EntrustorInfor;
import nts.uk.ctx.pr.transfer.dom.sourcebank.TransferSourceBank;

/**
 * 
 * @author HungTT
 *
 */
@Getter
@NoArgsConstructor
public class TransferSourceBankDto {

	private String code;
	private String name;
	private String branchId;
	private String bankCode;
	private String bankName;
	private String branchCode;
	private String branchName;
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

	public TransferSourceBankDto(TransferSourceBank domain, BankBranch bankBranch, Bank bank) {
		this.code = domain.getCode().v();
		this.name = domain.getName().v();
		this.branchId = domain.getBranchId();
		this.bankCode = bankBranch == null ? null : bankBranch.getBankCode().v();
		this.bankName = bank == null ? null : bank.getBankName().v();
		this.branchCode = bankBranch == null ? null : bankBranch.getBankBranchCode().v();
		this.branchName = bankBranch == null ? null : bankBranch.getBankBranchName().v();
		this.accountType = domain.getAccountAtr().value;
		this.accountNumber = domain.getAccountNumber().v();
		this.transferRequesterName = domain.getTransferRequesterName().isPresent()
				? domain.getTransferRequesterName().get().v() : null;
		this.memo = domain.getMemo().isPresent() ? domain.getMemo().get().v() : null;
		List<EntrustorInfor> entrustorInfors = domain.getEntrustorInfor().orElse(Collections.emptyList());
		for (EntrustorInfor infor : entrustorInfors) {
			switch (infor.getEntrustorNo()) {
			case 1:
				this.entrustorInforCode1 = infor.getCode().v();
				this.entrustorInforUse1 = infor.getUse().v();
				break;
			case 2:
				this.entrustorInforCode2 = infor.getCode().v();
				this.entrustorInforUse2 = infor.getUse().v();
				break;
			case 3:
				this.entrustorInforCode3 = infor.getCode().v();
				this.entrustorInforUse3 = infor.getUse().v();
				break;
			case 4:
				this.entrustorInforCode4 = infor.getCode().v();
				this.entrustorInforUse4 = infor.getUse().v();
				break;
			default:
				this.entrustorInforCode5 = infor.getCode().v();
				this.entrustorInforUse5 = infor.getUse().v();
				break;
			}
		}
	}
}
