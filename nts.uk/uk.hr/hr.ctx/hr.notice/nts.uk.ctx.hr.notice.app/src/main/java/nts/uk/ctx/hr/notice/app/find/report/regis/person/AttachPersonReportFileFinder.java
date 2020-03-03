/**
 * 
 */
package nts.uk.ctx.hr.notice.app.find.report.regis.person;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.notice.dom.report.registration.person.AttachPersonReportFileRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.DocumentSampleDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv
 * アルゴリズム「起動処理」を実行する (Thực hiện thuật toán "Xử lý khởi động")
 */

@Stateless
public class AttachPersonReportFileFinder {

	@Inject
	private AttachPersonReportFileRepository repo;

	public List<DocumentSampleDto> findAll(int layoutReportId, Integer reportId) {
		String cid = AppContexts.user().companyId();
		List<DocumentSampleDto> result = repo.getListDomainByLayoutId(cid, layoutReportId, reportId);
		return result;
	}
}
