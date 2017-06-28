package nts.uk.ctx.at.record.infra.entity.standardtime;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KMKMT_BASIC_AGREEMENT_SET")
public class KmkmtBasicAgreementSetting extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KmkmtBasicAgreementSettingPK kmkmtBasicAgreementSettingPK;

	@Column(name = "ALARM_WEEK")
	public BigDecimal alarmWeek;

	@Column(name = "ERROR_WEEK")
	public BigDecimal errorWeek;

	@Column(name = "LIMIT_WEEK")
	public BigDecimal limitWeek;

	@Column(name = "ALARM_TWO_WEEKS")
	public BigDecimal alarmTwoWeeks;

	@Column(name = "ERROR_TWO_WEEKS")
	public BigDecimal errorTwoWeeks;

	@Column(name = "LIMIT_TWO_WEEKS")
	public BigDecimal limitTwoWeeks;

	@Column(name = "ALARM_FOUR_WEEKS")
	public BigDecimal alarmFourWeeks;

	@Column(name = "ERROR_FOUR_WEEKS")
	public BigDecimal errorFourWeeks;

	@Column(name = "LIMIT_FOUR_WEEKS")
	public BigDecimal limitFourWeeks;

	@Column(name = "ALARM_ONE_MONTH")
	public BigDecimal alarmOneMonth;

	@Column(name = "ERROR_ONE_MONTH")
	public BigDecimal errorOneMonth;

	@Column(name = "LIMIT_ONE_MONTH")
	public BigDecimal limitOneMonth;

	@Column(name = "ALARM_TWO_MONTH")
	public BigDecimal alarmTwoMonths;

	@Column(name = "ERROR_TWO_MONTH")
	public BigDecimal errorTwoMonths;

	@Column(name = "LIMIT_TWO_MONTH")
	public BigDecimal limitTwoMonths;

	@Column(name = "ALARM_THREE_MONTH")
	public BigDecimal alarmThreeMonths;

	@Column(name = "ERROR_THREE_MONTH")
	public BigDecimal errorThreeMonths;

	@Column(name = "LIMIT_THREE_MONTH")
	public BigDecimal limitThreeMonths;

	@Column(name = "ALARM_YEARLY")
	public BigDecimal alarmOneYear;

	@Column(name = "ERROR_YEARLY")
	public BigDecimal errorOneYear;

	@Column(name = "LIMIT_YEARLY")
	public BigDecimal limitOneYear;

	@Override
	protected Object getKey() {
		return this.kmkmtBasicAgreementSettingPK;
	}
}
