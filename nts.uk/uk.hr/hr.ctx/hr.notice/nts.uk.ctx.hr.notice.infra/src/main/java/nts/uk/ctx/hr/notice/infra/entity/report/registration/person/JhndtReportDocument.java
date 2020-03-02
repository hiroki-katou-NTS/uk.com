package nts.uk.ctx.hr.notice.infra.entity.report.registration.person;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.hr.notice.dom.report.registration.person.DocumentRequiredForReport;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "JHNMT_RPT_DOC")
public class JhndtReportDocument extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public JhndtReportDocumentPK pk;
	
	@Column(name = "DOC_NAME")
	public String docName; //書類名
	
	@Column(name = "DISPORDER")
	public int dispOrder; //表示順
	
	@Column(name = "REQUIRED_DOC")
	public int requiredDoc; //必須書類
	
	@Override
	public Object getKey() {
		return pk;
	}

	public DocumentRequiredForReport toDomain() {
		return DocumentRequiredForReport.createFromJavaType(
				 this.pk.cid,
				 this.pk.reportLayoutID,
				 this.pk.docID,
				 this.docName,
				 this.dispOrder,
				 this.requiredDoc == 1 ? true : false );
	}
}
