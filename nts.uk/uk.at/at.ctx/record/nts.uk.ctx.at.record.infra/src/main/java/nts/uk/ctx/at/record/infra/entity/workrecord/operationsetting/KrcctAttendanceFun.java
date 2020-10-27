package nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "KRCCT_ATTENDANCE_FUN")
public class KrcctAttendanceFun extends ContractUkJpaEntity{
	
	@Id
	@Column(name = "FUNCTION_NO")
	public BigDecimal functionNo;
	
	@Column(name = "DESCRIPTION_OF_FUNCTION")
	public String descriptionOfFunction;

	@Column(name = "DISPLAY_NAME_OF_FUNCTION")
	public String displayNameOfFunction;
	
	@Column(name = "DISPLAY_ORDER")
	public int displayOrder;
	
	@Column(name = "INITAL_VALUE")
	public int initValue;
	
	@Override
	protected Object getKey() {
		return this.functionNo;
	}
	
}
