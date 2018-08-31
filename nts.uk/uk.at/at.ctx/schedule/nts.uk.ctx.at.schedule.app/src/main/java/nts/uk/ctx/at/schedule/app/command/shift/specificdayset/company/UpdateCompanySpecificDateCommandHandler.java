package nts.uk.ctx.at.schedule.app.command.shift.specificdayset.company;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class UpdateCompanySpecificDateCommandHandler extends CommandHandler<List<UpdateCompanySpecificDateCommand>>{

	@Inject
	private CompanySpecificDateRepository companyRepo;
	
	private static final String DATE_FORMAT = "yyyy/MM/dd";
	
	@Override
	protected void handle(CommandHandlerContext<List<UpdateCompanySpecificDateCommand>> context) {
		String companyId = AppContexts.user().companyId();
		for(UpdateCompanySpecificDateCommand updateSpecificDateCommand :  context.getCommand()){
			GeneralDate date = GeneralDate.fromString(updateSpecificDateCommand.getSpecificDate(), DATE_FORMAT);
			if(updateSpecificDateCommand.isUpdate()) {
				companyRepo.deleteComSpecByDate(companyId, date);
				List<CompanySpecificDateItem> listInsert = new ArrayList<CompanySpecificDateItem>();
				for(Integer specificDateNo : updateSpecificDateCommand.getSpecificDateItemNo()){
					listInsert.add(CompanySpecificDateItem.createFromJavaType(
							companyId,
							date,
							specificDateNo,
							"empty")
					);
				}
				companyRepo.InsertComSpecDate(listInsert);
			} else {
				List<CompanySpecificDateItem> listInsert = new ArrayList<CompanySpecificDateItem>();
				for(Integer specificDateNo : updateSpecificDateCommand.getSpecificDateItemNo()){
					listInsert.add(CompanySpecificDateItem.createFromJavaType(
							companyId,
							date,
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
