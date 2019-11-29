package nts.uk.ctx.at.function.infra.entity.dailyperformanceformat;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KfnmtAuthorityDailyPerformanceSFormatPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 勤怠項目ID
	 */
	@Column(name = "CID")
	public String companyId;
	
	/**
	 * 日別実績フォーマットコード
	 */
	@Column(name = "DAILY_PERFORMANCE_FORMAT_CD")
	public String dailyPerformanceFormatCode;

}
