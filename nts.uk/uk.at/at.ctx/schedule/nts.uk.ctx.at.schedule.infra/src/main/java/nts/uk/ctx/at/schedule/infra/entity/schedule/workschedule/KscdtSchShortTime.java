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

}
