package nts.uk.screen.at.app.reservation;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.query.reservation.BentoMenuByClosingTimeDto;
import nts.uk.ctx.at.record.dom.reservation.bento.WorkLocationCode;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentomenuAdapter;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.SWkpHistExport;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoMenuByClosingTime;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.BentoReservationSetting;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.GetEmployeeReferenceRangeService;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.OperationDistinction;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * 予約確認一覧
 *
 * @author 3si - Dang Huu Khai
 */
@Stateless
public class ReservationConfirmationListScreenQuery {

    @Inject
    private GetEmployeeReferenceRangeService domainService;

    @Inject
    private BentoMenuRepository bentoMenuRepo;

    @Inject
    private BentomenuAdapter bentomenuAdapter;

    public ReservationConfirmationListDto getReservationConfirmationListStartupInfo(
            String companyId,
            String employeeId,
            GeneralDate date)
    {
        ReservationConfirmationListDto dto = new ReservationConfirmationListDto();
        Optional<BentoReservationSetting> optBentoReservationSetting = domainService.getBentoReservationSetting(companyId);
        if (optBentoReservationSetting.isPresent()) {
            BentoReservationSetting bentoReservationSetting = optBentoReservationSetting.get();
            dto.setOperationDistinction(bentoReservationSetting.getOperationDistinction());
        }

        Optional<WorkLocationCode> workLocationCode = Optional.empty();

        if (dto.getOperationDistinction() == OperationDistinction.BY_LOCATION) {
            Optional<SWkpHistExport> hisItems = this.bentomenuAdapter.findBySid(employeeId,date);
            if (!hisItems.isPresent()){
                throw new RuntimeException("Invalid workplace history");
            }

            workLocationCode = Optional.ofNullable(hisItems.get().getWorkLocationCd() == null ? null :
                    new WorkLocationCode(hisItems.get().getWorkLocationCd()));

            BentoMenu bentoMenu = bentoMenuRepo.getBentoMenu(companyId, date, workLocationCode);
            BentoMenuByClosingTime bentoMenuClosingTime = bentoMenu.getByClosingTime(workLocationCode);
            BentoMenuByClosingTimeDto bentoMenuByClosingTimeDto = BentoMenuByClosingTimeDto.fromDomain(bentoMenuClosingTime);
            dto.setClosingTime1(bentoMenuByClosingTimeDto.getClosingTime1());
            dto.setClosingTime2(bentoMenuByClosingTimeDto.getClosingTime2());
        }

        return dto;
    }
}