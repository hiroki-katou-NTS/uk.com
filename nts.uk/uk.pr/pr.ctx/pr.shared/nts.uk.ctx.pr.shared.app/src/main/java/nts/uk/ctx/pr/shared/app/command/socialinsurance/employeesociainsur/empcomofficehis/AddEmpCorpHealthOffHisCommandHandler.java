package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empcomofficehis;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.*;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Optional;

@Stateless
public class AddEmpCorpHealthOffHisCommandHandler
        extends CommandHandlerWithResult<AddEmpCorpHealthOffHisCommand, PeregAddCommandResult>
        implements PeregAddCommandHandler<AddEmpCorpHealthOffHisCommand>{

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
        return AddEmpCorpHealthOffHisCommand.class;
    }

    @Override
    protected PeregAddCommandResult handle(CommandHandlerContext<AddEmpCorpHealthOffHisCommand> context) {

        val command = context.getCommand();
        String newHistId = IdentifierUtil.randomUniqueId();
        Optional<EmpCorpHealthOffHis> listHist = empCorpHealthOffHisRepository.getBySidAsc(command.getSid());
        DateHistoryItem itemAdded = new DateHistoryItem(newHistId,
                new DatePeriod(command.getStartDate(), command.getEndDate()!= null? command.getEndDate(): GeneralDate.max()));
        EmpCorpHealthOffHis domain = new EmpCorpHealthOffHis(command.getSid(), new ArrayList<>());
        AffOfficeInformation newHistInfo = new AffOfficeInformation(itemAdded.identifier(),
                new SocialInsuranceOfficeCode(command.getSocialInsurOfficeCode()));
        if(listHist.isPresent()) {
            domain = listHist.get();
        }
        domain.add(itemAdded);
        empCorpHealthOffHisService.add(domain, itemAdded, newHistInfo);
        return new PeregAddCommandResult(newHistId);
    }
}
