package nts.uk.ctx.basic.app.find.system.bank;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.basic.app.find.system.bank.dto.BankDto;
import nts.uk.ctx.basic.dom.system.bank.BankRepository;
import nts.uk.ctx.basic.dom.system.bank.branch.BankBranchRepository;
import nts.uk.ctx.basic.dom.system.bank.branch.BankBranch;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.ctx.basic.app.find.system.bank.dto.BranchDto;

@RequestScoped
public class BankFinder {
	@Inject
	private BankRepository bankRepository;
	
	@Inject
	private BankBranchRepository bankBranchRepository;
	
	public List<BankDto> findAll() {
		return this.bankRepository.findAll(AppContexts.user().companyCode()).stream()
				.map(x -> {
					List<BankBranch> branchs = this.bankBranchRepository.findAll(AppContexts.user().companyCode(), x.getBankCode());
					List<BranchDto> dtos = branchs.stream().map(bb -> new BranchDto(bb.getBankBranchCode().v(), bb.getBankBranchName().v(), 
							bb.getBankBranchNameKana().v(), bb.getMemo().v())).collect(Collectors.toList());
					return new BankDto(x.getBankCode().v(), x.getBankName().v(), x.getBankNameKana().v(), x.getMemo().v(), dtos);
				}).collect(Collectors.toList());
	}
}
