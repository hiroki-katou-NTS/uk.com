package nts.uk.ctx.at.record.infra.entity.workinformation;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author nampt
 * 日別実績の勤務情報
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_DAI_PER_WORK_INFO")
public class KrcmtDaiPerWorkInfo extends UkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtDaiPerWorkInfoPK krcmtDaiPerWorkInfoPK;
	
	@Column(name = "RECORD_WORK_WORKTYPE_CODE")
	public String recordWorkWorktypeCode;

	@Column(name = "RECORD_WORK_WORKTIME_CODE") 
	public String recordWorkWorktimeCode;

	@Column(name = "SCHEDULE_WORK_WORKTYPE_CODE")
	public String scheduleWorkWorktypeCode;
	
	@Column(name = "SCHEDULE_WORK_WORKTIME_CODE")
	public String scheduleWorkWorktimeCode;

	@Column(name = "CALCULATION_STATE")
	public BigDecimal calculationState;

	@Column(name = "GO_STRAIGHT_ATR")
	public BigDecimal goStraightAttribute;
	
	@Column(name = "BACK_STRAIGHT_ATR")
	public BigDecimal backStraightAttribute;

	@Column(name = "WORK_NO")
	public String workNo;

	@Column(name = "YMD")
	public BigDecimal ymd;

	@Override
	protected Object getKey() {
		return this.krcmtDaiPerWorkInfoPK;
	}

}
