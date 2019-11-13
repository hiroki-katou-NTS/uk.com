package nts.uk.ctx.hr.notice.app.command.report;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.hr.notice.dom.report.PersonalReportClassification;
import nts.uk.ctx.hr.notice.dom.report.PersonalReportClassificationRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class InsertLayoutReportHandler extends CommandHandlerWithResult<NewLayoutReportCommand, BundledBusinessException>{
		@Inject
		private PersonalReportClassificationRepository reportClsRepo;
	@Override
	protected BundledBusinessException handle(CommandHandlerContext<NewLayoutReportCommand> context) {
		NewLayoutReportCommand cmd = context.getCommand();
		String cid = AppContexts.user().companyId();
		PersonalReportClassification oldLayout = null;
		BundledBusinessException exceptions = BundledBusinessException.newInstance();
		int maxLayoutId = this.reportClsRepo.maxId(cid);
		return null;
	}

}
