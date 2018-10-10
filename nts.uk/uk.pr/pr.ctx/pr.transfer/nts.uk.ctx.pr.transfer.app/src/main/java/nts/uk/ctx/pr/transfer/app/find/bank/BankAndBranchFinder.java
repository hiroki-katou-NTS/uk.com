package nts.uk.ctx.pr.transfer.app.find.bank;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.gul.text.IdentifierUtil;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class BankAndBranchFinder {

	public List<BankDto> getAllBank() {
		String companyId = AppContexts.user().companyId();
		List<BankDto> result = new ArrayList<>();
		for (int i = 1; i <= 5; i++) {
			result.add(new BankDto("000" + i, "Bank000" + i, "KanaNameBank" + i, "Bank" + i + "Memo Example"));
		}
		return result;
	}
	
	public List<BankBranchDto> getAllBank(List<String> bankCodes) {
		String companyId = AppContexts.user().companyId();
		List<BankBranchDto> result = new ArrayList<>();
		for (String bCode : bankCodes) {
			for (int i = 1; i <= 5; i++) {
				result.add(new BankBranchDto(IdentifierUtil.randomUniqueId(), "000" + i, "Bank" + bCode + "Branch" + i, bCode, "KanaNameBank" + bCode + "Branch" + i, "Bank" + i + "Memo Example"));
			}
		}
		return result;
	}
	
	public BankBranchDto getBankBranchById(String branchId) {
		return new BankBranchDto(branchId, "002", "from server", "0002", "from server", "from server");
	}
	
}
