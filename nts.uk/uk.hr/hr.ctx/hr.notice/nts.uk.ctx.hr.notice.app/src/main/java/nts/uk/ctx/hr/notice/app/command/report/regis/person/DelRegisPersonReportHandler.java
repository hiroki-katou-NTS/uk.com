/**
 * 
 */
package nts.uk.ctx.hr.notice.app.command.report.regis.person;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReport;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReportRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv
 * アルゴリズム「届出情報削除処理」を実行する(Thực hiện thuật toán "Xử lý delete thông tin report")
 */
@Stateless
public class DelRegisPersonReportHandler extends CommandHandler<RemoveReportCommand>{
	
	@Inject
	private RegistrationPersonReportRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<RemoveReportCommand> context) {
		RemoveReportCommand command = context.getCommand();
		String cid = AppContexts.user().companyId();
		Optional<RegistrationPersonReport> domainReportOpt = repo.getDomainByReportId(cid, command.reportId);
		if (!domainReportOpt.isPresent()) {
			return;
		}
		
		// 届出IDをキーとしたドメイン「人事届出の登録.削除済」=trueに設定する 
		// (Cài Đặt tên miền "Đăng ký HR report. Đã xóa" = true với ID report làm khóa)
		repo.remove(cid, command.reportId);
	}
	
}
