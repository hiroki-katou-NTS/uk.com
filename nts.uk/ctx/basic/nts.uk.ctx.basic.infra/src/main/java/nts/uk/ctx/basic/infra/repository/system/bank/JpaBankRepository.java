package nts.uk.ctx.basic.infra.repository.system.bank;

import java.util.List;

import javax.enterprise.context.RequestScoped;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.basic.dom.system.bank.Bank;
import nts.uk.ctx.basic.dom.system.bank.BankRepository;
import nts.uk.ctx.basic.infra.entity.system.bank.CbkmtBank;
import nts.uk.ctx.basic.infra.entity.system.bank.CbkmtBankPK;

@RequestScoped
public class JpaBankRepository extends JpaRepository implements BankRepository {
	private final String SEL_1 = "SELECT b FROM CbkmtBank b WHERE b.cbkmtBankPK.companyCode = :companyCode ";
	
	@Override
	public List<Bank> findAll(String companyCode) {
		return this.queryProxy().query(SEL_1, CbkmtBank.class)
		 .setParameter("companyCode", companyCode)
		 .getList(x -> Bank.createFromJavaType(
				 x.cbkmtBankPK.companyCode, 
				 x.cbkmtBankPK.bankCode, 
				 x.bankName, 
				 x.bankKnName, 
				 x.memo));
	}

	@Override
	public void add(Bank bank) {
		this.commandProxy().insert(toEntity(bank));
		
	}

	@Override
	public void update(Bank bank) {
		this.commandProxy().update(toEntity(bank));
	}

	@Override
	public void remove(Bank bank) {
		this.commandProxy().remove(toEntity(bank));
	}
	
	/**
	 * Convert domain to entity.
	 * @param domain Bank
	 * @return CbkmtBank
	 */
	private static CbkmtBank toEntity(Bank domain) {
		CbkmtBankPK key = new CbkmtBankPK(domain.getCompanyCode(), domain.getBankCode().v());
		
		CbkmtBank entity = new CbkmtBank(key, domain.getBankName().v(), domain.getBankNameKana().v(), domain.getMemo().v());
		
		return entity;
	}

}
