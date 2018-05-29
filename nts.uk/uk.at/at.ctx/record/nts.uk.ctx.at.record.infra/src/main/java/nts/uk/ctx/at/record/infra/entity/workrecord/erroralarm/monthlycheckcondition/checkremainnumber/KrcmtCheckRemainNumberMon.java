package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.monthlycheckcondition.checkremainnumber;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CheckedCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareRange;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareSingleValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.TypeCheckVacation;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.checkremainnumber.CheckConValueRemainingNumber;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.checkremainnumber.CheckOperatorType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.checkremainnumber.CheckRemainNumberMon;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@Table(name = "KRCMT_CHECK_REMAIN_MON")
public class KrcmtCheckRemainNumberMon extends UkJpaEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ERAL_CHECK_ID")
	public String errorAlarmCheckID;

	@Column(name = "TYPE_CHECK_VACATION")
	public int typeCheckVacation;

	@Column(name = "CHECK_OPERATOR_TYPE")
	public int checkOperatorType;
	
	
	@OneToOne(mappedBy = "comparerange")
	public KrcmtCompareRange krcmtCompareRange;

	@OneToOne(mappedBy = "comparesingle")
	public KrcmtCompareSingleVal krcmtCompareSingleVal;

	@Override
	protected Object getKey() {
		return errorAlarmCheckID;
	}

	public KrcmtCheckRemainNumberMon(String errorAlarmCheckID, int typeCheckVacation, int checkOperatorType, KrcmtCompareRange krcmtCompareRange, KrcmtCompareSingleVal krcmtCompareSingleVal) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.typeCheckVacation = typeCheckVacation;
		this.checkOperatorType = checkOperatorType;
		this.krcmtCompareRange = krcmtCompareRange;
		this.krcmtCompareSingleVal = krcmtCompareSingleVal;
	}
	

	public static KrcmtCheckRemainNumberMon toEntity(CheckRemainNumberMon domain) {
		return new KrcmtCheckRemainNumberMon(domain.getErrorAlarmCheckID(), domain.getCheckVacation().value,
				domain.getCheckOperatorType().value,
				domain.getCheckOperatorType() == CheckOperatorType.RANGE_VALUE ? KrcmtCompareRange.toEntity(domain.getErrorAlarmCheckID(), (CompareRange<CheckConValueRemainingNumber>) domain.getCheckCondition()) : null,
				domain.getCheckOperatorType() == CheckOperatorType.SINGLE_VALUE ? KrcmtCompareSingleVal.toEntity(domain.getErrorAlarmCheckID(), (CompareSingleValue<CheckConValueRemainingNumber>) domain.getCheckCondition()) : null
						);

	}

	public CheckRemainNumberMon toDomain() {
		CheckedCondition checkedCondition = new CheckedCondition();
		if (this.checkOperatorType == 1) {
			checkedCondition = this.krcmtCompareRange.toDomain();
		} else {
			checkedCondition = this.krcmtCompareSingleVal.toDomain();
		}
		return new CheckRemainNumberMon(this.errorAlarmCheckID, EnumAdaptor.valueOf(this.typeCheckVacation, TypeCheckVacation.class), checkedCondition,
			   EnumAdaptor.valueOf(this.checkOperatorType, CheckOperatorType.class));
	}



	
}
