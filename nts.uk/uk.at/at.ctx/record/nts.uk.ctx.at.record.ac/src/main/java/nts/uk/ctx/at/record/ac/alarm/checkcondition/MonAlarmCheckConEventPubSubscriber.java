package nts.uk.ctx.at.record.ac.alarm.checkcondition;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.event.DomainEventSubscriber;
import nts.uk.ctx.at.function.pub.alarm.checkcondition.MonAlarmCheckConEventPub;
import nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto.ErAlAtdItemConAdapterPubDto;
import nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto.ExtraResultMonthlyDomainEventPubDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.AttendanceItemCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.ErAlAttendanceItemCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.ErAlConditionsAttendanceItem;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionAtr;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.ExtraResultMonthly;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.ExtraResultMonthlyRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.HowDisplayMessage;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.MessageDisplay;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.NameAlarmExtractionCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.TypeMonCheckItem;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.AttendanceItemId;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedAmountValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimeDuration;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimesValue;
import nts.uk.shr.com.time.TimeWithDayAttr;
@Stateless
public class MonAlarmCheckConEventPubSubscriber implements DomainEventSubscriber<MonAlarmCheckConEventPub>{

	
	@Inject
	private ExtraResultMonthlyRepository extraResultMonthlyRepo;
	
	@Override
	public Class<MonAlarmCheckConEventPub> subscribedToEventType() {
		return MonAlarmCheckConEventPub.class;
	}

	@Override
	public void handle(MonAlarmCheckConEventPub domainEvent) {
		String errorAlarmCheckID = domainEvent.getMonAlarmCheckConID();
		List<ExtraResultMonthlyDomainEventPubDto> listCheckConMonthly = domainEvent.getListExtraResultMonthly();
		
		if(domainEvent.isCheckAdd()) {
			for(ExtraResultMonthlyDomainEventPubDto extraResultMonthly : listCheckConMonthly) {
				extraResultMonthlyRepo.addExtraResultMonthly(convertToExtraResultMonDto(extraResultMonthly));
			}
		}else if(domainEvent.isCheckUpdate()){
			
		}else if(domainEvent.isCheckDelete()) {
			
		}
	}
	
	private ExtraResultMonthly convertToExtraResultMonDto(ExtraResultMonthlyDomainEventPubDto toDto) {
		/* EA is worng, don't need these fields (companyId and errorAlarmCode */
		String companyId = "";
		String errorAlarmCode = "";
		AttendanceItemCondition attendanceItemCon = null;
		if(toDto.getCheckConMonthly()!=null) {
			List<ErAlAttendanceItemCondition<?>> conditionsGroup1 = toDto.getCheckConMonthly().getGroup1().getLstErAlAtdItemCon().stream()
					.filter(item -> item.getCompareStartValue() != null)
					.map(atdItemCon -> convertAtdIemConToDomain(atdItemCon, companyId, errorAlarmCode)).collect(Collectors.toList());
			List<ErAlAttendanceItemCondition<?>> conditionsGroup2 = toDto.getCheckConMonthly().getGroup2().getLstErAlAtdItemCon().stream()
					.filter(item -> item.getCompareStartValue() != null)
					.map(atdItemCon -> convertAtdIemConToDomain(atdItemCon, companyId, errorAlarmCode)).collect(Collectors.toList());
			attendanceItemCon = AttendanceItemCondition.init(toDto.getCheckConMonthly().getOperatorBetweenGroups(), toDto.getCheckConMonthly().isGroup2UseAtr());
			attendanceItemCon.setGroup1(setErAlConditionsAttendanceItem(toDto.getCheckConMonthly().getGroup1().getConditionOperator(),conditionsGroup1));
			attendanceItemCon.setGroup2(setErAlConditionsAttendanceItem(toDto.getCheckConMonthly().getGroup2().getConditionOperator(),conditionsGroup2));
		}
		return new ExtraResultMonthly(
				toDto.getErrorAlarmCheckID(),
				toDto.getSortBy(),
				new NameAlarmExtractionCondition(toDto.getNameAlarmExtraCon()),
				toDto.isUseAtr(),
				EnumAdaptor.valueOf(toDto.getTypeCheckItem(), TypeMonCheckItem.class),
				new HowDisplayMessage(toDto.isMessageBold(), toDto.getMessageColor()),
				new MessageDisplay(toDto.getDisplayMessage()),
				attendanceItemCon
				);
	}
	
	private ErAlConditionsAttendanceItem setErAlConditionsAttendanceItem(int conditionOperator,
			List<ErAlAttendanceItemCondition<?>> conditions) {
		ErAlConditionsAttendanceItem group = ErAlConditionsAttendanceItem.init(conditionOperator);
		group.addAtdItemConditions(conditions);
		
		return group;
		
	}
	
