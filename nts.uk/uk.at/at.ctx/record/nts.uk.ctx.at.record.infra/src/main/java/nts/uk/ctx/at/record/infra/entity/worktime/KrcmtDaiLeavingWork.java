package nts.uk.ctx.at.record.infra.entity.worktime;

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
 * 日別実績の出退勤
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_DAI_LEAVING_WORK")
public class KrcmtDaiLeavingWork extends UkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtDaiLeavingWorkPK krcmtDaiLeavingWorkPK;
	
	@Column(name = "WORK_TIMES")
	public BigDecimal workTimes;

	@Column(name = "YMD")
	public BigDecimal ymd;

	@Column(name = "WORK_NO")
	public String workNo;
	
	@Override
	protected Object getKey() {
		return this.krcmtDaiLeavingWorkPK;
	}

}
