package nts.uk.ctx.pr.transfer.infra.repository.bank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pr.transfer.dom.bank.Bank;
import nts.uk.ctx.pr.transfer.dom.bank.BankBranch;
import nts.uk.ctx.pr.transfer.dom.bank.BankBranchRepository;
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
		List<BankBranch> brs = this.findAllBranch(companyId, Arrays.asList("0001", "0002", "0003", "0004", "0005"));
		return brs.stream().filter(br -> br.getBranchId().equals(branchId)).findFirst();
	}

	@Override
	public List<BankBranch> findAllBranch(String companyId, List<String> bankCodes) {
		List<BankBranch> result = new ArrayList<>();
		for (String bCode : bankCodes) {
			for (int i = 1; i <= 5; i++) {
				result.add(BankBranch.createFromJavaType(companyId, "0000-" + bCode + "-0000" + i, bCode, "00" + i,
						"Bank" + bCode + "Branch" + i, "KanaNameBank" + bCode + "Branch" + i,
						"Bank" + i + "Memo Example"));
			}
		}
		return result;
	}

	@Override
	public List<BankBranch> findAllBranchByBank(String companyId, String bankCode) {
		List<BankBranch> result = new ArrayList<>();
		for (int i = 1; i <= 5; i++) {
			result.add(BankBranch.createFromJavaType(companyId, IdentifierUtil.randomUniqueId(), "00" + i,
					"Bank" + bankCode + "Branch" + i, bankCode, "KanaNameBank" + bankCode + "Branch" + i,
					"Bank" + i + "Memo Example"));
		}
		return result;
	}

	@Override
	public boolean checkExistBranch(String companyCode, String bankCode, String branchCode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Bank> findAllBank(String companyId) {
		List<Bank> result = new ArrayList<>();
		for (int i = 1; i <= 5; i++) {
			result.add(Bank.createFromJavaType(companyId, "000" + i, "Bank000" + i,
					"KanaNameBank" + i, "Bank" + i + "Memo Example"));
		}
		return result;
	}

	@Override
	public Optional<Bank> findBank(String companyId, String bankCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addBranch(BankBranch bank) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBranch(BankBranch bank) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeBranch(String companyId, String branchId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListBranchFromBank(String companyId, String bankCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addBank(Bank bank) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBank(Bank bank) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeBank(String companyId, String bankCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListBank(String companyId, List<String> bankCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkExistBank(String companyId, String bankCode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListBranch(String companyId, List<String> branchIds) {
		// TODO Auto-generated method stub
		
	}

}
