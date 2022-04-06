package nts.uk.ctx.at.shared.infra.entity.scherec.addsettingofworktime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * エンティティ：労働時間の加算設定(含める要素を指定)
 * @author shuichi_ishida
 */
@Entity
@Table(name = "KSRMT_CALC_C_ADD_INCLUDE")
public class KsrmtCalcCAddInclude extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KsrmtCalcCAddIncludePK pk;
	
	/** 実働のみで計算する */
	@Column(name = "IS_ACTUAL_ONLY")
	public boolean isActualOnly;
	/** 休暇を含める */
	@Column(name = "IS_VACATION")
	public Boolean isVacation;
	/** 育児介護時間を含める */
	@Column(name = "IS_CARE")
	public Boolean isCare;
	/** 遅刻早退の扱いを就業時間帯毎に設定する */
	@Column(name = "LATE_SETTING_UNIT")
	public Boolean lateSettingUnit;
	/** 遅刻早退時間を含める */
	@Column(name = "IS_LATE")
	public Boolean isLate;
	/** 遅刻早退を申請にて取り消した場合も含める */
	@Column(name = "IS_LATE_DELETE_APP")
	public Boolean isLateDeleteApp;
	/** インターバル免除時間を含める */
	@Column(name = "IS_INTERVAL_TIME")
	public Boolean isIntervalTime;
	
	public int isActualOnly() {
		/** ENUM CalcurationByActualTimeAtr参照（0：実働のみ、1：実働以外）*/
		return this.isActualOnly ? 0 : 1;
	}

	public Integer getIsVacation() {
		return convertToInt(this.isVacation);
	}

	public Integer getIsCare() {
		return convertToInt(this.isCare);
	}

	public Integer getLateSettingUnit() {
		return convertToInt(this.lateSettingUnit);
	}

	public Integer getIsLate() {
		return convertToInt(this.isLate);
	}

	public Integer getIsLateDeleteApp() {
		return convertToInt(this.isLateDeleteApp);
	}

	public Integer getIsIntervalTime() {
		return convertToInt(this.isIntervalTime);
	}

	@Override
	protected Object getKey() {
		return this.pk;
	}
	
	public static Integer convertToInt(Boolean flag) {
		if (flag == null) return null;
		
		return flag ? 1 : 0;
	}
}
