package nts.uk.ctx.at.schedule.app.command.shift.specificdayset.company;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class UpdateCompanySpecificDateCommandHandler extends CommandHandler<List<UpdateCompanySpecificDateCommand>>{

	@Inject
	private CompanySpecificDateRepository companyRepo;
	
	@Override
	protected void handle(CommandHandlerContext<List<UpdateCompanySpecificDateCommand>> context) {
		String companyId = AppContexts.user().companyId();
		for(UpdateCompanySpecificDateCommand updateSpecificDateCommand :  context.getCommand()){
			if(updateSpecificDateCommand.isUpdate()) {
				companyRepo.deleteComSpecByDate(companyId,updateSpecificDateCommand.getSpecificDate().intValue());
				List<CompanySpecificDateItem> listInsert = new ArrayList<CompanySpecificDateItem>();
				for(BigDecimal specificDateNo : updateSpecificDateCommand.getSpecificDateItemNo()){
					listInsert.add(CompanySpecificDateItem.createFromJavaType(
							companyId,
							updateSpecificDateCommand.getSpecificDate(),
							specificDateNo,
							"empty")
					);
				}
				companyRepo.InsertComSpecDate(listInsert);
			} else {
				List<CompanySpecificDateItem> listInsert = new ArrayList<CompanySpecificDateItem>();
				for(BigDecimal specificDateNo : updateSpecificDateCommand.getSpecificDateItemNo()){
					listInsert.add(CompanySpecificDateItem.createFromJavaType(
							companyId,
							updateSpecificDateCommand.getSpecificDate(),
							specificDateNo,
							"empty")
					);
				}
				companyRepo.InsertComSpecDate(listInsert);
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		List<BigDecimal> listSpecDate = context.getCommand().stream().map(c->c.getSpecificDate()).distinct().collect(Collectors.toList());
//		
////		for(UpdateCompanySpecificDateCommand updateCommand : context.getCommand()){
////			
////		}
//		for(BigDecimal specDate : listSpecDate)	{
//			companyRepo.deleteComSpecByDate(companyId, specDate.intValue());
//		};
//		List<CompanySpecificDateItem> listInsert = context.getCommand()
//				.stream()
//				.map(c -> CompanySpecificDateItem.createFromJavaType(
//				companyId,
//				c.getSpecificDate(),
//				c.getSpecificDateNo(),
//				"")).collect(Collectors.toList());
//		companyRepo.InsertComSpecDate(listInsert);
		
		
	}
}
