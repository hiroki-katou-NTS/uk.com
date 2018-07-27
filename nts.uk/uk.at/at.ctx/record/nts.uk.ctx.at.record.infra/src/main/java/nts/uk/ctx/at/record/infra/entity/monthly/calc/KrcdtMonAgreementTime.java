package nts.uk.ctx.at.record.infra.entity.monthly.calc;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfMonthly;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitOneMonth;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTime;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 月別実績の36協定時間
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_MON_AGREEMENT_TIME")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtMonAgreementTime extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonAttendanceTimePK PK;
	
	/** 36協定時間 */
	@Column(name = "AGREEMENT_TIME")
	public int agreementTime;
	
	/** 限度エラー時間 */
	@Column(name = "LIMIT_ERROR_TIME")
	public int limitErrorTime;
	
	/** 限度アラーム時間 */
	@Column(name = "LIMIT_ALARM_TIME")
	public int limitAlarmTime;
	
	/** 特例限度エラー時間 */
	@Column(name = "EXCEPT_LIMIT_ERR_TIME")
	public Integer exceptionLimitErrorTime;
	
	/** 特例限度アラーム時間 */
	@Column(name = "EXCEPT_LIMIT_ALM_TIME")
	public Integer exceptionLimitAlarmTime;
	
	/** 状態 */
	@Column(name = "STATUS")
	public int status;

	/** マッチング：月別実績の勤怠時間 */
	@OneToOne
	@JoinColumns({
		@JoinColumn(name = "SID", referencedColumnName = "SID", insertable = false, updatable = false),
		@JoinColumn(name = "YM", referencedColumnName = "YM", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_ID", referencedColumnName = "CLOSURE_ID", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_DAY", referencedColumnName = "CLOSURE_DAY", insertable = false, updatable = false),
		@JoinColumn(name = "IS_LAST_DAY", referencedColumnName = "IS_LAST_DAY", insertable = false, updatable = false)
	})
	public KrcdtMonAttendanceTime krcdtMonAttendanceTime;
	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {		
		return this.PK;
	}
	
	/**
	 * ドメインに変換
	 * @return 月別実績の36協定時間
	 */
	public AgreementTimeOfMonthly toDomain(){
		
		return AgreementTimeOfMonthly.of(
				new AttendanceTimeMonth(this.agreementTime),
				new LimitOneMonth(this.limitErrorTime),
				new LimitOneMonth(this.limitAlarmTime),
				(this.exceptionLimitErrorTime == null ?
						Optional.empty() : Optional.of(new LimitOneMonth(this.exceptionLimitErrorTime))),
				(this.exceptionLimitAlarmTime == null ?
						Optional.empty() : Optional.of(new LimitOneMonth(this.exceptionLimitAlarmTime))),
				EnumAdaptor.valueOf(this.status, AgreementTimeStatusOfMonthly.class));
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param key キー値：月別実績の勤怠時間
	 * @param domain 月別実績の36協定時間
	 */
	public void fromDomainForPersist(AttendanceTimeOfMonthlyKey key, AgreementTimeOfMonthly domain){
		
		this.PK = new KrcdtMonAttendanceTimePK(
				key.getEmployeeId(),
				key.getYearMonth().v(),
				key.getClosureId().value,
				key.getClosureDate().getClosureDay().v(),
				(key.getClosureDate().getLastDayOfMonth() ? 1 : 0));
		this.fromDomainForUpdate(domain);
	}
	
	/**
	 * ドメインから変換　(for Update)
	 * @param domain 月別実績の36協定時間
	 */
	public void fromDomainForUpdate(AgreementTimeOfMonthly domain){
		
		this.agreementTime = domain.getAgreementTime().v();
		this.limitErrorTime = domain.getLimitErrorTime().v();
		this.limitAlarmTime = domain.getLimitAlarmTime().v();
		this.exceptionLimitErrorTime = (domain.getExceptionLimitErrorTime().isPresent() ?
				domain.getExceptionLimitErrorTime().get().v() : null); 
		this.exceptionLimitAlarmTime = (domain.getExceptionLimitAlarmTime().isPresent() ?
				domain.getExceptionLimitAlarmTime().get().v() : null);
		this.status = domain.getStatus().value;
	}
}
