package nts.uk.ctx.at.function.infra.entity.dailyperformanceformat;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author anhdt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNMT_DAY_FORM_S")
public class KfnmtAuthorityDailyPerformanceSFormat extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KfnmtAuthorityDailyPerformanceSFormatPK kfnmtAuthorityDailyPerformanceMobileFormatPK;
	
	/**
	 * 日別実績フォーマット名称
	 */
	@Column(name = "DAILY_PERFORMANCE_FORMAT_NAME")
	public String dailyPerformanceFormatName;

	@Override
	protected Object getKey() {
		return this.kfnmtAuthorityDailyPerformanceMobileFormatPK;
	}
}
