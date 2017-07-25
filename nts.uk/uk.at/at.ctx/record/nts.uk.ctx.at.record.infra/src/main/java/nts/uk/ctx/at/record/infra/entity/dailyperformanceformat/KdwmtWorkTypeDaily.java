package nts.uk.ctx.at.record.infra.entity.dailyperformanceformat;

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
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KDWMT_WORK_TYPE_DAILY")
public class KdwmtWorkTypeDaily extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KdwmtWorkTypeDailyPK kdwmtWorkTypeDailyPK;

	@Column(name = "SHEET_NAME")
	public String sheetName;

	@Column(name = "SHEET_NO")
	public BigDecimal sheetNo;

	@Column(name = "ORDER")
	public BigDecimal order;

	@Column(name = "COLUMN_WIDTH")
	public BigDecimal columnWidth;

	@Override
	protected Object getKey() {
		return this.kdwmtWorkTypeDailyPK;
	}
}
