/**
 * 
 */
package nts.uk.ctx.hr.notice.infra.repository.report.registration.person;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.DocRequiredForReportRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.DocumentSampleDto;

/**
 * @author laitv
 *
 */
public class JpaDocRequiredForReportRepository extends JpaRepository implements DocRequiredForReportRepository{

	private static final String getListByLayoutId = "select c.pk.cid, c.pk.reportLayoutID, c.pk.docID,"
			+ "c.docName, c.dispOrder, c.requiredDoc, d.docRemarks, d.sampleFileId, d.sampleFileName "
			+ " FROM  JhndtReportDocument c "
			+ " INNER JOIN JhndtDocument d ON c.pk.docID = d.pk.docID and c.pk.cid =  d.pk.cid "
			+ " Where c.pk.cid = :cid and c.pk.reportLayoutID = :reprtLayoutId ORDER BY c.dispOrder asc";
	
	@Override
	public List<DocumentSampleDto> getListDomainByLayoutId(String cid, int reprtLayoutId) {
		List<Object[]> listObj = this.queryProxy().query(getListByLayoutId, Object[].class)
				.setParameter("cid", cid)
				.setParameter("reprtLayoutId", reprtLayoutId).getList();
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
				obj[8] == null ? null : obj[8].toString())).collect(Collectors.toList());
		return result;
	}

}
