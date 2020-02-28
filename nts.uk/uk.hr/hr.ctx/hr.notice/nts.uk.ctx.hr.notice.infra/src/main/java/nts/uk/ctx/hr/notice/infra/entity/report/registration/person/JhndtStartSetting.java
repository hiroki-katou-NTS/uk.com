package nts.uk.ctx.hr.notice.infra.entity.report.registration.person;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ReportStartSetting;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "JHNMT_START_SETTING")
public class JhndtStartSetting extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CID")
	public String cid;
	
	@Column(name = "CHANGE_DISP")
	public boolean changeDisp;
	
	@Override
	public Object getKey() {
		return cid;
	}

	public ReportStartSetting toDomain() {
		return ReportStartSetting.createFromJavaType(
				 this.cid,
				 this.changeDisp);
	}
}
