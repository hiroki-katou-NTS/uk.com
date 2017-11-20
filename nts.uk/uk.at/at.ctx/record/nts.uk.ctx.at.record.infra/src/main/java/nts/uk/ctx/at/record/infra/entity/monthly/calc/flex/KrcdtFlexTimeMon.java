package nts.uk.ctx.at.record.infra.entity.monthly.calc.flex;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTime;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 月別実績のフレックス時間
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_FLEX_TIME_MON")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtFlexTimeMon extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonAttendanceTimePK PK;
	
	/** フレックス時間 */
	@Column(name = "FLEX_TIME")
	public int flexTime; 
	
	/** フレックス計算時間 */
	@Column(name = "FLEX_TIME_CALC")
	public int flexTimeCalc; 
	
	/** 計算事前フレックス時間 */
	@Column(name = "CALC_BFR_FLEX_TIME")
	public int calcBeforeFlexTime; 
	
	/** 計算法定内フレックス時間 */
	@Column(name = "CALC_WITHIN_FLEX_TM")
	public int calcWithinStatutoryFlexTime; 
	
	/** 計算法定外フレックス時間 */
	@Column(name = "CALC_EXCESS_FLEX_TM")
	public int calcExcessOfStatutoryFlexTime; 
	
	/** フレックス超過時間 */
	@Column(name = "FLEX_EXCESS_TIME")
	public int flexExcessTime; 
	
	/** フレックス不足時間 */
	@Column(name = "FLEX_SHORTAGE_TIME")
	public int flexShortageTime; 
	
	/** 事前フレックス時間 */
	@Column(name = "BEFORE_FLEX_TIME")
	public int beforeFlexTime; 
	
	/** フレックス繰越勤務時間 */
	@Column(name = "FLEX_CARRYFWD_WRK_TM")
	public int flexCarryforwardWorkTime; 
	
	/** フレックス繰越時間 */
	@Column(name = "FLEX_CARRYFWD_TIME")
	public int flexCarryforwardTime; 
	
	/** フレックス繰越不足時間 */
	@Column(name = "FLEX_CARRYFWD_SHT_TM")
	public int flexCarryforwardShortageTime; 
	
	/** 超過フレ区分 */
	@Column(name = "EXCESS_FLEX_ATR")
	public int excessFlexAtr; 
	
	/** 原則時間 */
	@Column(name = "PRINCIPLE_TIME")
	public int principleTime; 
	
	/** 便宜上時間 */
	@Column(name = "CONVENIENCE_TIME")
	public int forConvenienceTime; 

	/** マッチング：月別実績の勤怠時間 */
	@OneToOne
	@JoinColumns({
		@JoinColumn(name = "SID", referencedColumnName="KRCDT_MON_ATTENDANCE_TIME.SID", insertable = false, updatable = false),
		@JoinColumn(name = "START_YMD", referencedColumnName="KRCDT_MON_ATTENDANCE_TIME.START_YMD", insertable = false, updatable = false),
		@JoinColumn(name = "END_YMD", referencedColumnName="KRCDT_MON_ATTENDANCE_TIME.END_YMD", insertable = false, updatable = false)
	})
	public KrcdtMonAttendanceTime krcdtMonAttendanceTime;
	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {		
		return this.PK;
	}
}
