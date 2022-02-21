package nts.uk.screen.at.app.command.kdl.kdl016;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportableEmployeeRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee.EmployeeAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee.importeddto.EmployeeInfoImport;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class DeleteSupportInfoCommandHandler extends CommandHandlerWithResult<DeleteSupportInfoCommand, DeleteSupportInfoResult> {
    @Inject
    private SupportableEmployeeRepository supportableEmployeeRepo;

    @Inject
    private EmployeeAdapter employeeAdapter;

    @Override
    protected DeleteSupportInfoResult handle(CommandHandlerContext<DeleteSupportInfoCommand> commandHandlerContext) {
        DeleteSupportInfoCommand command = commandHandlerContext.getCommand();

        // 1. List<応援可能な社員>
        val supportEmployees = supportableEmployeeRepo.get(command.getEmployeeIds());


        supportEmployees.forEach(se -> {
            // 2. 応援可能な社員から応援予定を変更する.削除する(@Require, 応援可能な社員) : 1件以上存在する場合
            // TODO: UpdateSupportScheduleFromSupportableEmployee.delete();  Domain & domain service chưa có

            // 3.List<応援可能な社員からの登録結果> :filter $.エラーがあるか == false

            // 3.1 delete(List<応援可能な社員ID>)
            supportableEmployeeRepo.delete("");

            // 4.社員IDリスト = List<応援可能な社員からの登録結果> ：
            //　filter $.エラーがあるか == true
            //　map $.エラー情報.応援可能な社員.社員ID
            List<String> employeeErrors = new ArrayList<>();
            List<EmployeeInfoImport> employeeErrorInfors = employeeAdapter.getByListSid(employeeErrors);


        });

        return new DeleteSupportInfoResult();
    }
}
