package nts.uk.ctx.pr.transfer.infra.repository.bank;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.transfer.dom.bank.Bank;
import nts.uk.ctx.pr.transfer.dom.bank.BankBranch;
import nts.uk.ctx.pr.transfer.dom.bank.BankBranchRepository;
import nts.uk.ctx.pr.transfer.dom.bank.BankCode;
import nts.uk.ctx.pr.transfer.dom.bank.BankRepository;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaBankAndBranchRepository implements BankRepository, BankBranchRepository {

	@Override
	public Optional<BankBranch> findBranch(String companyId, String branchId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BankBranch> findAllBranch(String companyId, BankCode bankCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(BankBranch bank) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(BankBranch bank) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String companyId, String branchId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAll(String companyId, List<String> branchIdList) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean checkExists(String companyCode, String bankCode, String branchCode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Bank> findAllBank(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Bank> findBank(String companyId, String bankCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(Bank bank) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Bank bank) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String companyId, List<String> bankCode) {
		// TODO Auto-generated method stub

	}

}
