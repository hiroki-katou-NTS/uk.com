package nts.uk.ctx.at.function.app.command.monthlycorrection.fixedformatmonthly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.SerializationUtils;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonthlyRecordWorkType;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonthlyRecordWorkTypeRepository;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.SheetCorrectedMonthly;
import nts.uk.shr.com.context.AppContexts;
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Stateless
public class UpdateMonthlyRecordWorkTypeCmdHandler  extends CommandHandler<MonthlyRecordWorkTypeCmd> {

	@Inject
	private MonthlyRecordWorkTypeRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<MonthlyRecordWorkTypeCmd> context) {
		String companyID = AppContexts.user().companyId();
		MonthlyRecordWorkTypeCmd command = context.getCommand();
		command.setCompanyID(companyID);
		Optional<MonthlyRecordWorkType> data = repo.getMonthlyRecordWorkTypeByCode(companyID, command.getBusinessTypeCode());
		
		if(!data.isPresent()) {
			command.getDisplayItem().getListSheetCorrectedMonthly().get(0).setSheetNo(1);
			repo.addMonthlyRecordWorkType(MonthlyRecordWorkTypeCmd.fromCommand(command));
		}else {
			int sheetNo = command.getDisplayItem().getListSheetCorrectedMonthly().get(0).getSheetNo();
			if(!command.getDisplayItem().getListSheetCorrectedMonthly().get(0).getListDisplayTimeItem().isEmpty()) { //list null when click Sheet削除
				int maxSize = data.get().getDisplayItem().getListSheetCorrectedMonthly().size();
				Collections.sort(data.get().getDisplayItem().getListSheetCorrectedMonthly(), Comparator.comparing(SheetCorrectedMonthly::getSheetNo));
				if(sheetNo >1) {
					boolean checkExists = false;
					for(SheetCorrectedMonthly sheetCorrectedMonthly : data.get().getDisplayItem().getListSheetCorrectedMonthly()) {
						if(sheetCorrectedMonthly.getSheetNo()==sheetNo) {
							checkExists = true;
							break;
						}
					}
					if(!checkExists) {
//						for(int i = maxSize-2 ;i >= 0;i--) {
//							if(data.get().getDisplayItem().getListSheetCorrectedMonthly().get(i).getListDisplayTimeItem().isEmpty()) {
//								command.getDisplayItem().getListSheetCorrectedMonthly().get(0).setSheetNo(i+1);
//							}else {
//								int newSheetNo = data.get().getDisplayItem().getListSheetCorrectedMonthly().get(i).getSheetNo()+1;
//								command.getDisplayItem().getListSheetCorrectedMonthly().get(0).setSheetNo(newSheetNo);
//								break;
//							}
//						}
//					}else {
						command.getDisplayItem().getListSheetCorrectedMonthly().get(0).setSheetNo(maxSize+1);	
					}	
				}
			}
			repo.updateMonthlyRecordWorkType(MonthlyRecordWorkTypeCmd.fromCommand(command));
			if(command.getDisplayItem().getListSheetCorrectedMonthly().get(0).getListDisplayTimeItem().isEmpty()) { //list null when click Sheet削除
				
				MonthlyRecordWorkType dataAfterDelete = repo.getMonthlyRecordWorkTypeByCode(companyID, command.getBusinessTypeCode()).get();
				//sort
				Collections.sort(dataAfterDelete.getDisplayItem().getListSheetCorrectedMonthly(), Comparator.comparing(SheetCorrectedMonthly::getSheetNo));
				//delete all sheet
				int size = dataAfterDelete.getDisplayItem().getListSheetCorrectedMonthly().size();
				
				for(int i=sheetNo-1;i<size;i++) {
					MonthlyRecordWorkType monthlyRecordWorkType = dataAfterDelete.clone();
					SheetCorrectedMonthly sheetCorrectedMonthly = monthlyRecordWorkType.getDisplayItem().getListSheetCorrectedMonthly().get(i);
					List<SheetCorrectedMonthly> listSheetCorrectedMonthly = new ArrayList<>();
					listSheetCorrectedMonthly.add(sheetCorrectedMonthly);
					monthlyRecordWorkType.getDisplayItem().setListSheetCorrectedMonthly(listSheetCorrectedMonthly);
					monthlyRecordWorkType.getDisplayItem().getListSheetCorrectedMonthly().get(0).setListDisplayTimeItem(new ArrayList<>());
					repo.updateMonthlyRecordWorkType(monthlyRecordWorkType);
				}
				//insert 
				for(int i=sheetNo-1;i<dataAfterDelete.getDisplayItem().getListSheetCorrectedMonthly().size();i++) {
					MonthlyRecordWorkType temp = dataAfterDelete.clone();
					List<SheetCorrectedMonthly> listSheetCorrectedMonthly = new ArrayList<>();
					listSheetCorrectedMonthly.add(temp.getDisplayItem().getListSheetCorrectedMonthly().get(i));
					temp.getDisplayItem().setListSheetCorrectedMonthly(listSheetCorrectedMonthly);
					temp.getDisplayItem().getListSheetCorrectedMonthly().get(0).setSheetNo(i+1);
					repo.updateMonthlyRecordWorkType(temp);
				}
			}
		}
		
	}

}
