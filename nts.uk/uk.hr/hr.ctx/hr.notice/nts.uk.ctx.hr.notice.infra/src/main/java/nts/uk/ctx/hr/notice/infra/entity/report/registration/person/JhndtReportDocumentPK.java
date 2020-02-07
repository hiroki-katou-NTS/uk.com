package nts.uk.ctx.hr.notice.infra.entity.report.registration.person;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class JhndtReportDocumentPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "CID")
	String cid; //会社ID
	
	@NotNull
	@Column(name = "RPT_LAYOUT_ID")
	public int  reportLayoutID; // 個別届出種類ID
	
	@NotNull
	@Column(name = "DOC_ID")
	public int docID; //書類ID
}