	@SuppressWarnings("unchecked")
	private <V> ErAlAttendanceItemCondition<V> convertAtdIemConToDomain(
			ErAlAtdItemConAdapterPubDto atdItemCon, String companyId, String errorAlarmCode) {

		ErAlAttendanceItemCondition<V> atdItemConDomain = new ErAlAttendanceItemCondition<V>(companyId, errorAlarmCode,
				atdItemCon.getTargetNO(), atdItemCon.getConditionAtr(), atdItemCon.isUseAtr());
		// Set Target
		if (atdItemCon.getConditionAtr() == ConditionAtr.TIME_WITH_DAY.value) {
			atdItemConDomain.setUncountableTarget(atdItemCon.getUncountableAtdItem());
		} else {
			atdItemConDomain.setCountableTarget(atdItemCon.getCountableAddAtdItems(),
					atdItemCon.getCountableSubAtdItems());
		}
		// Set Compare
		if (atdItemCon.getCompareOperator() > 5) {
			if (atdItemCon.getConditionAtr() == ConditionAtr.AMOUNT_VALUE.value) {
				atdItemConDomain.setCompareRange(atdItemCon.getCompareOperator(),
						(V) new CheckedAmountValue(atdItemCon.getCompareStartValue().intValue()),
						(V)new CheckedAmountValue(atdItemCon.getCompareEndValue().intValue()));
			} else if (atdItemCon.getConditionAtr() == ConditionAtr.TIME_DURATION.value) {
				atdItemConDomain.setCompareRange(atdItemCon.getCompareOperator(),
						(V)new CheckedTimeDuration(atdItemCon.getCompareStartValue().intValue()),
						(V)new CheckedTimeDuration(atdItemCon.getCompareEndValue().intValue()));
			} else if (atdItemCon.getConditionAtr() == ConditionAtr.TIME_WITH_DAY.value) {
				atdItemConDomain.setCompareRange(atdItemCon.getCompareOperator(),
						(V)new TimeWithDayAttr(atdItemCon.getCompareStartValue().intValue()),
						(V)new TimeWithDayAttr(atdItemCon.getCompareEndValue().intValue()));
			} else if (atdItemCon.getConditionAtr() == ConditionAtr.TIMES.value) {
				atdItemConDomain.setCompareRange(atdItemCon.getCompareOperator(),
						(V)new CheckedTimesValue(atdItemCon.getCompareStartValue().intValue()),
						(V)new CheckedTimesValue(atdItemCon.getCompareEndValue().intValue()));
			}
		} else {
			if (atdItemCon.getConditionType() == ConditionType.FIXED_VALUE.value) {
				if (atdItemCon.getConditionAtr() == ConditionAtr.AMOUNT_VALUE.value) {
					atdItemConDomain.setCompareSingleValue(atdItemCon.getCompareOperator(),
							atdItemCon.getConditionType(),
							(V)new CheckedAmountValue(atdItemCon.getCompareStartValue().intValue()));
				} else if (atdItemCon.getConditionAtr() == ConditionAtr.TIME_DURATION.value) {
					atdItemConDomain.setCompareSingleValue(atdItemCon.getCompareOperator(),
							atdItemCon.getConditionType(),
							(V)new CheckedTimeDuration(atdItemCon.getCompareStartValue().intValue()));
				} else if (atdItemCon.getConditionAtr() == ConditionAtr.TIME_WITH_DAY.value) {
					atdItemConDomain.setCompareSingleValue(atdItemCon.getCompareOperator(),
							atdItemCon.getConditionType(),
							(V)new TimeWithDayAttr(atdItemCon.getCompareStartValue().intValue()));
				} else if (atdItemCon.getConditionAtr() == ConditionAtr.TIMES.value) {
					atdItemConDomain.setCompareSingleValue(atdItemCon.getCompareOperator(),
							atdItemCon.getConditionType(),
							(V)new CheckedTimesValue(atdItemCon.getCompareStartValue().intValue()));
				}
			} else {
				atdItemConDomain.setCompareSingleValue(atdItemCon.getCompareOperator(), atdItemCon.getConditionType(),
						(V) new AttendanceItemId(atdItemCon.getSingleAtdItem()));
			}
		}
		return atdItemConDomain;
	}
	
	
	
	private void abc () {
//		List<ErAlAttendanceItemCondition<?>> conditionsGroup1 = this.attendanceItemCondition.getGroup1().getLstErAlAtdItemCon().stream()
//				.filter(item -> item.getCompareStartValue() != null)
//				.map(atdItemCon -> convertAtdIemConToDomain(atdItemCon, companyId, errorAlarmCode)).collect(Collectors.toList());
//		List<ErAlAttendanceItemCondition<?>> conditionsGroup2 = this.attendanceItemCondition.getGroup2().getLstErAlAtdItemCon().stream()
//				.filter(item -> item.getCompareStartValue() != null)
//				.map(atdItemCon -> convertAtdIemConToDomain(atdItemCon, companyId, errorAlarmCode)).collect(Collectors.toList());
//		
//		condition.createAttendanceItemCondition(this.attendanceItemCondition.getOperatorBetweenGroups(),
//					this.attendanceItemCondition.isGroup2UseAtr())
//				.setAttendanceItemConditionGroup1(this.attendanceItemCondition.getGroup1().getConditionOperator(), conditionsGroup1)
//				.setAttendanceItemConditionGroup2(this.attendanceItemCondition.getGroup2().getConditionOperator(), conditionsGroup2);
	}

}
