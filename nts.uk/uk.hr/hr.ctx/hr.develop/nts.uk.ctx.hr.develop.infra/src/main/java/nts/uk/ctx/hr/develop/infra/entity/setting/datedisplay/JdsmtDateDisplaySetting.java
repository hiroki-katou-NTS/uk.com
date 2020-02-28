package nts.uk.ctx.hr.develop.infra.entity.setting.datedisplay;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.hr.develop.dom.setting.datedisplay.DateDisplaySetting;
import nts.uk.ctx.hr.develop.dom.setting.datedisplay.DateDisplaySettingValue;
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
	public Integer endDateSetClass;

	@Column(name = "END_DATE_SET_NUM")
	public Integer endDateSetNum;

	@Column(name = "END_DATE_SET_MONTH")
	public Integer endDateSetMonth;

	@Column(name = "END_DATE_SET_DATE")
	public Integer endDateSetDate;

	@Override
	protected Object getKey() {
		return programId;
	}

	public JdsmtDateDisplaySetting(DateDisplaySetting domain) {
		this.programId = domain.getProgramId();
		this.cid = domain.getCompanyId();
		// 終了日設定
		DateDisplaySettingValue startDateSet = domain.getStartDateSetting();
		this.startDateSetClass = startDateSet.getSettingClass().value;
		this.startDateSetDate = startDateSet.getSettingDate();
		this.startDateSetMonth = startDateSet.getSettingMonth();
		this.startDateSetNum = startDateSet.getSettingNum();
		// 開始日設定
		Optional<DateDisplaySettingValue> oEndDateSet = domain.getEndDateSetting();
		if (oEndDateSet.isPresent()) {
			DateDisplaySettingValue endDateSet = oEndDateSet.get();
			this.endDateSetClass = endDateSet.getSettingClass().value;
			this.endDateSetDate = endDateSet.getSettingDate();
			this.endDateSetMonth = endDateSet.getSettingMonth();
			this.endDateSetNum = endDateSet.getSettingNum();
		}

	}

	public JdsmtDateDisplaySetting(String companyId, DateDisplaySetting domain) {
		this.programId = domain.getProgramId();
		this.cid = companyId;
		// 終了日設定
		DateDisplaySettingValue startDateSet = domain.getStartDateSetting();
		this.startDateSetClass = startDateSet.getSettingClass().value;
		this.startDateSetDate = startDateSet.getSettingDate();
		this.startDateSetMonth = startDateSet.getSettingMonth();
		this.startDateSetNum = startDateSet.getSettingNum();
		// 開始日設定
		Optional<DateDisplaySettingValue> oEndDateSet = domain.getEndDateSetting();
		if (oEndDateSet.isPresent()) {
			DateDisplaySettingValue endDateSet = oEndDateSet.get();
			this.endDateSetClass = endDateSet.getSettingClass().value;
			this.endDateSetDate = endDateSet.getSettingDate();
			this.endDateSetMonth = endDateSet.getSettingMonth();
			this.endDateSetNum = endDateSet.getSettingNum();
		}
	}

}
