package nts.uk.ctx.at.shared.app.command.yearholidaygrant;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantYearHolidayRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceTbl;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author TanLV
 *
 */
@Transactional
@Stateless
public class LengthOfServiceAddCommandHandler extends CommandHandler<List<GrantHolidayCommand>> {
	@Inject
	private LengthServiceRepository lengthServiceRepository;
	
	@Inject
	private GrantYearHolidayRepository grantYearHolidayRepo;

	@Override
	protected void handle(CommandHandlerContext<List<GrantHolidayCommand>> context) {
		List<GrantHolidayCommand> command = context.getCommand();
		String companyId = AppContexts.user().companyId();

		// Validate input
		validateViewData(companyId, command);
		// Remove items case
		lengthServiceRepository.remove(companyId, command.get(0).getYearHolidayCode());
		grantYearHolidayRepo.remove(companyId, command.get(0).getYearHolidayCode());
		// add data
		command.forEach(item -> {
			LengthServiceTbl lengthOfService = LengthServiceTbl.createFromJavaType(companyId, item.getYearHolidayCode(),
					item.getGrantNum(), item.getAllowStatus(), item.getStandGrantDay(), item.getYear(),
					item.getMonth());
			lengthServiceRepository.add(lengthOfService);
			GrantHdTbl newData = GrantHdTbl.createFromJavaType(companyId, item.getConditionNo(),
					item.getYearHolidayCode(), item.getGrantNum(), item.getGrantDays(),
					item.getLimitTimeHd() != null ? item.getLimitTimeHd() : 0,
					item.getLimitDayYear() != null ? item.getLimitDayYear() : 0);

			grantYearHolidayRepo.add(newData);
		});
	}
	
	/**
	 * Validate data before process
	 * @param companyId
	 * @param command
	 */
	private void validateViewData(String companyId, List<GrantHolidayCommand> command) {
		List<LengthServiceTbl> lengthServiceData = new ArrayList<>();
		for (GrantHolidayCommand data : command) {
			LengthServiceTbl lengthService = LengthServiceTbl.createFromJavaType(companyId, data.getYearHolidayCode(), data.getGrantNum(), 
					data.getAllowStatus(), data.getStandGrantDay(), data.getYear(), data.getMonth());
			
			if(lengthService.getMonth() != null || lengthService.getYear() != null) {
				lengthServiceData.add(lengthService);
			}
		}
		
		LengthServiceTbl.validateInput(lengthServiceData);
	}
}
