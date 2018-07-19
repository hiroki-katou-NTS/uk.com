package nts.uk.ctx.at.record.ac.alarm.checkcondition;

import java.util.ArrayList;
import java.util.List;
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
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.TypeCheckWorkRecordMultipleMonth;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.HowDisplayMessage;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.MessageDisplay;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.NameAlarmExtractionCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonAlarmCheckCondRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonCheckCondAvgRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonCheckCondContRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonCheckCondCospRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonCheckCondRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonthAlarmCheckCond;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonthCheckCond;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonthCheckCondAverage;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonthCheckCondContinue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonthCheckCondCosp;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.AttendanceItemId;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedAmountValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimeDuration;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimesValue;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class MulMonAlarmCheckConEventPubSubscriber implements DomainEventSubscriber<MulMonAlarmCondEventPub> {

	@Inject
	private MulMonAlarmCheckCondRepository alarmCheckCondRepository;

	@Inject
	private MulMonCheckCondRepository condRepository;

	@Inject
	private MulMonCheckCondAvgRepository condAvgRepository;

	@Inject
	private MulMonCheckCondContRepository condContRepository;

	@Inject
	private MulMonCheckCondCospRepository condCospRepository;

	@Override
	public Class<MulMonAlarmCondEventPub> subscribedToEventType() {
		return MulMonAlarmCondEventPub.class;
	}

	@Override
	public void handle(MulMonAlarmCondEventPub domainEvent) {
		List<MulMonCheckCondDomainEventPubDto> listMulMonCheckConds = domainEvent.getListMulMonCheckConds();

		if (domainEvent.isCheckAdd()) {
			for (MulMonCheckCondDomainEventPubDto mulMonCheckCondDto : listMulMonCheckConds) {
				addMulMonAlarm(convertToMulMonAlarmDto(mulMonCheckCondDto));

				if (mulMonCheckCondDto.getTypeCheckItem() == 0 
						|| mulMonCheckCondDto.getTypeCheckItem() == 1
						|| mulMonCheckCondDto.getTypeCheckItem() == 2) {
					addMulMonthCheckCond(createMulMonthCheckCond(mulMonCheckCondDto));
				} else if (mulMonCheckCondDto.getTypeCheckItem() == 3 
						|| mulMonCheckCondDto.getTypeCheckItem() == 4
						|| mulMonCheckCondDto.getTypeCheckItem() == 5) {
					addMulMonthCheckCondAvg(createMulMonthCheckCondAvg(mulMonCheckCondDto));
				} else if (mulMonCheckCondDto.getTypeCheckItem() == 6
						||mulMonCheckCondDto.getTypeCheckItem() == 7
						|| mulMonCheckCondDto.getTypeCheckItem() == 8) {
					addMulMonthCheckCondCont(createMulMonthCheckCondCont(mulMonCheckCondDto));
				} else if (mulMonCheckCondDto.getTypeCheckItem() == 9
						||mulMonCheckCondDto.getTypeCheckItem() == 10
						|| mulMonCheckCondDto.getTypeCheckItem() == 11) {
					addMulMonthCheckCondCosp(createMulMonthCheckCondCosp(mulMonCheckCondDto));
				} 

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
				removeMulMonthCheckCond(eralCheckID);
				removeMulMonthCheckCondAvg(eralCheckID);
				removeMulMonthCheckCondCont(eralCheckID);
				removeMulMonthCheckCondCosp(eralCheckID);
			}

			for (MulMonCheckCondDomainEventPubDto mulMonCheckCondDto : listMulMonCheckConds) {
				updateMulMonAlarm(convertToMulMonAlarmDto(mulMonCheckCondDto));
				
				if (mulMonCheckCondDto.getTypeCheckItem() == 0 
						|| mulMonCheckCondDto.getTypeCheckItem() == 1
						|| mulMonCheckCondDto.getTypeCheckItem() == 2) {
					updateMulMonthCheckCond(createMulMonthCheckCond(mulMonCheckCondDto));
					removeMulMonthCheckCondAvg(mulMonCheckCondDto.getErrorAlarmCheckID());
					removeMulMonthCheckCondCont(mulMonCheckCondDto.getErrorAlarmCheckID());
					removeMulMonthCheckCondCosp(mulMonCheckCondDto.getErrorAlarmCheckID());
				} else if (mulMonCheckCondDto.getTypeCheckItem() == 3 
						|| mulMonCheckCondDto.getTypeCheckItem() == 4
						|| mulMonCheckCondDto.getTypeCheckItem() == 5) {
					updateMulMonthCheckCondAvg(createMulMonthCheckCondAvg(mulMonCheckCondDto));
					removeMulMonthCheckCond(mulMonCheckCondDto.getErrorAlarmCheckID());
					removeMulMonthCheckCondCont(mulMonCheckCondDto.getErrorAlarmCheckID());
					removeMulMonthCheckCondCosp(mulMonCheckCondDto.getErrorAlarmCheckID());
				} else if (mulMonCheckCondDto.getTypeCheckItem() == 6
						||mulMonCheckCondDto.getTypeCheckItem() == 7
						|| mulMonCheckCondDto.getTypeCheckItem() == 8) {
					updateMulMonthCheckCondCont(createMulMonthCheckCondCont(mulMonCheckCondDto));
					removeMulMonthCheckCond(mulMonCheckCondDto.getErrorAlarmCheckID());
					removeMulMonthCheckCondAvg(mulMonCheckCondDto.getErrorAlarmCheckID());
					removeMulMonthCheckCondCosp(mulMonCheckCondDto.getErrorAlarmCheckID());
				} else if (mulMonCheckCondDto.getTypeCheckItem() == 9
						||mulMonCheckCondDto.getTypeCheckItem() == 10
						|| mulMonCheckCondDto.getTypeCheckItem() == 11) {
					updateMulMonthCheckCondCosp(createMulMonthCheckCondCosp(mulMonCheckCondDto));
					removeMulMonthCheckCond(mulMonCheckCondDto.getErrorAlarmCheckID());
					removeMulMonthCheckCondAvg(mulMonCheckCondDto.getErrorAlarmCheckID());
					removeMulMonthCheckCondCont(mulMonCheckCondDto.getErrorAlarmCheckID());
				} 
			}
		} else if (domainEvent.isCheckDelete()) {
			List<String> listEralID = domainEvent.getListEralCheckIDOld();
			for (String eralCheckID : listEralID) {
				removeMulMonAlarm(eralCheckID);
				removeMulMonthCheckCond(eralCheckID);
				removeMulMonthCheckCondAvg(eralCheckID);
				removeMulMonthCheckCondCont(eralCheckID);
				removeMulMonthCheckCondCosp(eralCheckID);
			}
		}
	}

	private MulMonthAlarmCheckCond convertToMulMonAlarmDto(MulMonCheckCondDomainEventPubDto mulMonCheckCondDto) {
		return new MulMonthAlarmCheckCond(mulMonCheckCondDto.getErrorAlarmCheckID(),
				new NameAlarmExtractionCondition(mulMonCheckCondDto.getNameAlarmMulMon()),
				EnumAdaptor.valueOf(mulMonCheckCondDto.getTypeCheckItem(), TypeCheckWorkRecordMultipleMonth.class),
				new HowDisplayMessage(mulMonCheckCondDto.isMessageBold(), mulMonCheckCondDto.getMessageColor()),
				new MessageDisplay(mulMonCheckCondDto.getDisplayMessage()));
	}
	
	private MulMonthCheckCond createMulMonthCheckCond(MulMonCheckCondDomainEventPubDto mulMonCheckCondDto) {
		return new MulMonthCheckCond(mulMonCheckCondDto.getErrorAlarmCheckID(), mulMonCheckCondDto.isUseAtr()
				,convertAtdIemConToDomain(mulMonCheckCondDto.getErAlAtdItem(), null, null));
	}
	
	private MulMonthCheckCondAverage createMulMonthCheckCondAvg(MulMonCheckCondDomainEventPubDto mulMonCheckCondDto) {
		return new MulMonthCheckCondAverage(mulMonCheckCondDto.getErrorAlarmCheckID(), mulMonCheckCondDto.isUseAtr(),
				convertAtdIemConToDomain(mulMonCheckCondDto.getErAlAtdItem(), null, null));
	}
	
	private MulMonthCheckCondContinue createMulMonthCheckCondCont(MulMonCheckCondDomainEventPubDto mulMonCheckCondDto) {
		return new MulMonthCheckCondContinue(mulMonCheckCondDto.getErrorAlarmCheckID(), mulMonCheckCondDto.isUseAtr(), 
				mulMonCheckCondDto.getContinuousMonths(),
				convertAtdIemConToDomain(mulMonCheckCondDto.getErAlAtdItem(), null, null));
	}
	
	private MulMonthCheckCondCosp createMulMonthCheckCondCosp(MulMonCheckCondDomainEventPubDto mulMonCheckCondDto) {
		return new MulMonthCheckCondCosp(mulMonCheckCondDto.getErrorAlarmCheckID(), mulMonCheckCondDto.isUseAtr(), 
				mulMonCheckCondDto.getTimes(), mulMonCheckCondDto.getCompareOperator(),
				convertAtdIemConToDomain(mulMonCheckCondDto.getErAlAtdItem(), null, null));
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
		if (atdItemCon.getConditionType() < 2) {
			if (atdItemCon.getCompareOperator() > 5) {
				if (atdItemCon.getConditionAtr() == ConditionAtr.AMOUNT_VALUE.value) {
					atdItemConDomain.setCompareRange(atdItemCon.getCompareOperator(),
							(V) new CheckedAmountValue(atdItemCon.getCompareStartValue().intValue()),
							(V) new CheckedAmountValue(atdItemCon.getCompareEndValue().intValue()));
				} else if (atdItemCon.getConditionAtr() == ConditionAtr.TIME_DURATION.value) {
					atdItemConDomain.setCompareRange(atdItemCon.getCompareOperator(),
							(V) new CheckedTimeDuration(atdItemCon.getCompareStartValue().intValue()),
							(V) new CheckedTimeDuration(atdItemCon.getCompareEndValue().intValue()));
				} else if (atdItemCon.getConditionAtr() == ConditionAtr.TIME_WITH_DAY.value) {
					atdItemConDomain.setCompareRange(atdItemCon.getCompareOperator(),
							(V) new TimeWithDayAttr(atdItemCon.getCompareStartValue().intValue()),
							(V) new TimeWithDayAttr(atdItemCon.getCompareEndValue().intValue()));
				} else if (atdItemCon.getConditionAtr() == ConditionAtr.TIMES.value) {
					atdItemConDomain.setCompareRange(atdItemCon.getCompareOperator(),
							(V) new CheckedTimesValue(atdItemCon.getCompareStartValue().intValue()),
							(V) new CheckedTimesValue(atdItemCon.getCompareEndValue().intValue()));
				}
			} else {
				if (atdItemCon.getConditionType() == ConditionType.FIXED_VALUE.value) {
					if (atdItemCon.getConditionAtr() == ConditionAtr.AMOUNT_VALUE.value) {
						atdItemConDomain.setCompareSingleValue(atdItemCon.getCompareOperator(),
								atdItemCon.getConditionType(),
								(V) new CheckedAmountValue(atdItemCon.getCompareStartValue().intValue()));
					} else if (atdItemCon.getConditionAtr() == ConditionAtr.TIME_DURATION.value) {
						atdItemConDomain.setCompareSingleValue(atdItemCon.getCompareOperator(),
								atdItemCon.getConditionType(),
								(V) new CheckedTimeDuration(atdItemCon.getCompareStartValue().intValue()));
					} else if (atdItemCon.getConditionAtr() == ConditionAtr.TIME_WITH_DAY.value) {
						atdItemConDomain.setCompareSingleValue(atdItemCon.getCompareOperator(),
								atdItemCon.getConditionType(),
								(V) new TimeWithDayAttr(atdItemCon.getCompareStartValue().intValue()));
					} else if (atdItemCon.getConditionAtr() == ConditionAtr.TIMES.value) {
						atdItemConDomain.setCompareSingleValue(atdItemCon.getCompareOperator(),
								atdItemCon.getConditionType(),
								(V) new CheckedTimesValue(atdItemCon.getCompareStartValue().intValue()));
					}
				} else {
					atdItemConDomain.setCompareSingleValue(atdItemCon.getCompareOperator(),
							atdItemCon.getConditionType(), (V) new AttendanceItemId(atdItemCon.getSingleAtdItem()));
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
		if (alarmCheckCondRepository.getMulMonAlarmByID(mulMonthAlarmCheckCond.getErrorAlarmCheckID()).isPresent())
			throw new BusinessException("Msg_3");
		alarmCheckCondRepository.addMulMonAlarm(mulMonthAlarmCheckCond);
	}

	private void addMulMonthCheckCond(MulMonthCheckCond mulMonthCheckCond) {
		if (condRepository.getMulMonthCheckCondById(mulMonthCheckCond.getErrorAlarmCheckID()).isPresent())
			throw new BusinessException("Msg_3");
		condRepository.addMulMonthCheckCond(mulMonthCheckCond);
	}

	private void addMulMonthCheckCondAvg(MulMonthCheckCondAverage mulMonthCheckCondAverage) {
		if (condAvgRepository.getMulMonthCheckCondAvgById(mulMonthCheckCondAverage.getErrorAlarmCheckID()).isPresent())
			throw new BusinessException("Msg_3");
		condAvgRepository.addMulMonthCheckCondAvg(mulMonthCheckCondAverage);
	}

	private void addMulMonthCheckCondCont(MulMonthCheckCondContinue mulMonthCheckCondContinue) {
		if (condContRepository.getMulMonthCheckCondContById(mulMonthCheckCondContinue.getErrorAlarmCheckID())
				.isPresent())
			throw new BusinessException("Msg_3");
		condContRepository.addMulMonthCheckCondCont(mulMonthCheckCondContinue);
	}

	private void addMulMonthCheckCondCosp(MulMonthCheckCondCosp mulMonthCheckCondCosp) {
		if (condCospRepository.getMulMonthCheckCondCospById(mulMonthCheckCondCosp.getErrorAlarmCheckID()).isPresent())
			throw new BusinessException("Msg_3");
		condCospRepository.addMulMonthCheckCondCosp(mulMonthCheckCondCosp);
	}

	/**
	 * Update
	 */
	private void updateMulMonAlarm(MulMonthAlarmCheckCond mulMonthAlarmCheckCond) {
		if (!alarmCheckCondRepository.getMulMonAlarmByID(mulMonthAlarmCheckCond.getErrorAlarmCheckID()).isPresent()) {
			addMulMonAlarm(mulMonthAlarmCheckCond);
		} else {
			alarmCheckCondRepository.updateMulMonAlarm(mulMonthAlarmCheckCond);
		}
	}

	private void updateMulMonthCheckCond(MulMonthCheckCond mulMonthCheckCond) {
		if (!condRepository.getMulMonthCheckCondById(mulMonthCheckCond.getErrorAlarmCheckID()).isPresent()) {
			addMulMonthCheckCond(mulMonthCheckCond);
		} else {
			condRepository.updateMulMonthCheckCond(mulMonthCheckCond);
		}
	}

	private void updateMulMonthCheckCondAvg(MulMonthCheckCondAverage mulMonthCheckCondAverage) {
		if (!condAvgRepository.getMulMonthCheckCondAvgById(mulMonthCheckCondAverage.getErrorAlarmCheckID())
				.isPresent()) {
			addMulMonthCheckCondAvg(mulMonthCheckCondAverage);
		} else {
			condAvgRepository.updateMulMonthCheckCondAvg(mulMonthCheckCondAverage);
		}
	}

	private void updateMulMonthCheckCondCont(MulMonthCheckCondContinue mulMonthCheckCondContinue) {
		if (!condContRepository.getMulMonthCheckCondContById(mulMonthCheckCondContinue.getErrorAlarmCheckID())
				.isPresent()) {
			addMulMonthCheckCondCont(mulMonthCheckCondContinue);
		} else {
			condContRepository.updateMulMonthCheckCondCont(mulMonthCheckCondContinue);
		}
	}

	private void updateMulMonthCheckCondCosp(MulMonthCheckCondCosp mulMonthCheckCondCosp) {
		if (!condCospRepository.getMulMonthCheckCondCospById(mulMonthCheckCondCosp.getErrorAlarmCheckID())
				.isPresent()) {
			addMulMonthCheckCondCosp(mulMonthCheckCondCosp);
		} else {
			condCospRepository.updateMulMonthCheckCondCosp(mulMonthCheckCondCosp);
		}
	}

	/**
	 * Remove
	 */
	private void removeMulMonAlarm(String eralCheckID) {
		if (alarmCheckCondRepository.getMulMonAlarmByID(eralCheckID).isPresent())
			alarmCheckCondRepository.deleteMulMonAlarm(eralCheckID);
	}

	private void removeMulMonthCheckCond(String eralCheckID) {
		if (condRepository.getMulMonthCheckCondById(eralCheckID).isPresent())
			condRepository.deleteMulMonthCheckCond(eralCheckID);
	}

	private void removeMulMonthCheckCondAvg(String eralCheckID) {
		if (condAvgRepository.getMulMonthCheckCondAvgById(eralCheckID).isPresent())
			condAvgRepository.deleteMulMonthCheckCondAvg(eralCheckID);
	}

	private void removeMulMonthCheckCondCont(String eralCheckID) {
		if (condContRepository.getMulMonthCheckCondContById(eralCheckID).isPresent())
			condContRepository.deleteMulMonthCheckCondCont(eralCheckID);
	}

	private void removeMulMonthCheckCondCosp(String eralCheckID) {
		if (condCospRepository.getMulMonthCheckCondCospById(eralCheckID).isPresent())
			condCospRepository.deleteMulMonthCheckCondCosp(eralCheckID);
	}

}
