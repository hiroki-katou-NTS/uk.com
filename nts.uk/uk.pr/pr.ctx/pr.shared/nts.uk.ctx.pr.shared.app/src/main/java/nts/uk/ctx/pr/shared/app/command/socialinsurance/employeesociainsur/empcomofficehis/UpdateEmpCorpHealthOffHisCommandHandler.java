package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empcomofficehis;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.*;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;

@Stateless
public class UpdateEmpCorpHealthOffHisCommandHandler
        extends CommandHandler<UpdateEmpCorpHealthOffHisCommand>
        implements PeregUpdateCommandHandler<UpdateEmpCorpHealthOffHisCommand> {

    @Inject
    private EmpCorpHealthOffHisRepository empCorpHealthOffHisRepository;

    @Inject
    private AffOfficeInformationRepository affOfficeInformationRepository;

    @Inject
    private EmpCorpHealthOffHisService empCorpHealthOffHisService;

    @Override
    public String targetCategoryCd() {
        return "CS00075";
    }

    @Override
    public Class<?> commandClass() {
        return UpdateEmpCorpHealthOffHisCommand.class;
    }

    @Override
    protected void handle(CommandHandlerContext<UpdateEmpCorpHealthOffHisCommand> context) {
        val command = context.getCommand();
        Optional<EmpCorpHealthOffHis> existHist = empCorpHealthOffHisRepository.getBySidDesc(command.getEmployeeId());
        Optional<DateHistoryItem> itemToBeUpdate = existHist.get().getPeriod().stream()
                .filter(h -> h.identifier().equals(command.getHistId()))
                .findFirst();
        if(itemToBeUpdate.isPresent()){
            existHist.get().changeSpan(itemToBeUpdate.get(), new DatePeriod(command.getStartDate(),
                    command.getEndDate()!= null? command.getEndDate(): GeneralDate.max()));
            AffOfficeInformation updateInfo = new AffOfficeInformation(itemToBeUpdate.get().identifier(), new SocialInsuranceOfficeCode(command.getCode()));
            empCorpHealthOffHisService.update(existHist.get(), itemToBeUpdate.get(), updateInfo);
        }
    }
}
