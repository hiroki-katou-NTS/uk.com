package nts.uk.ctx.at.function.infra.entity.dailyperformanceformat;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author nampt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNMT_DAY_FORM_DEFAULT")
public class KfnmtDailyPerformanceDisplay extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KfnmtDailyPerformanceDisplayPK kfnmtDailyPerformanceDisplayPK;
	
	@Override
	protected Object getKey() {
		return this.kfnmtDailyPerformanceDisplayPK;
	}
}
