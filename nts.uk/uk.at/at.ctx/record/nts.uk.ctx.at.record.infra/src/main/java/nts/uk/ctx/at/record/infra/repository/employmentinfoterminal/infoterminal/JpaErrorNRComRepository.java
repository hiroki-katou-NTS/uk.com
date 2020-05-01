package nts.uk.ctx.at.record.infra.repository.employmentinfoterminal.infoterminal;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ErrorNRCom;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.ErrorNRComRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaErrorNRComRepository implements ErrorNRComRepository {

	@Override
	public void insert(ErrorNRCom errorNR) {
		// TODO Auto-generated method stub

	}

}
