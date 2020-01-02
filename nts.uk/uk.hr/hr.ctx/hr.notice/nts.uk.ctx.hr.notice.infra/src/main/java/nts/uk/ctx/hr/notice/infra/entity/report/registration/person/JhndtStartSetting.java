package nts.uk.ctx.hr.notice.infra.entity.report.registration.person;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.hr.notice.dom.report.registration.person.Document;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "JHNMT_START_SETTING")
public class JhndtStartSetting extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public JhndtDocumentPK pk;
	
	@Column(name = "DOC_NAME")
	public String docName; //書類名
	
	@Column(name = "DOC_REMARKS")
	public String docRemarks; //備考
	
	@Column(name = "SAMPLE_FILE_ID")
	public int sampleFileId; //サンプルファイルID
	
	@Column(name = "SAMPLE_FILE_NAME")
	public String sampleFileName; //サンプルファイル名
	
	@Override
	public Object getKey() {
		return pk;
	}


}
