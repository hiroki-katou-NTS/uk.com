/**
 * 
 */
package nts.uk.ctx.hr.notice.infra.repository.report.registration.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.AttachPersonReportFileRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.AttachmentPersonReportFile;
import nts.uk.ctx.hr.notice.dom.report.registration.person.DocumentSampleDto;
import nts.uk.ctx.hr.notice.infra.entity.report.registration.person.JhndtReportAtcFile;
import nts.uk.ctx.hr.notice.infra.entity.report.registration.person.JhndtReportAtcFilePK;

/**
 * @author laitv
 *
 */
@Stateless
public class JpaAttachPersonReportFileRepository extends JpaRepository  implements AttachPersonReportFileRepository{

	private static final String getListByReprtId = "select c FROM  JhndtReportAtcFile c Where c.pk.cid = :cid and c.reportID = :reportId ";
	
	private static final String getListByLayoutId = "select c.pk.cid, c.pk.reportLayoutID, c.pk.docID,"
			+ "c.docName, c.dispOrder, c.requiredDoc, d.docRemarks, d.sampleFileId, d.sampleFileName "
			+ " e.reportID, e.pk.fileId, e.fileName, e.fileSize "
			+ " FROM  JhndtReportDocument c "
			+ " INNER JOIN JhndtDocument d ON c.pk.docID = d.pk.docID and c.pk.cid =  d.pk.cid "
			+ " INNER JOIN JhndtReportAtcFile e ON e.docID = d.pk.docID and e.pk.cid = d.pk.cid "
			+ " Where c.pk.cid = :cid and c.pk.reportLayoutID = :reprtLayoutId "
			+ " and e.reportID =: reportId and e.delFlg = 0 ORDER BY c.dispOrder asc";
	
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
		Optional<JhndtReportAtcFile> entityOpt = this.queryProxy().find(pk , JhndtReportAtcFile.class);
		if (entityOpt.isPresent()) {
			JhndtReportAtcFile entity = entityOpt.get();
			entity.setDelFlg(1);
			this.commandProxy().update(entity);
		}else{
			System.out.println("file not exit");
		}
		
	}
	
	@Override
	public List<DocumentSampleDto> getListDomainByLayoutId(String cid, int reprtLayoutId, int reportId ) {
		List<Object[]> listObj = this.queryProxy().query(getListByLayoutId, Object[].class)
				.setParameter("cid", cid)
				.setParameter("reprtLayoutId", reprtLayoutId)
				.setParameter("reportId", reportId).getList();
		if (listObj.isEmpty()) {
			return new ArrayList<DocumentSampleDto>();
		}
		
		List<DocumentSampleDto> result = listObj.stream().map(obj -> 
		new DocumentSampleDto(
				obj[0] == null ? null : obj[0].toString(),
				obj[1] == null ? null : Integer.valueOf(obj[1].toString()),
				obj[2] == null ? null : Integer.valueOf(obj[2].toString()), 
				obj[3] == null ? null : obj[3].toString(),
				obj[4] == null ? null : Integer.valueOf(obj[4].toString()),
				obj[5] == null ? null : Integer.valueOf(obj[5].toString()), 
				obj[6] == null ? null : obj[6].toString(),
				obj[7] == null ? null : Integer.valueOf(obj[7].toString()),
				obj[8] == null ? null : obj[8].toString(),
				obj[9] == null ? null : Integer.valueOf(obj[9].toString()),	
				obj[10] == null ? null : obj[10].toString(),
				obj[11] == null ? null : obj[11].toString(),
				obj[12] == null ? null : Integer.valueOf(obj[12].toString())
				)).collect(Collectors.toList());
		return result;
	}

}
