package nts.uk.ctx.at.record.app.command.reservation.bento;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.reservation.bento.*;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 弁当予約を強制削除
 *
 * @author Le Huu Dat
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ForceDeleteBentoReserveCommandHandler extends CommandHandler<ForceDeleteBentoReserveCommand> {
    @Inject
    private BentoReservationRepository bentoReservationRepository;

    @Inject
    private RecordDomRequireService requireService;

    @Override
    protected void handle(CommandHandlerContext<ForceDeleteBentoReserveCommand> context) {
        String cid = AppContexts.user().companyId();
        ForceDeleteBentoReserveCommand command = context.getCommand();
        val closingTimeFrame = EnumAdaptor.valueOf(command.getClosingTimeFrame(), ReservationClosingTimeFrame.class);
        val reservationDate = new ReservationDate(command.getDate(), closingTimeFrame);
        List<ReservationRegisterInfo> reservationRegisterInfos = new ArrayList<>();
        for (ForceDeleteBentoReserveCommand.ReservationInfoCommand item : command.getReservationInfos()) {
            RequireImpl require = new RequireImpl(requireService);
            boolean canModify = BentoReservationStateService.check(require, item.getEmpployeeId(), command.getDate());

            if (!canModify) {
                throw new BusinessException("Msg_1838");
            }

            ReservationRegisterInfo reservationRegisterInfo = new ReservationRegisterInfo(item.getReservationCardNo());
            reservationRegisterInfos.add(reservationRegisterInfo);
        }
        List<BentoReservation> bentoReservations = bentoReservationRepository.getReservationInformation(reservationRegisterInfos, reservationDate);
        for (BentoReservation item : bentoReservations) {
            if (item.getReservationDate().getClosingTimeFrame().value == command.getClosingTimeFrame()) {
                bentoReservationRepository.delete(item);
            }
        }
    }

    @AllArgsConstructor
    private class RequireImpl implements BentoReservationStateService.Require {
        private RecordDomRequireService requireService;

        @Override
        public Optional<GeneralDate> getClosureStart(String employeeId) {
            val require = requireService.createRequire();
            val cacheCarrier = new CacheCarrier();
            // 社員に対応する締め開始日を取得する
            return GetClosureStartForEmployee.algorithm(require, cacheCarrier, employeeId);
        }
    }
}
