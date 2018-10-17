package nts.uk.ctx.pr.transfer.ac.wageprovision.processdatecls;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.pub.wageprovision.processdatecls.CurrProcessYmExport;
import nts.uk.ctx.pr.core.pub.wageprovision.processdatecls.CurrProcessYmPub;
import nts.uk.ctx.pr.transfer.dom.adapter.wageprovision.processdatecls.CurrProcessYmAdapter;
import nts.uk.ctx.pr.transfer.dom.adapter.wageprovision.processdatecls.CurrProcessYmImport;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class CurrProcessYmAdapterImpl implements CurrProcessYmAdapter {

	@Inject
	private CurrProcessYmPub currYmPub;

	@Override
	public Optional<CurrProcessYmImport> getCurrentSalaryProcessYm(String companyId, int processCateNo) {
		Optional<CurrProcessYmExport> optEx = currYmPub.getCurrentSalaryProcessYm(companyId, processCateNo);
		if (optEx.isPresent())
			return Optional.of(fromExport(optEx.get()));
		else
			return Optional.empty();
	}

	private CurrProcessYmImport fromExport(CurrProcessYmExport export) {
		return new CurrProcessYmImport(export.getCid(), export.getProcessCateNo(), export.getCurrentYm());
	}

}
