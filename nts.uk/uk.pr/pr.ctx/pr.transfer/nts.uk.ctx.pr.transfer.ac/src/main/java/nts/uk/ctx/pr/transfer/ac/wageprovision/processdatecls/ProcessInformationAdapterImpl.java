package nts.uk.ctx.pr.transfer.ac.wageprovision.processdatecls;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.pub.wageprovision.processdatecls.ProcessInformationExport;
import nts.uk.ctx.pr.core.pub.wageprovision.processdatecls.ProcessInformationPub;
import nts.uk.ctx.pr.transfer.dom.adapter.wageprovision.processdatecls.ProcessInformationAdapter;
import nts.uk.ctx.pr.transfer.dom.adapter.wageprovision.processdatecls.ProcessInformationImport;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class ProcessInformationAdapterImpl implements ProcessInformationAdapter {

	@Inject
	private ProcessInformationPub procInforPub;

	@Override
	public List<ProcessInformationImport> getProcessInformationByDeprecatedCategory(String companyId,
			int deprecateAtr) {
		return procInforPub.getProcessInformationByDeprecatedCategory(companyId, deprecateAtr).stream()
				.map(i -> this.fromExport(i)).collect(Collectors.toList());
	}

	private ProcessInformationImport fromExport(ProcessInformationExport export) {
		return new ProcessInformationImport(export.getCid(), export.getProcessCateNo(), export.getDeprecatCate().value,
				export.getProcessCls().v());
	}

}
