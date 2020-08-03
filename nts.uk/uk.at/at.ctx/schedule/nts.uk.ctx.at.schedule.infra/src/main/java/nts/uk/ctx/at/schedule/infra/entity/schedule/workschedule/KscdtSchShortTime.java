package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.DeductionTotalTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.ShortWorkTimeOfDaily;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 勤務予定の短時間勤務時間
 * @author HieuLt
 *
 */
@Entity
@NoArgsConstructor
@Table(name="KSCDT_SCH_SHORTTIME")
public class KscdtSchShortTime extends ContractUkJpaEntity{
	
	@EmbeddedId
	public KscdtSchShortTimePK pk;
	
 	/** 会社ID **/								
	@Column(name = "CID")
	public String cid;
	
	/** 回数 **/
	@Column(name = "COUNT")
	public int count;
	
	/** 合計時間**/
	@Column(name = "TOTAL_TIME")
	public int totalTime;
	/** 所定内合計時間 */
	@Column(name = "TOTAL_TIME_WITHIN")
	public int totalTimeWithIn;
	
	/** 所定外合計時間 */									
	@Column(name = "TOTAL_TIME_WITHOUT")
	public int totalTimeWithOut;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID"),
			@PrimaryKeyJoinColumn(name = "YMD", referencedColumnName = "YMD") })
	public KscdtSchTime kscdtSchTime;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}

	public KscdtSchShortTime(KscdtSchShortTimePK pk, String cid, int count, int totalTime, int totalTimeWithIn,
			int totalTimeWithOut) {
		super();
		this.pk = pk;
		this.cid = cid;
		this.count = count;
		this.totalTime = totalTime;
		this.totalTimeWithIn = totalTimeWithIn;
		this.totalTimeWithOut = totalTimeWithOut;
	}
	
	public static KscdtSchShortTime toEntity(ShortWorkTimeOfDaily deductionTotalTime,String sid , GeneralDate ymd){
		return new KscdtSchShortTime(new KscdtSchShortTimePK(sid, ymd, deductionTotalTime.getChildCareAttribute().value),
				AppContexts.user().companyId(), 
				deductionTotalTime.getWorkTimes().v(), 
				deductionTotalTime.getTotalTime().getTotalTime().getTime().v(), 
				deductionTotalTime.getTotalTime().getWithinStatutoryTotalTime().getTime().v(), 
				deductionTotalTime.getTotalTime().getExcessOfStatutoryTotalTime().getTime().v());
	}
}
