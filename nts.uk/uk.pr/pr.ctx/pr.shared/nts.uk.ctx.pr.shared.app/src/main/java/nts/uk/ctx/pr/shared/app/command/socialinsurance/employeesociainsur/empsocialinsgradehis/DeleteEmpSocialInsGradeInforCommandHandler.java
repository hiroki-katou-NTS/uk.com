package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empsocialinsgradehis;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeHis;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeHisRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeHisService;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeInfoRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.pereg.app.command.PeregDeleteCommandHandler;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.swing.text.html.Option;
import java.util.Optional;

@Stateless
public class DeleteEmpSocialInsGradeInforCommandHandler
        extends CommandHandler<DeleteEmpSocialInsGradeInforCommand>
        implements PeregDeleteCommandHandler<DeleteEmpSocialInsGradeInforCommand> {

    @Inject
    private EmpSocialInsGradeHisRepository esighRepository;

    @Inject
    private EmpSocialInsGradeInfoRepository esigiRepository;

    @Inject
    private EmpSocialInsGradeHisService empSocialInsGradeHisService;

    @Override
    public String targetCategoryCd() {
        return "CS00092";
    }

    @Override
    public Class<?> commandClass() {
        return DeleteEmpSocialInsGradeInforCommand.class;
    }

    @Override
    protected void handle(CommandHandlerContext<DeleteEmpSocialInsGradeInforCommand> context) {
        val command = context.getCommand();
        String companyId = AppContexts.user().companyId();

        Optional<EmpSocialInsGradeHis> existHist = esighRepository.getEmpSocialInsGradeHisBySId(companyId, command.getSId());

        if (!existHist.isPresent()){
            throw new RuntimeException("invalid EmpSocialInsGradeHis");
        }
        Optional<YearMonthHistoryItem> itemToBeDelete = existHist.get().getYearMonthHistoryItems().stream()
                .filter(h -> h.identifier().equals(command.getHistoryId()))
                .findFirst();

        if (!itemToBeDelete.isPresent()){
            throw new RuntimeException("invalid EmpSocialInsGradeHis");
        }
        existHist.get().remove(itemToBeDelete.get());
        empSocialInsGradeHisService.delete(existHist.get(),itemToBeDelete.get());

        esigiRepository.delete(command.getHistoryId());
    }
}
