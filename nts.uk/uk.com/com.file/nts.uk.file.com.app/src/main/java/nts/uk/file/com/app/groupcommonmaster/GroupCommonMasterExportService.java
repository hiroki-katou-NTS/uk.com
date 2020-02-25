package nts.uk.file.com.app.groupcommonmaster;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.bs.employee.dom.groupcommonmaster.GroupCommonMaster;
import nts.uk.ctx.bs.employee.dom.groupcommonmaster.GroupCommonMasterDomainService;
import nts.uk.ctx.bs.employee.dom.groupcommonmaster.GroupCommonMasterItem;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class GroupCommonMasterExportService extends ExportService<Object> {

	@Inject
	private GroupCommonMasterExportGenerator generator;

	@Inject
	private GroupCommonMasterDomainService service;
	
	@Inject
	private CompanyAdapter company;

	@Override
	protected void handle(ExportServiceContext<Object> context) {
		String contractCode = AppContexts.user().contractCode();
		List<GroupCommonMaster> data = this.service.getGroupCommonMaster(contractCode);
		data.forEach(x -> {

			List<GroupCommonMasterItem> commonMasterItems = this.service.getGroupCommonMasterItem(contractCode,
					x.getCommonMasterId());

			x.setCommonMasterItems(commonMasterItems);
		});

		this.generator.generate(context.getGeneratorContext(), this.company.getCurrentCompany(), data);
	}

}
