/**
 * 
 */
package nts.uk.ctx.hr.notice.infra.repository.report.registration.person;

import java.util.List;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.AttachPersonReportFileRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.AttachmentPersonReportFile;
import nts.uk.ctx.hr.notice.infra.entity.report.registration.person.JhndtReportAtcFile;
import nts.uk.ctx.hr.notice.infra.entity.report.registration.person.JhndtReportAtcFilePK;

/**
 * @author laitv
 *
 */
public class JpaAttachPersonReportFileRepository extends JpaRepository  implements AttachPersonReportFileRepository{

	private static final String getListByReprtId = "select c FROM  JhndtReportAtcFile c Where c.pk.cid = :cid and c.reportID = :reportId ";
	
	private AttachmentPersonReportFile toDomain(JhndtReportAtcFile entity) {
		return entity.toDomain();
	}
	
	private JhndtReportAtcFile toEntity(AttachmentPersonReportFile domain) {
		return JhndtReportAtcFile.toEntity(domain);
	}
	
	@Override
	public List<AttachmentPersonReportFile> getListDomainByReportId(String cid, String reportId) {
		return this.queryProxy().query(getListByReprtId, JhndtReportAtcFile.class)
				.setParameter("cid", cid)
				.setParameter("reportId", reportId).getList(c -> toDomain(c));
	}

	@Override
	public void add(AttachmentPersonReportFile domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void delete(String fileId, String cid) {
		JhndtReportAtcFilePK pk = new JhndtReportAtcFilePK(fileId, cid);
		this.commandProxy().remove(JhndtReportAtcFile.class, pk);
	}

}
