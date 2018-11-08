package nts.uk.ctx.pr.transfer.infra.repository.sourcebank;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.transfer.dom.sourcebank.TransferSourceBank;
import nts.uk.ctx.pr.transfer.dom.sourcebank.TransferSourceBankRepository;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaTransferSourceBankRepository implements TransferSourceBankRepository {

	@Override
	public void addSourceBank(TransferSourceBank sourceBank) {
		// TODO Auto-generated method stub
		
	}

}
