package nts.uk.ctx.at.record.infra.entity.monthly.erroralarm;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.erroralarm.EmployeeMonthlyPerError;
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

	public KrcdtEmployeeMonthlyPerError convertToEntity(EmployeeMonthlyPerError domain, Optional<KrcdtEmployeeMonthlyPerErrorPK> entityKey){
		
		KrcdtEmployeeMonthlyPerError entity = new KrcdtEmployeeMonthlyPerError();
		if (!entityKey.isPresent()) {
			val key = new KrcdtEmployeeMonthlyPerErrorPK(domain.getErrorType().value, domain.getYearMonth().v(),
					domain.getEmployeeID(), domain.getClosureId().value, domain.getClosureDate().getClosureDay().v(),
					(domain.getClosureDate().getLastDayOfMonth() ? 1 : 0));
			entity.krcdtEmployeeMonthlyPerErrorPK = key;

		}else{
			entity.krcdtEmployeeMonthlyPerErrorPK = entityKey.get();
		}
		entity.flex = domain.getFlex().isPresent() ? domain.getFlex().get().value : null;
		entity.annualHoliday = domain.getAnnualHoliday().isPresent() ? domain.getAnnualHoliday().get().value : null;
		entity.yearlyReserved = domain.getYearlyReserved().isPresent() ? domain.getYearlyReserved().get().value : null;
		
		return entity;
	}
}
