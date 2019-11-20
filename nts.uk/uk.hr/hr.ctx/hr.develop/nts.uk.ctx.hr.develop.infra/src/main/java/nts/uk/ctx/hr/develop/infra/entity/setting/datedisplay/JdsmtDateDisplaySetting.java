package nts.uk.ctx.hr.develop.infra.entity.setting.datedisplay;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "JDSMT_DATE_DISPLAY_SETTING")
public class JdsmtDateDisplaySetting extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PROGRAM_ID")
	public String programId;

	@Column(name = "EXCLUS_VER")
	public int exclusVer;

	@Column(name = "CID")
	public String cid;

	@Column(name = "START_DATE_SET_CLASS")
	public int startDateSetClass;

	@Column(name = "START_DATE_SET_NUM")
	public int startDateSetNum;

	@Column(name = "START_DATE_SET_MONTH")
	public int startDateSetMonth;

	@Column(name = "START_DATE_SET_DATE")
	public int startDateSetDate;

	@Column(name = "END_DATE_SET_CLASS")
	public int endDateSetClass;

	@Column(name = "END_DATE_SET_NUM")
	public int endDateSetNum;

	@Column(name = "END_DATE_SET_MONTH")
	public int endDateSetMonth;

	@Column(name = "END_DATE_SET_DATE")
	public int endDateSetDate;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return null;
	}
}
