/**
 * 
 */
package nts.uk.ctx.hr.notice.infra.repository.report.registration.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

	private static final String getListByReportId = "select c FROM  JhndtReportAtcFile c Where c.pk.cid = :cid and c.reportID = :reportId ";
	
	private static final String getListByCid = "select c FROM  JhndtReportAtcFile c Where c.pk.cid = :cid ";
	
	
	private static final String getListByReportId2 = "select e "
			+ " FROM JhndtReportAtcFile e "
			+ " Where e.pk.cid = :cid and  e.reportID = :reportId and e.delFlg = 0";
	
	private static final String getListByLayoutId = "select c.pk.cid, c.pk.reportLayoutID, c.pk.docID,"
			+ "c.docName, c.dispOrder, c.requiredDoc, d.docRemarks, d.sampleFileId, d.sampleFileName "
			+ " FROM  JhndtReportDocument c "
			+ " INNER JOIN JhndtDocument d ON c.pk.docID = d.pk.docID and c.pk.cid =  d.pk.cid "
			+ " Where c.pk.cid = :cid and c.pk.reportLayoutID = :reprtLayoutId "
			+ " ORDER BY c.dispOrder asc";
	
	private AttachmentPersonReportFile toDomain(JhndtReportAtcFile entity) {
		return entity.toDomain();
	}
	
	private JhndtReportAtcFile toEntity(AttachmentPersonReportFile domain) {
		return JhndtReportAtcFile.toEntity(domain);
	}
	
	@Override
	public List<AttachmentPersonReportFile> getListDomainByReportId(String cid, Integer reportId) {
		return this.queryProxy().query(getListByReportId, JhndtReportAtcFile.class)
				.setParameter("cid", cid)
				.setParameter("reportId", reportId).getList(c -> toDomain(c));
	}
	
	@Override
	public List<AttachmentPersonReportFile> getListAttachFileByCid(String cid) {
		return this.queryProxy().query(getListByCid, JhndtReportAtcFile.class)
				.setParameter("cid", cid).getList(c -> toDomain(c));
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
			entity.setFileName(null);
			this.commandProxy().update(entity);
		}else{
			System.out.println("file not exit");
		}
	}
	
	@Override
	public List<DocumentSampleDto> getListDomainByLayoutId(String cid, int reprtLayoutId, Integer reportId) {
		
		List<DocumentSampleDto> result = new ArrayList<>();
		List<JhndtReportAtcFile> listAttachFile   = new ArrayList<>();
		
		List<Object[]> listObjSample = this.queryProxy().query(getListByLayoutId, Object[].class)
				.setParameter("cid", cid)
				.setParameter("reprtLayoutId", reprtLayoutId)
				.getList();

		if (listObjSample.isEmpty()) {
			return new ArrayList<DocumentSampleDto>();
		}
		
		if (reportId != null) {
			listAttachFile = this.queryProxy().query(getListByReportId2, JhndtReportAtcFile.class)
					.setParameter("cid", cid)
					.setParameter("reportId", reportId)
					.getList();
		}
		
		for (Object[] obj : listObjSample ){
			DocumentSampleDto dto = new DocumentSampleDto();
			int docId = obj[2] == null ? null : Integer.valueOf(obj[2].toString());
			dto.setCid(obj[0] == null ? null : obj[0].toString());
			dto.setReportLayoutID(obj[1] == null ? null : Integer.valueOf(obj[1].toString()));
			dto.setDocID(obj[2] == null ? null : Integer.valueOf(obj[2].toString()));
			dto.setDocName(obj[3] == null ? null : obj[3].toString());
			dto.setDispOrder(obj[4] == null ? null : Integer.valueOf(obj[4].toString()));
			dto.setRequiredDoc(obj[5] == null ? null : Integer.valueOf(obj[5].toString()));
			dto.setDocRemarks(obj[6] == null ? null : obj[6].toString());
			dto.setSampleFileId(obj[7] == null ? null : obj[7].toString());
			dto.setSampleFileName(obj[8] == null ? null : obj[8].toString());
			if (!listAttachFile.isEmpty()) {
				Optional<JhndtReportAtcFile> attachFile = listAttachFile.stream().filter(e -> e.docID == docId).findFirst();
				if (attachFile.isPresent()) {
					dto.setReportID(attachFile.get().reportID);
					dto.setFileId(attachFile.get().pk.fileId); 
					dto.setFileName(attachFile.get().fileName);
					dto.setFileSize(attachFile.get().fileSize); 
				}
			}
			
			result.add(dto);
		}
		
		return result;
	}

}
