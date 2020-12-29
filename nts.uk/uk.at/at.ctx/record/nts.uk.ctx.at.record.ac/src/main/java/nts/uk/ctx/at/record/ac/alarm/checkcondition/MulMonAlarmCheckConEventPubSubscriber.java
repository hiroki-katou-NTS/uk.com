package nts.uk.ctx.at.record.ac.alarm.checkcondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.event.DomainEventSubscriber;
import nts.uk.ctx.at.function.pub.alarm.checkcondition.MulMonAlarmCondEventPub;
import nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto.ErAlAtdItemConAdapterPubDto;
import nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto.MulMonCheckCondDomainEventPubDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.ErAlAttendanceItemCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionAtr;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.SingleValueCompareType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.TypeCheckWorkRecordMultipleMonth;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.MessageDisplay;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.NameAlarmExtractionCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonAlarmCheckCondRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonthAlarmCheckCond;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.AttendanceItemId;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedAmountValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimeDuration;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimesValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimesValueDay;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class MulMonAlarmCheckConEventPubSubscriber<V> implements DomainEventSubscriber<MulMonAlarmCondEventPub> {

	@Inject
	private MulMonAlarmCheckCondRepository alarmCheckCondRepository;

	@Override
	public Class<MulMonAlarmCondEventPub> subscribedToEventType() {
		return MulMonAlarmCondEventPub.class;
	}

	@Override
	public void handle(MulMonAlarmCondEventPub domainEvent) {
		List<MulMonCheckCondDomainEventPubDto> listMulMonCheckConds = domainEvent.getListMulMonCheckConds();

		if (domainEvent.isCheckAdd()) {
			for (MulMonCheckCondDomainEventPubDto mulMonCheckCondDto : listMulMonCheckConds) {
				
				if (alarmCheckCondRepository.getMulMonAlarmByID(mulMonCheckCondDto.getErrorAlarmCheckID()).isPresent())
					   throw new BusinessException("Msg_3");
				
				addMulMonAlarm(convertToMulMonAlarmDto(mulMonCheckCondDto));			

			}
		} else if (domainEvent.isCheckUpdate()) {
			List<String> listEralIDOld = domainEvent.getListEralCheckIDOld();
			List<String> listEralIDNew = listMulMonCheckConds.stream().map(c -> c.getErrorAlarmCheckID())
					.collect(Collectors.toList());
			List<String> listEralIdRemove = new ArrayList<>();
			for (String eralIdOld : listEralIDOld) {
				boolean checkExist = false;
				for (String eralIdNew : listEralIDNew) {
					if (eralIdOld.equals(eralIdNew)) {
						checkExist = true;
						break;
					}
				}
				if (!checkExist) {
					listEralIdRemove.add(eralIdOld);
				}
			}

			// remove
			for (String eralCheckID : listEralIdRemove) {
				removeMulMonAlarm(eralCheckID);
				
			}

			for (MulMonCheckCondDomainEventPubDto mulMonCheckCondDto : listMulMonCheckConds) {
				updateMulMonAlarm(convertToMulMonAlarmDto(mulMonCheckCondDto));
				
			}
		} else if (domainEvent.isCheckDelete()) {
			List<String> listEralID = domainEvent.getListEralCheckIDOld();
			for (String eralCheckID : listEralID) {
				removeMulMonAlarm(eralCheckID);				
			}
		}
	}

	private MulMonthAlarmCheckCond convertToMulMonAlarmDto(MulMonCheckCondDomainEventPubDto dto) {
		ErAlAttendanceItemCondition<V> atdItemConDomain = convertAtdIemConToDomain(dto.getErAlAtdItem(), dto.getCid(), null);
		
		MulMonthAlarmCheckCond domain = new MulMonthAlarmCheckCond(dto.getCid(),
				dto.getErrorAlarmCheckID(),
				dto.getCondNo(),
				new NameAlarmExtractionCondition(dto.getNameAlarmMulMon()),
				EnumAdaptor.valueOf(dto.getTypeCheckItem(), TypeCheckWorkRecordMultipleMonth.class),
				dto.isUseAtr(),
				dto.getDisplayMessage().isEmpty() ? Optional.empty() : Optional.ofNullable(new MessageDisplay(dto.getDisplayMessage())),
				atdItemConDomain,
				Optional.ofNullable(dto.getContinuousMonths()),
				Optional.ofNullable(dto.getTimes()),
				Optional.ofNullable(EnumAdaptor.valueOf(dto.getCompareOperator(),SingleValueCompareType.class)));
		
		
		return domain;
	}
	
	@SuppressWarnings("unchecked")
	private <V> ErAlAttendanceItemCondition<V> convertAtdIemConToDomain(ErAlAtdItemConAdapterPubDto atdItemCon,
			String companyId, String errorAlarmCode) {

		ErAlAttendanceItemCondition<V> atdItemConDomain = new ErAlAttendanceItemCondition<V>(companyId, errorAlarmCode,
				atdItemCon.getTargetNO(), atdItemCon.getConditionAtr(), atdItemCon.isUseAtr(),
				atdItemCon.getConditionType());
		// Set Target
		atdItemConDomain.setCountableTarget(atdItemCon.getCountableAddAtdItems(), atdItemCon.getCountableSubAtdItems());
		
		// Set Compare
		int conditionType = atdItemCon.getConditionType();
		int compareOperator = atdItemCon.getCompareOperator();
		int conditionAtr = atdItemCon.getConditionAtr();
		if (conditionType == ConditionType.FIXED_VALUE.value 
				|| conditionType == ConditionType.ATTENDANCE_ITEM.value ) {
				if (compareOperator > SingleValueCompareType.GREATER_THAN.value) {

					if (conditionAtr == ConditionAtr.AMOUNT_VALUE.value) {
						atdItemConDomain.setCompareRange(compareOperator,
								atdItemCon.getCompareStartValue() != null ? (V) new CheckedAmountValue(atdItemCon.getCompareStartValue().intValue()) : (V) new CheckedAmountValue(0),
								atdItemCon.getCompareEndValue() != null ? (V) new CheckedAmountValue(atdItemCon.getCompareEndValue().intValue()) : (V) new CheckedAmountValue(0));
					} else if (conditionAtr == ConditionAtr.TIME_DURATION.value) {
						atdItemConDomain.setCompareRange(compareOperator,
								atdItemCon.getCompareStartValue() != null ? (V) new CheckedTimeDuration(atdItemCon.getCompareStartValue().intValue()) : (V) new CheckedTimeDuration(0),
								atdItemCon.getCompareEndValue() != null ? (V) new CheckedTimeDuration(atdItemCon.getCompareEndValue().intValue()) : (V) new CheckedTimeDuration(0));
					} else if (conditionAtr == ConditionAtr.TIME_WITH_DAY.value) {
						atdItemConDomain.setCompareRange(compareOperator,
						atdItemCon.getCompareStartValue() != null ? (V) new TimeWithDayAttr(atdItemCon.getCompareStartValue().intValue()) : (V) new TimeWithDayAttr(0),
						atdItemCon.getCompareEndValue() != null ? (V) new TimeWithDayAttr(atdItemCon.getCompareEndValue().intValue()) : (V) new TimeWithDayAttr(0));
					} else if (conditionAtr == ConditionAtr.TIMES.value) {
						atdItemConDomain.setCompareRange(compareOperator,
								atdItemCon.getCompareStartValue() != null ? (V) new CheckedTimesValue(atdItemCon.getCompareStartValue().intValue()) : (V) new CheckedTimesValue(0),
								atdItemCon.getCompareEndValue() != null ? (V) new CheckedTimesValue(atdItemCon.getCompareEndValue().intValue()) : (V) new CheckedTimesValue(0));
					}else  if(conditionAtr == ConditionAtr.DAYS.value){
						atdItemConDomain.setCompareRange(compareOperator,
								atdItemCon.getCompareStartValue() != null ? (V) new CheckedTimesValueDay(atdItemCon.getCompareStartValue().doubleValue()) : (V) new CheckedTimesValueDay((double) 0),
								atdItemCon.getCompareEndValue() != null ? (V) new CheckedTimesValueDay(atdItemCon.getCompareEndValue().doubleValue()) : (V) new CheckedTimesValueDay((double) 0));
					}
				} else {
					if (conditionType == ConditionType.FIXED_VALUE.value) {
						if (conditionAtr == ConditionAtr.AMOUNT_VALUE.value) {
							atdItemConDomain.setCompareSingleValue(compareOperator, conditionType,
									atdItemCon.getCompareStartValue() != null ? (V) new CheckedAmountValue(atdItemCon.getCompareStartValue().intValue()) : (V) new CheckedAmountValue(0));
						} else if (conditionAtr == ConditionAtr.TIME_DURATION.value) {
							atdItemConDomain.setCompareSingleValue(compareOperator, conditionType,
							atdItemCon.getCompareStartValue() != null ? (V) new CheckedTimeDuration(atdItemCon.getCompareStartValue().intValue()) : (V) new CheckedTimeDuration(0));
						} else if (conditionAtr == ConditionAtr.TIME_WITH_DAY.value) {
							atdItemConDomain.setCompareSingleValue(compareOperator, conditionType,
									atdItemCon.getCompareStartValue() != null ? (V) new TimeWithDayAttr(atdItemCon.getCompareStartValue().intValue()) : (V) new TimeWithDayAttr(0));
						} else if (conditionAtr == ConditionAtr.TIMES.value) {
							atdItemConDomain.setCompareSingleValue(compareOperator, conditionType,
									atdItemCon.getCompareStartValue() != null ? (V) new CheckedTimesValue(atdItemCon.getCompareStartValue().intValue()) : (V) new CheckedTimesValue(0));
						}else  if(conditionAtr == ConditionAtr.DAYS.value){
							atdItemConDomain.setCompareSingleValue(compareOperator, conditionType,
									atdItemCon.getCompareStartValue() != null ? (V) new CheckedTimesValueDay(atdItemCon.getCompareStartValue().doubleValue()) : (V) new CheckedTimesValueDay((double) 0));
						}
					} else {
						atdItemConDomain.setCompareSingleValue(compareOperator, conditionType,
								(V) new AttendanceItemId(atdItemCon.getSingleAtdItem()));
					}
				}
		} else {
			atdItemConDomain.setInputCheck(atdItemCon.getInputCheckCondition().intValue());
		}
		return atdItemConDomain;
	}

	/**
	 * Add
	 */
	private void addMulMonAlarm(MulMonthAlarmCheckCond mulMonthAlarmCheckCond) {
		if (alarmCheckCondRepository.getMulMonAlarmByID(mulMonthAlarmCheckCond.getEralCheckId()).isPresent())
			throw new BusinessException("Msg_3");
		alarmCheckCondRepository.addMulMonAlarm(mulMonthAlarmCheckCond);
	}

	/**
	 * Update
	 */
	private void updateMulMonAlarm(MulMonthAlarmCheckCond mulMonthAlarmCheckCond) {
		if (!alarmCheckCondRepository.getMulMonAlarmByID(mulMonthAlarmCheckCond.getEralCheckId()).isPresent()) {
			addMulMonAlarm(mulMonthAlarmCheckCond);
		} else {
			alarmCheckCondRepository.updateMulMonAlarm(mulMonthAlarmCheckCond);
		}
	}

	/**
	 * Remove
	 */
	private void removeMulMonAlarm(String eralCheckID) {
		if (alarmCheckCondRepository.getMulMonAlarmByID(eralCheckID).isPresent())
			alarmCheckCondRepository.deleteMulMonAlarm(eralCheckID);
	}
	
}
