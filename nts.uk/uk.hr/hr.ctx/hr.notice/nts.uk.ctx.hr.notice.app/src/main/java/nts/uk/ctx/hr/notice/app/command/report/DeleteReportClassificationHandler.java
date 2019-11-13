package nts.uk.ctx.hr.notice.app.command.report;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.hr.notice.dom.report.PersonalReportClassification;
import nts.uk.ctx.hr.notice.dom.report.PersonalReportClassificationRepository;
import nts.uk.ctx.hr.notice.dom.report.RegisterPersonalReportItem;
import nts.uk.ctx.hr.notice.dom.report.RegisterPersonalReportItemRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DeleteReportClassificationHandler extends CommandHandler<String>{

	@Inject
	private PersonalReportClassificationRepository reportClsRepo;
	
	@Inject
	private RegisterPersonalReportItemRepository reportItemRepo;
	
	@Override
	protected void handle(CommandHandlerContext<String> context) {
		int reportClsId = Integer.valueOf(context.getCommand()).intValue();
		String cid = AppContexts.user().companyId();
		Optional<PersonalReportClassification > reportClsOpt = this.reportClsRepo.getDetailReportClsByReportClsID(Integer.valueOf(reportClsId).intValue());
		if(reportClsOpt.isPresent()) {
			reportClsOpt.get().setAbolition(true);
			this.reportClsRepo.update(reportClsOpt.get());
			List<RegisterPersonalReportItem> itemLst = this.reportItemRepo.getAllItemBy(cid, reportClsId);
			if(!CollectionUtil.isEmpty(itemLst)) {
				this.reportItemRepo.updateAll(itemLst.stream().map(c -> {
					c.setIsAbolition(Optional.of(true));
					return c;
				}).collect(Collectors.toList()));
			}
		}
	}

}
