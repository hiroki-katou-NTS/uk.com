package nts.uk.ctx.sys.portal.infra.entity.logsettings;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
import nts.uk.ctx.sys.portal.dom.logsettings.LogSetting;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SRCDT_LOG_SETTING")
@EqualsAndHashCode(callSuper = true)
public class SrcdtLogSetting extends UkJpaEntity
		implements Serializable, LogSetting.MementoGetter, LogSetting.MementoSetter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public SrcdtLogSettingPK srcdtLogSettingPK;

	/**
	 * ログイン履歴記録.するしない区分
	 */
	@Column(name = "LOGIN_LOG_USE_ATR")
	public int loginLogUseAtr;

	/**
	 * 起動履歴記録．するしない区分
	 */
	@Column(name = "STARTUP_LOG_USE_ATR")
	public int startupLogUseAtr;

	/**
	 * 修正履歴（データ）記録．するしない区分
	 */
	@Column(name = "UPDATE_LOG_USE_ATR")
	public int updateLogUseAtr;

	@Override
	public void setSystem(int system) {
		if (this.srcdtLogSettingPK != null) {
			this.srcdtLogSettingPK = new SrcdtLogSettingPK();
		}
		this.srcdtLogSettingPK.setSystem(system);
	}

	@Override
	public void setProgramId(String programId) {
		if (this.srcdtLogSettingPK != null) {
			this.srcdtLogSettingPK = new SrcdtLogSettingPK();
		}
		this.srcdtLogSettingPK.setProgramId(programId);
	}

	@Override
	public void setMenuClassification(Integer menuClassification) {
		this.setMenuClassification(menuClassification);
	}

	@Override
	public void setLoginHistoryRecord(Integer loginHistoryRecord) {
		this.setLoginHistoryRecord(loginHistoryRecord);
	}

	@Override
	public void setCompanyId(String companyId) {
		if (this.srcdtLogSettingPK != null) {
			this.srcdtLogSettingPK = new SrcdtLogSettingPK();
		}
		this.srcdtLogSettingPK.setCid(companyId);
	}

	@Override
	public void setEditHistoryRecord(Integer editHistoryRecord) {
		this.setEditHistoryRecord(editHistoryRecord);
	}

	@Override
	public void setBootHistoryRecord(Integer bootHistoryRecord) {
		this.setBootHistoryRecord(bootHistoryRecord);
	}

	@Override
	public int getSystem() {
		if (this.srcdtLogSettingPK != null) {
			return this.srcdtLogSettingPK.getSystem();
		}
		return 0;
	}

	@Override
	public String getProgramId() {
		if (this.srcdtLogSettingPK != null) {
			return this.srcdtLogSettingPK.getProgramId();
		}
		return null;
	}

	@Override
	public Integer getMenuClassification() {
		if (this.srcdtLogSettingPK != null) {
			return this.srcdtLogSettingPK.getMenuClassification();
		}
		return null;
	}

	@Override
	public Integer getLoginHistoryRecord() {
		return this.getLoginLogUseAtr();
	}

	@Override
	public String getCompanyId() {
		if (this.srcdtLogSettingPK != null) {
			this.srcdtLogSettingPK.getCid();
		}
		return null;
	}

	@Override
	public Integer getEditHistoryRecord() {
		return this.getUpdateLogUseAtr();
	}

	@Override
	public Integer getBootHistoryRecord() {
		return this.getStartupLogUseAtr();
	}

	@Override
	protected SrcdtLogSettingPK getKey() {
		return srcdtLogSettingPK;
	}

}
