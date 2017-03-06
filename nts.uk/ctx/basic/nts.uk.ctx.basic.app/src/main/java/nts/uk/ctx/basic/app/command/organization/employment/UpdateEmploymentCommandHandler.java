package nts.uk.ctx.basic.app.command.organization.employment;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.basic.dom.organization.employment.CloseDateNo;
import nts.uk.ctx.basic.dom.organization.employment.Employment;
import nts.uk.ctx.basic.dom.organization.employment.EmploymentCode;
import nts.uk.ctx.basic.dom.organization.employment.EmploymentName;
import nts.uk.ctx.basic.dom.organization.employment.EmploymentRepository;
import nts.uk.ctx.basic.dom.organization.employment.ManageOrNot;
import nts.uk.ctx.basic.dom.organization.employment.ProcessingNo;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.primitive.Memo;

@Stateless
@Transactional
public class UpdateEmploymentCommandHandler extends CommandHandler<UpdateEmploymentCommand>{
	@Inject
	private EmploymentRepository repository;
	@Override
	protected void handle(CommandHandlerContext<UpdateEmploymentCommand> context) {
		try{
			UpdateEmploymentCommand command = context.getCommand();
			String companyCode = AppContexts.user().companyCode();		
			Employment employment = new Employment(companyCode,
					new EmploymentCode(command.getEmploymentCode()),
					new EmploymentName(command.getEmploymentName()),
					new CloseDateNo(command.getCloseDateNo()),
					new Memo(command.getMemo()),
					new ProcessingNo(command.getProcessingNo()),
					EnumAdaptor.valueOf( command.getStatutoryHolidayAtr(), ManageOrNot.class),
					new EmploymentCode(command.getEmployementOutCd()),
					EnumAdaptor.valueOf( command.getDisplayFlg(), ManageOrNot.class));
			
			this.repository.update(employment);
			//A_SEL_001にチェックが付いている場合
			if(command.isChkDisplayFlg()){
				Optional<Employment> employmentByDisplayFlg = repository.findEmploymnetByDisplayFlg(companyCode);
				if(employmentByDisplayFlg.isPresent()){
					//[雇用マスタ.UPD-2]を実施する
					Employment employmentDisplay = employmentByDisplayFlg.get();
					employmentDisplay.setDisplayFlg(ManageOrNot.NOT_MANAGE);
					repository.update(employmentDisplay);
				}
			}
		}
		catch(Exception ex){
			throw ex;
		}
	}

}
