package nts.uk.ctx.at.request.infra.entity.application.overtime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRQDT_APP_OVERTIME_DET_M")
public class KrqdtTime36UpLimitPerMonth extends ContractUkJpaEntity {
	
	@EmbeddedId
	public KrqdpTime36UpLimitPerMonthPK pk;
	
	/*
	 * 平均時間
	 */
	@Column(name = "AVE_TIME")
	public Integer averageTime;
	
	/*
	 * 合計時間
	 */
	@Column(name = "TOTAL_TIME")
	public Integer totalTime;
	
	@ManyToOne
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name="CID", referencedColumnName="CID"),
    	@PrimaryKeyJoinColumn(name="APP_ID", referencedColumnName="APP_ID")
    })
	public KrqdtAppOvertimeDetail appOvertimeDetail;

	@Override
	protected Object getKey() {
		return pk;
	}

	public KrqdtTime36UpLimitPerMonth(KrqdpTime36UpLimitPerMonthPK pk, Integer averageTime, Integer totalTime) {
		super();
		this.pk = pk;
		this.averageTime = averageTime;
		this.totalTime = totalTime;
	}
	
	
	
}
