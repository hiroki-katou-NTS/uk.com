package nts.uk.ctx.at.record.app.command.dailyperformanceformat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessFormatSheet;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeFormatDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.BusinessTypeCode;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessFormatSheetRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypeFormatDailyRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DeleteBusiFormatBySheetCmdHandler extends CommandHandler<DeleteBusiFormatBySheetCmd> {

	@Inject
	private BusinessFormatSheetRepository repo;
	
	@Inject
	private BusinessTypeFormatDailyRepository businessTypeFormatDailyRepo;
	
	@Override
	protected void handle(CommandHandlerContext<DeleteBusiFormatBySheetCmd> context) {
		String companyId = AppContexts.user().companyId();
		DeleteBusiFormatBySheetCmd command = context.getCommand();
		if(repo.getSheetInformation(companyId,new BusinessTypeCode( command.getBusinessTypeCode()), command.getSheetNo()).isPresent()) {
			repo.deleteBusBySheetNo(companyId, command.getBusinessTypeCode(),  command.getSheetNo());
			//listSheet
			List<BusinessFormatSheet>  listBusFormatSheet =  repo.getSheetInformationByCode(companyId, command.getBusinessTypeCode());
			//sort 
			Collections.sort(listBusFormatSheet, Comparator.comparing(BusinessFormatSheet::getSheetNo));
			int size = listBusFormatSheet.size();
			List<BusinessFormatSheet>  listBusFormatSheetClone =  listBusFormatSheet.stream().map(c->c.clone()).collect(Collectors.toList());
			
			//listItem after remove
			List<BusinessTypeFormatDaily> listBusTypeFormatDaily = businessTypeFormatDailyRepo.getBusinessTypeFormat(companyId, command.getBusinessTypeCode());
			
			List<BusinessTypeFormatDaily> listBusTypeFormatDailyClone = listBusTypeFormatDaily.stream().map(c->c.clone()).collect(Collectors.toList());
			
			//delete all
			for(int i = command.getSheetNo().intValue()-1;i<size;i++) {
				repo.deleteBusBySheetNo(companyId, command.getBusinessTypeCode(),listBusFormatSheetClone.get(i).getSheetNo());
			}
			
			//insert
			for(int i = command.getSheetNo().intValue()-1;i<size;i++) {
				int sheetNoOld = listBusFormatSheetClone.get(i).getSheetNo().intValue();
				listBusFormatSheetClone.get(i).setSheetNo(BigDecimal.valueOf(i+1));
				repo.add(listBusFormatSheetClone.get(i));
				List<BusinessTypeFormatDaily> listBusTypeFormatDailyNew = new ArrayList<>();
				for(BusinessTypeFormatDaily busTypeFormatDaily : listBusTypeFormatDailyClone) {
					if(busTypeFormatDaily.getSheetNo().intValue() == sheetNoOld) {
						busTypeFormatDaily.setSheetNo(BigDecimal.valueOf(i+1));
						listBusTypeFormatDailyNew.add(busTypeFormatDaily);
					}
				}
				businessTypeFormatDailyRepo.add(listBusTypeFormatDailyNew);
				
				
			}
			
			
		}
	}

}
