/**
 * 
 */
package nts.uk.ctx.hr.notice.app.command.report.regis.person;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.hr.notice.dom.report.registration.person.AttachPersonReportFileRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReportRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv
 * アルゴリズム「アップロードファイル削除処理」を実行する(Thực hiện thuật toán ""Xử lý delete Upload file "")
 */
@Stateless
public class DeleteAttachPersonReportFileHandler extends CommandHandler<DeleteDocumentReportCommand>{

	@Inject
	private AttachPersonReportFileRepository repo;
	
	@Inject
	private RegistrationPersonReportRepository repoPersonReport;
	
	@Override
	protected void handle(CommandHandlerContext<DeleteDocumentReportCommand> context) {
		DeleteDocumentReportCommand command = context.getCommand();
		repo.delete(command.fileId, command.cid);
		
		updateMissingDocName(AppContexts.user().companyId(),Integer.valueOf(command.getReportId()), command.getMissingDocName());
	}
	
	private void updateMissingDocName(String cid, int reportId, String missingDocName) {
		this.repoPersonReport.updateMissingDocName(cid, reportId, missingDocName);
	}

}
