package nts.uk.ctx.at.record.app.command.reservation.bento;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationRepository;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReserveCommonService;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationDate;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationRegisterInfo;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * 従業員の弁当予約を強制修正する
 * @author Le Huu Dat
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ForceUpdateBentoReserveCommandHandler extends CommandHandler<ForceDeleteBentoReserveCommand> {
    @Inject
    private BentoReservationRepository bentoReservationRepository;

    @Inject
    private BentoReserveCommonService bentoReserveCommonService;

    @Override
    protected void handle(CommandHandlerContext<ForceDeleteBentoReserveCommand> context) {
        String cid = AppContexts.user().companyId();

        // 1: 弁当を強制予約する

    }
}
