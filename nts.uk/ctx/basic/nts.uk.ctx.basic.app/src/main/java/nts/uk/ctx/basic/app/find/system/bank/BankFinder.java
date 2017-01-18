package nts.uk.ctx.basic.app.find.system.bank;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.basic.app.find.system.bank.dto.BankDto;
import nts.uk.ctx.basic.dom.system.bank.BankRepository;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
public class BankFinder {
	@Inject
	private BankRepository bankRepository;
	
	public List<BankDto> findAll() {
		return this.bankRepository.findAll(AppContexts.user().companyCode())
				.stream()
				.map(x -> new BankDto(x.getBankCode().v(), x.getBankName().v()))
				.collect(Collectors.toList());
	}
}
