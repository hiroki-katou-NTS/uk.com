package nts.uk.ctx.at.shared.app.command.yearholidaygrant;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.service.GrantHdTblRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantYearHolidayRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author TanLV
 *
 */
@Transactional
@Stateless
public class GrantHolidayTblAddCommandHandler extends CommandHandler<GrantHolidayTblCommand> {
	
	@Inject
	private YearHolidayRepository yearHolidayRepo;
	@Inject
	private GrantYearHolidayRepository grantYearHolidayRepo;
	@Inject
	private GrantHdTblRepository grantHdTblRepository;
	
	@Override
	protected void handle(CommandHandlerContext<GrantHolidayTblCommand> context) {
		GrantHolidayTblCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		// check exists year holiday code
		Optional<GrantHdTblSet> grantHolidaySet = yearHolidayRepo.findByCode(companyId, command.getYearHolidayCode());
		if (!grantHolidaySet.isPresent()) {
			throw new RuntimeException("Year holiday code not created!");
		}
		
		List<GrantHdTbl> grantHolidays = command.getGrantHolidayList().stream()
				.map(x->x.toDomain(companyId)).collect(Collectors.toList());
		
		// 勤続年数が入力されている場合、付与日数を入力すること
		for (GrantHdTbl item : grantHolidays) {
			if(item.getGrantDays().v() == null) {
				throw new BusinessException("Msg_270");
			}
		}
		
		// check update/insert limit time in holiday 半日年休上限回数  or not
		if(!grantHdTblRepository.checkLimitTime()) {
			for (GrantHdTbl item : grantHolidays) {
				item.setLimitDayYear(null);
			}
		}
		
		// check update/insert limit day in year 時間年休上限日数 or not
		if(!grantHdTblRepository.checkLimitDay()) {
			for (GrantHdTbl item : grantHolidays) {
				item.setLimitTimeHd(null);
			}
		}
		
		// remove all
		grantYearHolidayRepo.remove(companyId, command.getConditionNo(), command.getYearHolidayCode());
		
		for (GrantHdTbl item : grantHolidays) {
			// validate
			item.validate();		
					
			// add to db
			grantYearHolidayRepo.add(item);
		}
	}
}
