package nts.uk.ctx.hr.notice.app.command.report;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.hr.notice.dom.report.PersonalReportClassification;
import nts.uk.ctx.hr.notice.dom.report.PersonalReportClassificationRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class SaveLayoutReportHandler extends CommandHandler<NewLayoutReportCommand>{
	@Inject
	private PersonalReportClassificationRepository reportClsRepo;
	
	@Override
	protected void handle(CommandHandlerContext<NewLayoutReportCommand> context) {
		NewLayoutReportCommand cmd = context.getCommand();
		String cid = AppContexts.user().companyId();
		
		BundledBusinessException exceptions = BundledBusinessException.newInstance();
		
		switch (cmd.getAction()) {
		case 0: // insert
			// set new layoutID for insert object
			insert(cid, cmd, exceptions); 
			break;
		case 1: 
			//update
			update(cid, cmd, exceptions); 
			break;
		}
	}
	
	public void insert(String cid, NewLayoutReportCommand cmd,  BundledBusinessException exceptions) {
		Map<String, Boolean> checkExits = this.reportClsRepo.checkExist(cid, cmd.getReportCode(), cmd.getReportName());
		int maxLayoutId = this.reportClsRepo.maxId(cid);
		int maxDisOrder= this.reportClsRepo.maxDisorder(cid);
		if (checkExits.isEmpty()) {
			this.reportClsRepo.insert(PersonalReportClassification.createFromJavaType(cid, maxLayoutId + 1,
					cmd.getReportCode(), cmd.getReportName(), cmd.getReportNameYomi(), maxDisOrder + 1, cmd.isAbolition(),
					cmd.getReportType(), cmd.getRemark(), cmd.getMemo(), cmd.getMessage(), cmd.isFormReport(), true));

		} else {
			Boolean name = checkExits.get("NAME");
			Boolean code = checkExits.get("CODE");

			if (code != null) {
				// JHN011_B222_1_1
				BusinessException codeMessage = new BusinessException("Msgj_41",
						TextResource.localize("JHN011_B222_1_1"));
				codeMessage.setSuppliment("NameID", TextResource.localize("JHN011_B222_1_1"));
				exceptions.addMessage(codeMessage);
			}

			if (name != null) {
				BusinessException nameMessage = new BusinessException("Msgj_42",
						TextResource.localize("JHN011_B222_1_2"));
				nameMessage.setSuppliment("NameID", TextResource.localize("JHN011_B222_1_1"));
				exceptions.addMessage(nameMessage);

			}
			
			// Has error, throws message
			if (exceptions.cloneExceptions().size() > 0) {
				exceptions.throwExceptions();
			}

		}
	}
	
	public void update(String cid, NewLayoutReportCommand cmd, BundledBusinessException exceptions) {
		
		 List<PersonalReportClassification> duplicateNameLst = this.reportClsRepo.getAllSameNameByCid(cid, cmd.getReportName());
		 
		 Optional<PersonalReportClassification> duplicateNameOpt = duplicateNameLst.stream().filter(c ->  c.getPReportClsId() != cmd.getId().intValue()).findFirst();
		 if(duplicateNameOpt.isPresent()) {
			 BusinessException nameMessage = new BusinessException("Msgj_42",
						TextResource.localize("JHN011_B222_1_2"));
				nameMessage.setSuppliment("NameID", TextResource.localize("JHN011_B222_1_1"));
				exceptions.addMessage(nameMessage);
				// Has error, throws message
				if (exceptions.cloneExceptions().size() > 0) {
					exceptions.throwExceptions();
				}
		 }else {
			 Optional<PersonalReportClassification> oldReport = duplicateNameLst.stream().filter(c ->  c.getPReportClsId() == cmd.getId().intValue()).findFirst();
			 PersonalReportClassification domain = PersonalReportClassification.createFromJavaType(cid, oldReport.get().getPReportClsId(),
						cmd.getReportCode(), cmd.getReportName(), cmd.getReportNameYomi(), oldReport.get().getDisplayOrder(), cmd.isAbolition(),
						cmd.getReportType(), cmd.getRemark(), cmd.getMemo(), cmd.getMessage(), cmd.isFormReport(), true);
			 this.reportClsRepo.update(domain);
		 }
	}

}
