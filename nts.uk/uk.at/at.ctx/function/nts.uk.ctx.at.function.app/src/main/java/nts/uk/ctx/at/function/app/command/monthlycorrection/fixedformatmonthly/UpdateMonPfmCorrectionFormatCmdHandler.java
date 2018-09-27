package nts.uk.ctx.at.function.app.command.monthlycorrection.fixedformatmonthly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.InitialDisplayMonthly;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.InitialDisplayMonthlyRepository;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonPfmCorrectionFormat;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonPfmCorrectionFormatRepository;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonthlyPerformanceFormatCode;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.SheetCorrectedMonthly;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateMonPfmCorrectionFormatCmdHandler extends CommandHandler<MonPfmCorrectionFormatCmd> {

	@Inject
	private MonPfmCorrectionFormatRepository repo;
	
	@Inject
	private InitialDisplayMonthlyRepository initialRepo;
	
	@Override
	protected void handle(CommandHandlerContext<MonPfmCorrectionFormatCmd> context) {
		String companyID = AppContexts.user().companyId();
		MonPfmCorrectionFormatCmd command = context.getCommand();
		command.setCompanyID(companyID);
		Optional<MonPfmCorrectionFormat> data = repo.getMonPfmCorrectionFormat(companyID, command.getMonthlyPfmFormatCode());
		
		if(data.isPresent()) {
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
						command.getDisplayItem().getListSheetCorrectedMonthly().get(0).setSheetNo(maxSize+1);	
					}	
				}
			}
			repo.updateMonPfmCorrectionFormat(MonPfmCorrectionFormatCmd.fromCommand(command));
			if(command.getDisplayItem().getListSheetCorrectedMonthly().get(0).getListDisplayTimeItem().isEmpty()) { //list null when click Sheet削除
				
				MonPfmCorrectionFormat dataAfterDelete = repo.getMonPfmCorrectionFormat(companyID, command.getMonthlyPfmFormatCode()).get();
				//sort
				Collections.sort(dataAfterDelete.getDisplayItem().getListSheetCorrectedMonthly(), Comparator.comparing(SheetCorrectedMonthly::getSheetNo));
				//delete all sheet
				int size = dataAfterDelete.getDisplayItem().getListSheetCorrectedMonthly().size();
				
				for(int i=sheetNo-1;i<size;i++) {
					MonPfmCorrectionFormat monthlyRecordWorkType = dataAfterDelete.clone();
					SheetCorrectedMonthly sheetCorrectedMonthly = monthlyRecordWorkType.getDisplayItem().getListSheetCorrectedMonthly().get(i);
					List<SheetCorrectedMonthly> listSheetCorrectedMonthly = new ArrayList<>();
					listSheetCorrectedMonthly.add(sheetCorrectedMonthly);
					monthlyRecordWorkType.getDisplayItem().setListSheetCorrectedMonthly(listSheetCorrectedMonthly);
					monthlyRecordWorkType.getDisplayItem().getListSheetCorrectedMonthly().get(0).setListDisplayTimeItem(new ArrayList<>());
					repo.updateMonPfmCorrectionFormat(monthlyRecordWorkType);
				}
				//insert 
				for(int i=sheetNo-1;i<dataAfterDelete.getDisplayItem().getListSheetCorrectedMonthly().size();i++) {
					MonPfmCorrectionFormat temp = dataAfterDelete.clone();
					List<SheetCorrectedMonthly> listSheetCorrectedMonthly = new ArrayList<>();
					listSheetCorrectedMonthly.add(temp.getDisplayItem().getListSheetCorrectedMonthly().get(i));
					temp.getDisplayItem().setListSheetCorrectedMonthly(listSheetCorrectedMonthly);
					temp.getDisplayItem().getListSheetCorrectedMonthly().get(0).setSheetNo(i+1);
					repo.updateMonPfmCorrectionFormat(temp);
				}
			}
			
			
			//if A9_1 = true
			if(command.isSetFormatToDefault()) {
				initialRepo.deleteByCid(companyID);
				initialRepo.addInitialDisplayMonthly(new InitialDisplayMonthly(companyID,new MonthlyPerformanceFormatCode( command.getMonthlyPfmFormatCode())));
			}
		}
		else {
			throw new BusinessException("Msg_3");
		}
		
	}

}
