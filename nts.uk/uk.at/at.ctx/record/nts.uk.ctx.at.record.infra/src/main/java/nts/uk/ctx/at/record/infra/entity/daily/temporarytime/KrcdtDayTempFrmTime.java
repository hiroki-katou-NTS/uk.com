package nts.uk.ctx.at.record.infra.entity.daily.temporarytime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.infra.entity.daily.time.KrcdtDayTimeAtd;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.TemporaryFrameTimeOfDaily;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * エンティティ：日別実績の臨時枠時間
 * @author shuichi_ishida
 */
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_DAY_TEMP_FRM_TIME")
public class KrcdtDayTempFrmTime extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtDayTempFrmTimePK krcdtDayTempFrmTimePK;

	/** 臨時時間 */
	@Column(name ="TEMPORARY_TIME")
	public int temporaryTime;
	/** 臨時深夜時間 */
	@Column(name ="TEMPORARY_MIDN_TIME")
	public int temporaryMidnightTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns(value = {
			@JoinColumn(name = "SID", referencedColumnName = "SID", insertable = false, updatable = false),
			@JoinColumn(name = "YMD", referencedColumnName = "YMD", insertable = false, updatable = false) })
	public KrcdtDayTimeAtd krcdtDayTime;
	
	@Override
	protected Object getKey() {
		return this.krcdtDayTempFrmTimePK;
	}

	/**
	 * エンティティに変換
	 * @param employeeId 社員ID
	 * @param ymd 年月日
	 * @param domain 日別実績の臨時枠時間
	 * @return エンティティ
	 */
	public static KrcdtDayTempFrmTime toEntity(String employeeId, GeneralDate ymd, TemporaryFrameTimeOfDaily domain) {
		val entity = new KrcdtDayTempFrmTime();
		entity.krcdtDayTempFrmTimePK = new KrcdtDayTempFrmTimePK(employeeId, ymd, domain.getWorkNo().v());
		entity.setData(domain);
		return entity;
	}

	/**
	 * データ設定
	 * @param domain 日別実績の臨時枠時間
	 */
	public void setData(TemporaryFrameTimeOfDaily domain) {
		this.temporaryTime = domain.getTemporaryTime().valueAsMinutes();
		this.temporaryMidnightTime = domain.getTemporaryLateNightTime().valueAsMinutes();
	}
	
	/**
	 * ドメインに変換
	 * @return 日別実績の臨時枠時間
	 */
	public TemporaryFrameTimeOfDaily toDomain() {
		return new TemporaryFrameTimeOfDaily(
				new WorkNo(this.krcdtDayTempFrmTimePK.workNo),
				new AttendanceTime(this.temporaryTime),
				new AttendanceTime(this.temporaryMidnightTime));
	}
}
