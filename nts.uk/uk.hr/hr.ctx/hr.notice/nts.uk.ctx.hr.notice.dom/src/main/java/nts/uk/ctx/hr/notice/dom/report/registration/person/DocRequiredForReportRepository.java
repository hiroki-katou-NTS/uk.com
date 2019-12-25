/**
 * 
 */
package nts.uk.ctx.hr.notice.dom.report.registration.person;

import java.util.List;

/**
 * @author laitv
 *
 */
public interface DocRequiredForReportRepository {
	
	List<DocumentSampleDto> getListDomainByLayoutId(String cid, int reprtLayoutId);

	
}
