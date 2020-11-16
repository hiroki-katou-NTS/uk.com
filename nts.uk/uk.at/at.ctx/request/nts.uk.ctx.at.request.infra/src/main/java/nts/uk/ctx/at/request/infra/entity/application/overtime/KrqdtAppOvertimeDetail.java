package nts.uk.ctx.at.request.infra.entity.application.overtime;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * Refactor5
 * @author hoangnd
 *
 */
@AllArgsConstructor
@NoArgsConstructor
public class KrqdtAppOvertimeDetail extends ContractUkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrqdtAppOvertimeDetailPk appOvertimeDetailPk;
	
	@Column(name = "APPLICATION_TIME")
	public Integer applicationTime;
	
	@Column(name = "YEAR_MONTH")
	public Integer yearMonth;
	
	@Column(name = "ACTUAL_TIME")
	public Integer actualTime;
	
	@Column(name = "LIMIT_ERROR_TIME")
	public Integer limitErrorTime;
	
	@Column(name = "LIMIT_ALARM_TIME")
	public Integer limitAlarmTime;
	
	@Column(name = "EXCEPTION_LIMIT_ERROR_TIME")
	public Integer extLimitErrorTime;
	
	@Column(name = "EXCEPTION_LIMIT_ALARM_TIME")
	public Integer extLimitAlarmTime;
	
	@Column(name = "NUM_OF_YEAR36_OVER")
	public Integer numOfYear36Over;
	
	@Column(name = "ACTUAL_TIME_YEAR")
	public Integer actualTimeYear;
	
	@Column(name = "LIMIT_TIME_YEAR")
	public Integer limitTimeYear;
	
	@Column(name = "REG_APPLICATION_TIME")
	public Integer regApplicationTime;
	
	@Column(name = "REG_ACTUAL_TIME")
	public Integer regActualTime;
	
	@Column(name = "REG_LIMIT_TIME")
	public Integer regLimitTime;
	
	@Column(name = "REG_LIMIT_TIME_MULTI")
	public Integer regLimitTimeMulti;
	
	@Override
	protected Object getKey() {
		return null;
	}

}
