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

	@OneToOne(mappedBy = "comparerange")
	public KrcmtCompareRange krcmtCompareRange;

	@OneToOne(mappedBy = "comparesingle")
	public KrcmtCompareSingleVal krcmtCompareSingleVal;

	@Override
	protected Object getKey() {
		return errorAlarmCheckID;
	}

	public KrcmtCheckRemainNumberMon(String errorAlarmCheckID, int typeCheckVacation, KrcmtCompareRange krcmtCompareRange, KrcmtCompareSingleVal krcmtCompareSingleVal) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.typeCheckVacation = typeCheckVacation;
		this.krcmtCompareRange = krcmtCompareRange;
		this.krcmtCompareSingleVal = krcmtCompareSingleVal;
	}

	//
	// public KrcmtCheckRemainNumberMon(String errorAlarmCheckID, int
	// typeCheckVacation, KrcmtCompareSingleVal krcmtCompareSingleVal) {
	// super();
	// this.errorAlarmCheckID = errorAlarmCheckID;
	// this.typeCheckVacation = typeCheckVacation;
	// this.krcmtCompareSingleVal = krcmtCompareSingleVal;
	// }

	public static KrcmtCheckRemainNumberMon toEntity(CheckRemainNumberMon domain) {
		return new KrcmtCheckRemainNumberMon(domain.getErrorAlarmCheckID(), domain.getCheckVacation().value,
				domain.getCheckVacation().value == 1 ? KrcmtCompareRange.toEntity(domain.getErrorAlarmCheckID(), 
						(CompareRange<CheckConValueRemainingNumber>) domain.getCheckCondition()) : null,
				domain.getCheckVacation().value != 1 ? KrcmtCompareSingleVal.toEntity(domain.getErrorAlarmCheckID(), 
						(CompareSingleValue<CheckConValueRemainingNumber>) domain.getCheckCondition()) : null);

	}

	public CheckRemainNumberMon toDomain() {
		CheckedCondition checkedCondition = new CheckedCondition();
		if (this.typeCheckVacation == 1) {
			checkedCondition = this.krcmtCompareRange.toDomain();
		} else {
			checkedCondition = this.krcmtCompareSingleVal.toDomain();
		}
		return new CheckRemainNumberMon(this.errorAlarmCheckID, EnumAdaptor.valueOf(this.typeCheckVacation, TypeCheckVacation.class), checkedCondition);
	}
}
