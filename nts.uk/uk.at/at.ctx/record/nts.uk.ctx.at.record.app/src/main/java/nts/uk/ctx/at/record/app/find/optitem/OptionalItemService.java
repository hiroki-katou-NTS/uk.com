package nts.uk.ctx.at.record.app.find.optitem;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.ControlOfAttendanceItems;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.ControlOfAttendanceItemsRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.ControlOfMonthlyItems;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.ControlOfMonthlyItemsRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author anhnm
 *
 */
@Stateless
public class OptionalItemService {

    
    @Inject
    private ControlOfMonthlyItemsRepository monthlyControlRepository;
    
    @Inject
    private ControlOfAttendanceItemsRepository dailyControlRepository;
    
    public ControlUnitDto getItemControl(int performanceAtr, int itemID) {
        String companyID = AppContexts.user().companyId();
        
        if (performanceAtr == 0) {
            Optional<ControlOfMonthlyItems> controlMonthlyOpt = monthlyControlRepository.getControlOfMonthlyItem(companyID, itemID);
            return controlMonthlyOpt.isPresent() ? ControlUnitDto.fromMonthlyDomain(controlMonthlyOpt.get()) : null;
        } else {
            Optional<ControlOfAttendanceItems> controlDailyOptional = dailyControlRepository.getControlOfAttendanceItem(companyID, itemID);
            return controlDailyOptional.isPresent() ? ControlUnitDto.fromDailyDomain(controlDailyOptional.get()) : null;
        }
    }
}
