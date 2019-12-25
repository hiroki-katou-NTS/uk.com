package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empcomofficehis;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHis;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHisRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHisService;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.pereg.app.command.PeregDeleteCommandHandler;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class DeleteEmpCorpHealthOffHisCommandHandler extends CommandHandler<DeleteEmpCorpHealthOffHisCommand>
        implements PeregDeleteCommandHandler<DeleteEmpCorpHealthOffHisCommand> {

    @Inject
    private EmpCorpHealthOffHisRepository empCorpHealthOffHisRepository;

    @Inject
    private EmpCorpHealthOffHisService empCorpHealthOffHisService;


    @Override
    public String targetCategoryCd() {
        return "CS00075";
    }

    @Override
    public Class<?> commandClass() {
        return DeleteEmpCorpHealthOffHisCommand.class;
    }

    @Override
    protected void handle(CommandHandlerContext<DeleteEmpCorpHealthOffHisCommand> context) {
        val command = context.getCommand();
        Optional<EmpCorpHealthOffHis> listHist = empCorpHealthOffHisRepository.getBySidDesc(command.getEmployeeId());
        if (!listHist.isPresent()) {
            throw new RuntimeException("invalid CorpHealthOffHistory");
        }
        Optional<DateHistoryItem> itemToBeDeleted = listHist.get().getPeriod().stream()
                .filter(h -> h.identifier().equals(command.getHistId()))
                .findFirst();
        if (!itemToBeDeleted.isPresent()) {
            throw new RuntimeException("invalid CorpHealthOffHistory");
        }
        listHist.get().remove(itemToBeDeleted.get());
        empCorpHealthOffHisService.delete(listHist.get(), itemToBeDeleted.get());
    }


}
