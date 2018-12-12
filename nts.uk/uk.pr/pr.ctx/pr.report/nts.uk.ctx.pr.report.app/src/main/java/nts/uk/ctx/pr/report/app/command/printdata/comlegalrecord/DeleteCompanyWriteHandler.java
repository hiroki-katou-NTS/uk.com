package nts.uk.ctx.pr.report.app.command.printdata.comlegalrecord;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.pr.report.dom.printdata.comlegalrecord.CompanyStatutoryWriteRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class DeleteCompanyWriteHandler extends CommandHandlerWithResult<DeleteCompanyWriteCommand, List<String>> {

    @Inject
    private CompanyStatutoryWriteRepository companyStatutoryWriteRepository;

    @Override
    protected List<String> handle(CommandHandlerContext<DeleteCompanyWriteCommand> context) {
        List<String> response = new ArrayList<>();
        //ドメインモデル「法定調書用会社」を削除する
        DeleteCompanyWriteCommand data = context.getCommand();
        companyStatutoryWriteRepository.remove(AppContexts.user().companyId(),data.getCode());
        response.add(data.getCode());
        return response;
    }

}
