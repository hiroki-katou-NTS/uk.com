package nts.uk.ctx.at.record.infra.entity.monthly.erroralarm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@NoArgsConstructor
@Entity
@Table(name = "KRCDT_MONTH_PER_ERR")
public class KrcdtEmployeeMonthlyPerError extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrcdtEmployeeMonthlyPerErrorPK krcdtEmployeeMonthlyPerErrorPK;
	/**
	 * フレックス: フレックスエラー
	 */
	@Column(name = "IS_LAST_DAY")
	public Integer flex;
	/**
	 * 年休: 年休エラー
	 */
	@Column(name = "ANNUAL_HOLIDAY")
	public Integer annualHoliday;
	/**
	 * 積立年休: 積立年休エラー
	 */
	@Column(name = "YEARLY_RESERVED")
	public Integer yearlyReserved;

	@Override
	protected Object getKey() {
		return krcdtEmployeeMonthlyPerErrorPK;
	}

}
