package nts.uk.ctx.at.record.dom.reservation.bento;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentomenuAdapter;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.SWkpHistExport;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.BentoReservationSetting;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.BentoReservationSettingRepository;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.OperationDistinction;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class BentoReserveCommonService {
    @Inject
    private BentomenuAdapter bentomenuAdapter;

    @Inject
    private BentoReservationSettingRepository bentoReservationSettingRepository;

    public Optional<WorkLocationCode> getWorkLocationCode(String cid, String sid, GeneralDate date) {

        Optional<BentoReservationSetting> bentoReservationSettings = bentoReservationSettingRepository.findByCId(cid);

        // If mode By Location get work location code
        val isPresentSetting = bentoReservationSettings.isPresent()
                && bentoReservationSettings.get().getOperationDistinction().value == OperationDistinction.BY_LOCATION.value;

        Optional<SWkpHistExport> hisItems = isPresentSetting ? this.bentomenuAdapter
                .findBySid(sid, date) : Optional.empty();
        Optional<WorkLocationCode> workLocationCode = hisItems.isPresent() ? Optional.of(new WorkLocationCode(hisItems.get().getWorkplaceCode()))
                : Optional.empty();

        return workLocationCode;
    }
}
