package nts.uk.ctx.basic.app.command.organization.position;


import java.time.LocalDate;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.app.find.organization.position.JobHistFinder;
import nts.uk.ctx.basic.dom.organization.position.JobHistory;
import nts.uk.ctx.basic.dom.organization.position.PositionRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DeleteHistoryCommandHandler extends CommandHandler<DeleteHistoryCommand> {

	@Inject
	private PositionRepository repository;
	@Inject
	private JobHistFinder jobHistFinder;
	
	@Override

		

		protected void handle(CommandHandlerContext<DeleteHistoryCommand> context) {
			
			DeleteHistoryCommand command = context.getCommand();
			String companyCode = AppContexts.user().companyCode();

			
			String historyId = command.getHistoryId();
			//check xem lich su co ton tai khong
			repository.findSingleHistory(companyCode, historyId).ifPresent(x -> {throw new BusinessException("ER010");});
			if(!repository.findSingleHistory(companyCode, historyId).isPresent()){
				throw new BusinessException("ER010");
			}
			//xoa lich su
			repository.deleteHist(companyCode, historyId);
			//xoa tat ca postition cua lich su
			repository.deleteJobTitleByHisId(companyCode, historyId);
			//update tat ca ngay ket thuc cua lich su truoc ve 1999-12-31
			//---Tim lich su cua thang dang truoc
			GeneralDate endDate = GeneralDate.localDate(LocalDate.parse(command.getOldStartDate())).addDays(-1);
			Optional<JobHistory> getHistoryByEndDate = repository.getHistoryByEdate(companyCode, endDate);
			if(getHistoryByEndDate.isPresent()){
				JobHistory history = getHistoryByEndDate.get();
				history.setEndDate(GeneralDate.localDate(LocalDate.parse("9999-12-31")));
				//---update ngay ket thuc cua thang truoc
				repository.updateHistory(history);
			}
			//Xoa tat ca quyen cua position
					
			//dudt
		}
	}


