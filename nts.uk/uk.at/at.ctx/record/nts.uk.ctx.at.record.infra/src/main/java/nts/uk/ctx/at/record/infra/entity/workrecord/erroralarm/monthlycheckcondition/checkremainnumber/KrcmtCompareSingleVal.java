package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.monthlycheckcondition.checkremainnumber;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareSingleValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.checkremainnumber.CheckConValueRemainingNumber;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@Table(name = "KRCMT_COMPARE_SINGLE_VAL")
public class KrcmtCompareSingleVal extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ERAL_CHECK_ID")
	public String errorAlarmCheckID;

	@Column(name = "COMPARE_OPERATOR")
	public int compareOperator;
	
	@Column(name = "DAYS_VALUE")
	public BigDecimal daysValue;
	
	@Column(name = "TIME_VALUE")
	public Integer timeValue;
	
	@OneToOne
	@JoinColumn(name = "ERAL_CHECK_ID", referencedColumnName = "ERAL_CHECK_ID", insertable = false, updatable = false)
	public KrcmtCheckRemainNumberMon comparesingle;
	
	@Override
	protected Object getKey() {
		return errorAlarmCheckID;
	}

	public KrcmtCompareSingleVal(String errorAlarmCheckID, int compareOperator, BigDecimal daysValue, Integer timeValue) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.compareOperator = compareOperator;
		this.daysValue = daysValue;
		this.timeValue = timeValue;
	}
	
	public static KrcmtCompareSingleVal toEntity(String errorAlarmCheckID,CompareSingleValue<CheckConValueRemainingNumber> domain) {
		return new KrcmtCompareSingleVal(
				errorAlarmCheckID,
				domain.getCompareOpertor().value,
				domain.getValue().getDaysValue(),
				domain.getValue().getTimeValue() ==null?null:(!domain.getValue().getTimeValue().isPresent()?null:domain.getValue().getTimeValue().get())
				);
		
	}
	
	public CompareSingleValue<CheckConValueRemainingNumber> toDomain() {
		CheckConValueRemainingNumber data1 = new CheckConValueRemainingNumber(this.daysValue, 
				this.timeValue==null?null:Optional.of(this.timeValue.intValue())
				);
		CompareSingleValue<CheckConValueRemainingNumber> data = new CompareSingleValue<>(this.compareOperator,0);
		data.setValue(data1);
		return data;
	}

}
