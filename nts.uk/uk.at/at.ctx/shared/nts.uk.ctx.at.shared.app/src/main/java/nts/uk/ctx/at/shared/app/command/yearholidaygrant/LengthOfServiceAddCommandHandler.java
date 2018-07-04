package nts.uk.ctx.at.shared.app.command.yearholidaygrant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantNum;
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
		Boolean isDelete = false;
		
		// Get data from Db
		List<LengthServiceTbl> lengthServiceTbl = lengthServiceRepository.findByCode(companyId, command.get(0).getYearHolidayCode());
		
		// Validate input
		validateViewData(companyId, command);
		
		// Remove items case
		if(command.size() < lengthServiceTbl.size()) {
			// Get GrantNums
			List<Integer> grantNums = getGrantNums(lengthServiceTbl, command);
			lengthServiceRepository.remove(companyId, command.get(0).getYearHolidayCode(), grantNums);
			grantYearHolidayRepo.removeByGrantNums(companyId, command.get(0).getYearHolidayCode(), grantNums);
			
			reUpdateData(companyId, command);
			
			isDelete = true;
		}
		
		if(!isDelete) {
			// Insert new items or update items case
			for (GrantHolidayCommand item : command) {
				// Compare data from Db with data on UI:
				Boolean lengthService = lengthServiceTbl.stream().anyMatch(x -> x.getYearHolidayCode().v().equals(item.getYearHolidayCode()) && x.getGrantNum().v() == item.getGrantNum());

				LengthServiceTbl lengthOfService = LengthServiceTbl.createFromJavaType(companyId, item.getYearHolidayCode(), item.getGrantNum(), 
						item.getAllowStatus(), item.getStandGrantDay(), item.getYear(), item.getMonth());
				
				if (lengthService) {
					// Update data
					lengthServiceRepository.update(lengthOfService);
					
					GrantHdTbl newData = GrantHdTbl.createFromJavaType(companyId, item.getConditionNo(), item.getYearHolidayCode(), 
							item.getGrantNum(), item.getGrantDays(), item.getLimitTimeHd() != null ? item.getLimitTimeHd() : 0, 
									item.getLimitDayYear() != null ? item.getLimitDayYear() : 0);
					
					grantYearHolidayRepo.update(newData);
				} else {
					// Insert new record
					lengthServiceRepository.add(lengthOfService);

					GrantHdTbl newData = GrantHdTbl.createFromJavaType(companyId, item.getConditionNo(), item.getYearHolidayCode(), 
								item.getGrantNum(), item.getGrantDays(), item.getLimitTimeHd() != null ? item.getLimitTimeHd() : 0, 
										item.getLimitDayYear() != null ? item.getLimitDayYear() : 0);
						
					grantYearHolidayRepo.add(newData);
				}
			}
		}		
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
	
	/**
	 * Return list of GrantNums
	 * @param lengthServiceTbl
	 * @param command
	 * @return
	 */
	private List<Integer> getGrantNums(List<LengthServiceTbl> lengthServiceTbl, List<GrantHolidayCommand> command) {
		List<Integer> grantNums = new ArrayList<>();

		for (int i = 0; i < lengthServiceTbl.size(); i++) {
			LengthServiceTbl dbItem = lengthServiceTbl.get(i);
			
			boolean isDiffrent =true;
			
			for (int j = 0; j < command.size(); j++) {
				GrantHolidayCommand viewItem = command.get(j);
				
				if(dbItem.getGrantNum().v() == viewItem.getGrantNum()){
					isDiffrent=false;
					break;
				}
			}
			
			if(isDiffrent){
				grantNums.add(dbItem.getGrantNum().v());
			}
		}
		
		if(grantNums.size() > 0) {
			return grantNums;
		}
		
		return Collections.emptyList();
	}
	
	/**
	 * Re-update data after delete data from db
	 * @param companyId
	 * @param command
	 */
	private void reUpdateData(String companyId, List<GrantHolidayCommand> command) {
		// Re-check data in db
		List<LengthServiceTbl> savedSerive = lengthServiceRepository.findByCode(companyId, command.get(0).getYearHolidayCode());
		List<GrantHdTbl> savedGrant = grantYearHolidayRepo.findByCode(companyId, 1, command.get(0).getYearHolidayCode());
		
		lengthServiceRepository.remove(companyId, command.get(0).getYearHolidayCode());
		grantYearHolidayRepo.remove(companyId, 1, command.get(0).getYearHolidayCode());
		
		int index = 0;
		// Add to Length Service
		for (LengthServiceTbl data : savedSerive) {
			data.setGrantNum(new GrantNum(index + 1));
			lengthServiceRepository.add(data);
			index += 1;
		}
		
		int count = 0;
		// Add to GrantHd
		for (GrantHdTbl data : savedGrant) {
			data.setGrantNum(new GrantNum(count + 1));
			grantYearHolidayRepo.add(data);
			count += 1;
		}
	}
}
