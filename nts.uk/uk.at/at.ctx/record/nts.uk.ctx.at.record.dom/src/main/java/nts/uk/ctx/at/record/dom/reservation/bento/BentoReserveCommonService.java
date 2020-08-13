package nts.uk.ctx.at.record.dom.reservation.bento;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentomenuAdapter;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.SWkpHistExport;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.BentoReservationSetting;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.BentoReservationSettingRepository;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.OperationDistinction;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class BentoReserveCommonService {
    @Inject
    private BentomenuAdapter bentomenuAdapter;
    @Inject
    private GetClosureStartForEmployee getClosureStartForEmployee;

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

    /**
     * 弁当予約が強制修正できる状態を取得する
     * @param employeeId 社員ID
     * @param date 予約日
     * @return True: 予約でき. False: 予約できない
     */
    public boolean canReservation(String employeeId, GeneralDate date){
        // 社員に対応する締め開始日を取得する
        Optional<GeneralDate> closureStartOpt = getClosureStartForEmployee.algorithm(employeeId);
        if (closureStartOpt.isPresent()){
            // 修正できる状態
            return date.before(closureStartOpt.get());
        }
        return false;
    }
}
