package nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.worktime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.GoingOutReason;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.shared.dom.common.times.AttendanceTimesMonth;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.goout.AggregateGoOut;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTime;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 集計外出
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_MON_AGGR_GOOUT")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtMonAggrGoout extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonAggrGooutPK PK;
	
	/** 外出回数 */
	@Column(name = "GOOUT_TIMES")
	public int goOutTimes;
	
	/** 法定内時間 */
	@Column(name = "LEGAL_TIME")
	public int legalTime;
	
	/** 計算法定内時間 */
	@Column(name = "CALC_LEGAL_TIME")
	public int calcLegalTime;
	
	/** 法定外時間 */
	@Column(name = "ILLEGAL_TIME")
	public int illegalTime;
	
	/** 計算法定外時間 */
	@Column(name = "CALC_ILLEGAL_TIME")
	public int calcIllegalTime;
	
	/** 合計時間 */
	@Column(name = "TOTAL_TIME")
	public int totalTime;
	
	/** 計算合計時間 */
	@Column(name = "CALC_TOTAL_TIME")
	public int calcTotalTime;

	/** マッチング：月別実績の勤怠時間 */
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "SID", referencedColumnName = "SID", insertable = false, updatable = false),
		@JoinColumn(name = "YM", referencedColumnName = "YM", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_ID", referencedColumnName = "CLOSURE_ID", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_DAY", referencedColumnName = "CLOSURE_DAY", insertable = false, updatable = false),
		@JoinColumn(name = "IS_LAST_DAY", referencedColumnName = "IS_LAST_DAY", insertable = false, updatable = false)
	})
	public KrcdtMonAttendanceTime krcdtMonAttendanceTime;
//	//テーブル結合用
//	public KrcdtMonTime krcdtMonTime;
	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {		
		return this.PK;
	}
	
	/**
	 * ドメインに変換
	 * @return 集計外出
	 */
	public AggregateGoOut toDomain(){
		
		return AggregateGoOut.of(
				EnumAdaptor.valueOf(this.PK.goOutReason, GoingOutReason.class),
				new AttendanceTimesMonth(this.goOutTimes),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.legalTime),
						new AttendanceTimeMonth(this.calcLegalTime)),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.illegalTime),
						new AttendanceTimeMonth(this.calcIllegalTime)),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(this.totalTime),
						new AttendanceTimeMonth(this.calcTotalTime)));
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param key キー値：月別実績の勤怠時間
	 * @param domain 集計外出
	 */
	public void fromDomainForPersist(AttendanceTimeOfMonthlyKey key, AggregateGoOut domain){
		
		this.PK = new KrcdtMonAggrGooutPK(
				key.getEmployeeId(),
				key.getYearMonth().v(),
				key.getClosureId().value,
				key.getClosureDate().getClosureDay().v(),
				(key.getClosureDate().getLastDayOfMonth() ? 1 : 0),
				domain.getGoOutReason().value);
		this.fromDomainForUpdate(domain);
	}
	
	/**
	 * ドメインから変換　(for Update)
	 * @param domain 集計外出
	 */
	public void fromDomainForUpdate(AggregateGoOut domain){
		
		this.goOutTimes = domain.getTimes().v();
		this.legalTime = domain.getLegalTime().getTime().v();
		this.calcLegalTime = domain.getLegalTime().getCalcTime().v();
		this.illegalTime = domain.getIllegalTime().getTime().v();
		this.calcIllegalTime = domain.getIllegalTime().getCalcTime().v();
		this.totalTime = domain.getTotalTime().getTime().v();
		this.calcTotalTime = domain.getTotalTime().getCalcTime().v();
	}
}
