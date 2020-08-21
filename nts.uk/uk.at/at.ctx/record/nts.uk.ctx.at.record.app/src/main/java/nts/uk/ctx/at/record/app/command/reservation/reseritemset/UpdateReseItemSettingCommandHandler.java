package nts.uk.ctx.at.record.app.command.reservation.reseritemset;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.bento.WorkLocationCode;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.*;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.Optional;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class UpdateReseItemSettingCommandHandler extends CommandHandler<UpdateReseItemSettingCommand> {

    @Inject
    private BentoMenuRepository bentoMenuRepository;

    @Override
    protected void handle(CommandHandlerContext<UpdateReseItemSettingCommand> commandHandlerContext) {

        val command = commandHandlerContext.getCommand();

        Bento bento = new Bento(command.getFrameNo(),
                new BentoName(command.getBenToName()),
                new BentoAmount(command.getAmount1()),
                new BentoAmount(command.getAmount2()),
                new BentoReservationUnitName(command.getUnit()),
                command.isCanBookClosesingTime1(),
                command.isCanBookClosesingTime2(),
                Optional.of(new WorkLocationCode(command.getWorkLocationCode()))
        );

        String cid = AppContexts.user().companyId();
        GeneralDate date = GeneralDate.max();

        BentoMenu bentoMenu = command.getHistId() == null ?
                bentoMenuRepository.getBentoMenuByEndDate(cid,date) :
                bentoMenuRepository.getBentoMenuByHistId(cid,command.getHistId());

        Bento[] bentoList = new Bento[bentoMenu.getMenu().size()];
        bentoMenu.getMenu().toArray(bentoList);
        Optional<Bento> optionalBento = Arrays.stream(bentoList)
                .filter(x -> x.getFrameNo() == bento.getFrameNo())
                .findFirst();
        if(optionalBento.isPresent()){
            int i = Arrays.asList(bentoList).indexOf(optionalBento.get());
            bentoList[i] = bento;
        }
        BentoMenu result = new BentoMenu(bentoMenu.getHistoryID(),Arrays.asList(bentoList),bentoMenu.getClosingTime());

        bentoMenuRepository.update(result);
    }

}
