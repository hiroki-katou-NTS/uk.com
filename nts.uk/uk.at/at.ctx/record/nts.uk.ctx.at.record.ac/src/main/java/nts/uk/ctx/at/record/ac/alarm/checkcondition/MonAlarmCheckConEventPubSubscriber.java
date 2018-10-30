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
import nts.uk.ctx.at.function.pub.alarm.checkcondition.MonAlarmCheckConEventPub;
import nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto.AgreementCheckCon36AdapterPubDto;
import nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto.CheckRemainNumberMonAdapterPubDto;
import nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto.CompareRangeAdapterPubDto;
import nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto.CompareSingleValueAdapterPubDto;
import nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto.ErAlAtdItemConAdapterPubDto;
import nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto.ExtraResultMonthlyDomainEventPubDto;
import nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto.SpecHolidayCheckConAdapterPubDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.AttendanceItemCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CheckedCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareRange;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareSingleValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.ErAlAttendanceItemCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.ErAlConditionsAttendanceItem;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionAtr;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ErrorAlarmConditionType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.SingleValueCompareType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.AgreementCheckCon36;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.AgreementCheckCon36Repository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.ErrorAlarmRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.ExtraResultMonthly;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.ExtraResultMonthlyRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.HowDisplayMessage;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.MessageDisplay;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.NameAlarmExtractionCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.SpecHolidayCheckCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.SpecHolidayCheckConRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.TypeCheckVacation;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.TypeMonCheckItem;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.checkremainnumber.CheckConValueRemainingNumber;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.checkremainnumber.CheckOperatorType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.checkremainnumber.CheckRemainNumberMon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.checkremainnumber.CheckRemainNumberMonRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.AttendanceItemId;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedAmountValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimeDuration;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimesValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimesValueDay;
import nts.uk.ctx.at.shared.dom.common.days.MonthlyDays;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class MonAlarmCheckConEventPubSubscriber implements DomainEventSubscriber<MonAlarmCheckConEventPub> {

	@Inject
	private ExtraResultMonthlyRepository extraResultMonthlyRepo;

	@Inject
	private SpecHolidayCheckConRepository specHolidayCheckConRepo;

	@Inject
	private CheckRemainNumberMonRepository checkRemainNumberMonRepo;

	@Inject
	private AgreementCheckCon36Repository agreementCheckCon36Repo;

	@Override
	public Class<MonAlarmCheckConEventPub> subscribedToEventType() {
		return MonAlarmCheckConEventPub.class;
	}

	@Override
	public void handle(MonAlarmCheckConEventPub domainEvent) {
		List<ExtraResultMonthlyDomainEventPubDto> listCheckConMonthly = domainEvent.getListExtraResultMonthly();

		if (domainEvent.isCheckAdd()) {
			for (ExtraResultMonthlyDomainEventPubDto extraResultMonthly : listCheckConMonthly) {
				// add ExtraResultMonthly
				
				addExtraResultMonthly(convertToExtraResultMonDto(extraResultMonthly));

				if (extraResultMonthly.getTypeCheckItem() == 0) {

					// add SpecHolidayCheckCon
					extraResultMonthly.getSpecHolidayCheckCon().setErrorAlarmCheckID(extraResultMonthly.getErrorAlarmCheckID());
					SpecHolidayCheckCon specHolidayCheckCon = convertToSpecHolidayCheckConDto(extraResultMonthly.getSpecHolidayCheckCon());
					addSpecHolidayCheckCon(specHolidayCheckCon);
				} else if (extraResultMonthly.getTypeCheckItem() == 1 || extraResultMonthly.getTypeCheckItem() == 2) {
					// add listAgreementCheckCon36
					extraResultMonthly.getAgreementCheckCon36().setErrorAlarmCheckID(extraResultMonthly.getErrorAlarmCheckID());
					AgreementCheckCon36 agreementCheckCon36 = convertToAgreementCheckCon36AdapterPubDto(extraResultMonthly.getAgreementCheckCon36());
					addAgreementCheckCon36(agreementCheckCon36);
				} else if (extraResultMonthly.getTypeCheckItem() == 3) {
					// add CheckRemainNumberMon
					extraResultMonthly.getCheckRemainNumberMon().setErrorAlarmCheckID(extraResultMonthly.getErrorAlarmCheckID());
					CheckRemainNumberMon checkRemainNumberMon = convertToCheckRemainNumberMonAdapterPubDto(extraResultMonthly.getCheckRemainNumberMon());
					addCheckRemainNumberMon(checkRemainNumberMon);
				}

			}
		} else if (domainEvent.isCheckUpdate()) {
			List<String> listEralIDOld = domainEvent.getListEralCheckIDOld();
			List<String> listEralIDNew = listCheckConMonthly.stream().map(c -> c.getErrorAlarmCheckID()).collect(Collectors.toList());
			List<String> listEralIdRemove = new ArrayList<>();
			for(String eralIdOld : listEralIDOld  ) {
				boolean checkExist = false;
				for(String eralIdNew : listEralIDNew  ) {
					if(eralIdOld.equals(eralIdNew)) {
						checkExist = true;
						break;
					}
				}
				if(!checkExist) {
					listEralIdRemove.add(eralIdOld);
				}
			}
			
			//remove
			for (String eralCheckID : listEralIdRemove) {
				// delete ExtraResultMonthly
				removeExtraResultMonthly(eralCheckID);
				// delete SpecHolidayCheckCon
				removeSpecHolidayCheckCon(eralCheckID);
				// delete CheckRemainNumberMon
				removeCheckRemainNumberMon(eralCheckID);
				// delete agreementCheckCon36
				removeAgreementCheckCon36(eralCheckID);
			}
			
			for (ExtraResultMonthlyDomainEventPubDto extraResultMonthly : listCheckConMonthly) {
				// update ExtraResultMonthly
				updateExtraResultMonthly(convertToExtraResultMonDto(extraResultMonthly));
				if (extraResultMonthly.getTypeCheckItem() == 0) {
					// update SpecHolidayCheckCon
					extraResultMonthly.getSpecHolidayCheckCon().setErrorAlarmCheckID(extraResultMonthly.getErrorAlarmCheckID());
					updateSpecHolidayCheckCon(convertToSpecHolidayCheckConDto(extraResultMonthly.getSpecHolidayCheckCon()));
					removeAgreementCheckCon36(extraResultMonthly.getErrorAlarmCheckID());
					removeCheckRemainNumberMon(extraResultMonthly.getErrorAlarmCheckID());
				} else if (extraResultMonthly.getTypeCheckItem() == 3) {
					// update CheckRemainNumberMon
					extraResultMonthly.getCheckRemainNumberMon().setErrorAlarmCheckID(extraResultMonthly.getErrorAlarmCheckID());
					CheckRemainNumberMon checkRemainNumberMon = convertToCheckRemainNumberMonAdapterPubDto(extraResultMonthly.getCheckRemainNumberMon());
					updateCheckRemainNumberMon(checkRemainNumberMon);
					removeAgreementCheckCon36(extraResultMonthly.getErrorAlarmCheckID());
					removeSpecHolidayCheckCon(extraResultMonthly.getErrorAlarmCheckID());
				} else if (extraResultMonthly.getTypeCheckItem() == 1 || extraResultMonthly.getTypeCheckItem() == 2 ) {
					// update agreementCheckCon36
					extraResultMonthly.getAgreementCheckCon36().setErrorAlarmCheckID(extraResultMonthly.getErrorAlarmCheckID());
					AgreementCheckCon36 agreementCheckCon36 = convertToAgreementCheckCon36AdapterPubDto(extraResultMonthly.getAgreementCheckCon36());
					updateAgreementCheckCon36(agreementCheckCon36);
					removeSpecHolidayCheckCon(extraResultMonthly.getErrorAlarmCheckID());
					removeCheckRemainNumberMon(extraResultMonthly.getErrorAlarmCheckID());
				} else { // when typecheck = 3 4 5 6 7 8
					removeAgreementCheckCon36(extraResultMonthly.getErrorAlarmCheckID());
					removeSpecHolidayCheckCon(extraResultMonthly.getErrorAlarmCheckID());	
					removeCheckRemainNumberMon(extraResultMonthly.getErrorAlarmCheckID());
				}
			}
		} else if (domainEvent.isCheckDelete()) {
			List<String> listEralID = domainEvent.getListEralCheckIDOld();
			for (String eralCheckID : listEralID) {
				// delete ExtraResultMonthly
				removeExtraResultMonthly(eralCheckID);
				// delete SpecHolidayCheckCon
				removeSpecHolidayCheckCon(eralCheckID);
				// delete CheckRemainNumberMon
				removeCheckRemainNumberMon(eralCheckID);
				// delete agreementCheckCon36
				removeAgreementCheckCon36(eralCheckID);
			}
		}
	}

	/**
	 * Add
	 */
	private void addExtraResultMonthly(ExtraResultMonthly extraResultMonthly) {
		if (extraResultMonthlyRepo.getExtraResultMonthlyByID(extraResultMonthly.getErrorAlarmCheckID()).isPresent())
			throw new BusinessException("Msg_3");
		extraResultMonthlyRepo.addExtraResultMonthly(extraResultMonthly);
	}

	private void addSpecHolidayCheckCon(SpecHolidayCheckCon specHolidayCheckCon) {
		if (specHolidayCheckConRepo.getSpecHolidayCheckConById(specHolidayCheckCon.getErrorAlarmCheckID()).isPresent())
			throw new BusinessException("Msg_3");
		specHolidayCheckConRepo.addSpecHolidayCheckCon(specHolidayCheckCon);
	}

	private void addCheckRemainNumberMon(CheckRemainNumberMon checkRemainNumberMon) {
		if (checkRemainNumberMonRepo.getByEralCheckID(checkRemainNumberMon.getErrorAlarmCheckID()).isPresent())
			throw new BusinessException("Msg_3");
		checkRemainNumberMonRepo.addCheckRemainNumberMon(checkRemainNumberMon);
	}

	private void addAgreementCheckCon36(AgreementCheckCon36 agreementCheckCon36) {
		if (agreementCheckCon36Repo.getAgreementCheckCon36ById(agreementCheckCon36.getErrorAlarmCheckID()).isPresent())
			throw new BusinessException("Msg_3");
		agreementCheckCon36Repo.addAgreementCheckCon36(agreementCheckCon36);
	}

	/**
	 * Update
	 */
	private void updateExtraResultMonthly(ExtraResultMonthly extraResultMonthly) {
		if (!extraResultMonthlyRepo.getExtraResultMonthlyByID(extraResultMonthly.getErrorAlarmCheckID()).isPresent()) {
			addExtraResultMonthly(extraResultMonthly);
		}else {
			extraResultMonthlyRepo.updateExtraResultMonthly(extraResultMonthly);
		}
	}

	private void updateSpecHolidayCheckCon(SpecHolidayCheckCon specHolidayCheckCon) {
		if (!specHolidayCheckConRepo.getSpecHolidayCheckConById(specHolidayCheckCon.getErrorAlarmCheckID()).isPresent()) {
			addSpecHolidayCheckCon(specHolidayCheckCon);
		}else {
			specHolidayCheckConRepo.updateSpecHolidayCheckCon(specHolidayCheckCon);
		}
	}

	private void updateCheckRemainNumberMon(CheckRemainNumberMon checkRemainNumberMon) {
		if (!checkRemainNumberMonRepo.getByEralCheckID(checkRemainNumberMon.getErrorAlarmCheckID()).isPresent()) {
			addCheckRemainNumberMon(checkRemainNumberMon);
		}else {
			checkRemainNumberMonRepo.updateCheckRemainNumberMon(checkRemainNumberMon);
		}
	}

	private void updateAgreementCheckCon36(AgreementCheckCon36 agreementCheckCon36) {
		if (!agreementCheckCon36Repo.getAgreementCheckCon36ById(agreementCheckCon36.getErrorAlarmCheckID()).isPresent()) {
			addAgreementCheckCon36(agreementCheckCon36);
		}else {
			agreementCheckCon36Repo.updateAgreementCheckCon36(agreementCheckCon36);
		}
	}

	/**
	 * Remove
	 */
	private void removeExtraResultMonthly(String eralCheckID) {
		if (extraResultMonthlyRepo.getExtraResultMonthlyByID(eralCheckID).isPresent())
			extraResultMonthlyRepo.deleteExtraResultMonthly(eralCheckID);
	}

	private void removeSpecHolidayCheckCon(String eralCheckID) {
		if (specHolidayCheckConRepo.getSpecHolidayCheckConById(eralCheckID).isPresent())
			specHolidayCheckConRepo.deleteSpecHolidayCheckCon(eralCheckID);
	}

	private void removeCheckRemainNumberMon(String eralCheckID) {
		if (checkRemainNumberMonRepo.getByEralCheckID(eralCheckID).isPresent())
			checkRemainNumberMonRepo.deleteCheckRemainNumberMon(eralCheckID);
	}

	private void removeAgreementCheckCon36(String eralCheckID) {
		if (agreementCheckCon36Repo.getAgreementCheckCon36ById(eralCheckID).isPresent())
			agreementCheckCon36Repo.deleteAgreementCheckCon36(eralCheckID);
	}

	private AgreementCheckCon36 convertToAgreementCheckCon36AdapterPubDto(AgreementCheckCon36AdapterPubDto dto) {
		return new AgreementCheckCon36(dto.getErrorAlarmCheckID(), EnumAdaptor.valueOf(dto.getClassification(), ErrorAlarmRecord.class), EnumAdaptor.valueOf(dto.getCompareOperator(), SingleValueCompareType.class), dto.getEralBeforeTime());
	}

	private CheckRemainNumberMon convertToCheckRemainNumberMonAdapterPubDto(CheckRemainNumberMonAdapterPubDto dto) {
		CheckedCondition checkedCondition = new CheckedCondition();
		if (dto.getCheckOperatorType() == 0) {
			checkedCondition = convertToCompareSingleValueAdapterPubDto(dto.getCompareSingleValueEx());
		} else {
			checkedCondition = convertToCompareRangeAdapterPubDto(dto.getCompareRangeEx());
		}
		return new CheckRemainNumberMon(dto.getErrorAlarmCheckID(), 
				EnumAdaptor.valueOf(dto.getCheckVacation(), TypeCheckVacation.class), 
				checkedCondition, EnumAdaptor.valueOf(dto.getCheckOperatorType(), CheckOperatorType.class),
				dto.getListItemID()
				);
	}

	private CompareRange<CheckConValueRemainingNumber> convertToCompareRangeAdapterPubDto(CompareRangeAdapterPubDto dto) {
		CheckConValueRemainingNumber startValue = new CheckConValueRemainingNumber(dto.getStartValue().getDaysValue(), dto.getStartValue().getTimeValue() == null ? null : Optional.of(dto.getStartValue().getTimeValue()));
		CheckConValueRemainingNumber endValue = new CheckConValueRemainingNumber(dto.getEndValue().getDaysValue(), dto.getEndValue().getTimeValue() == null ? null : Optional.of(dto.getEndValue().getTimeValue()));

		CompareRange<CheckConValueRemainingNumber> data = new CompareRange<>(dto.getCompareOperator());
		data.setStartValue(startValue);
		data.setEndValue(endValue);
		return data;
	}

	private CompareSingleValue<CheckConValueRemainingNumber> convertToCompareSingleValueAdapterPubDto(CompareSingleValueAdapterPubDto dto) {
		CheckConValueRemainingNumber value = new CheckConValueRemainingNumber(
				dto.getValue().getDaysValue(), dto.getValue().getTimeValue() == null ? null : Optional.of(dto.getValue().getTimeValue()));
		CompareSingleValue<CheckConValueRemainingNumber> data = new CompareSingleValue<>(dto.getCompareOpertor(), 0);
		data.setValue(value);
		return data;
	}

	private SpecHolidayCheckCon convertToSpecHolidayCheckConDto(SpecHolidayCheckConAdapterPubDto dto) {
		return new SpecHolidayCheckCon(
				dto.getErrorAlarmCheckID(), dto.getCompareOperator(), 
				new MonthlyDays(dto.getNumberDayDiffHoliday1().doubleValue()),
				dto.getNumberDayDiffHoliday2() == null ? null : new MonthlyDays(dto.getNumberDayDiffHoliday2().doubleValue()));

	}

	private ExtraResultMonthly convertToExtraResultMonDto(ExtraResultMonthlyDomainEventPubDto toDto) {

		/* EA is worng, don't need these fields (companyId and errorAlarmCode */
		String companyId = "";
		String errorAlarmCode = "";
//		int conAtr = 0;
//		switch(toDto.getTypeCheckItem()) {
//		case 4 : conAtr = 1;break;
//		case 5 : conAtr = 4;break;
//		case 6 : conAtr = 3;break;
//		case 7 : conAtr = 0;break;
//		}
		
		AttendanceItemCondition attendanceItemCon = null;
		if (toDto.getCheckConMonthly() != null) {
			List<ErAlAttendanceItemCondition<?>> conditionsGroup1 = toDto.getCheckConMonthly().getGroup1().getLstErAlAtdItemCon().stream().filter(item -> item.getCompareStartValue() != null)
					.map(atdItemCon -> convertAtdIemConToDomain(atdItemCon, companyId, errorAlarmCode)).collect(Collectors.toList());
			List<ErAlAttendanceItemCondition<?>> conditionsGroup2 = null;
			if(toDto.getCheckConMonthly().getGroup2()!=null) {
				conditionsGroup2 = toDto.getCheckConMonthly().getGroup2().getLstErAlAtdItemCon().stream().filter(item -> item.getCompareStartValue() != null)
					.map(atdItemCon -> convertAtdIemConToDomain(atdItemCon, companyId, errorAlarmCode)).collect(Collectors.toList());
			}
			attendanceItemCon = AttendanceItemCondition.init(toDto.getCheckConMonthly().getOperatorBetweenGroups(), toDto.getCheckConMonthly().isGroup2UseAtr());
			attendanceItemCon.setGroup1(setErAlConditionsAttendanceItem(toDto.getCheckConMonthly().getGroup1().getConditionOperator(), conditionsGroup1));
			if(toDto.getCheckConMonthly().getGroup2()!=null) {
				attendanceItemCon.setGroup2(setErAlConditionsAttendanceItem(toDto.getCheckConMonthly().getGroup2().getConditionOperator(), conditionsGroup2));
			}
		}
		return new ExtraResultMonthly(toDto.getErrorAlarmCheckID(), 
				toDto.getSortBy(), 
				new NameAlarmExtractionCondition(toDto.getNameAlarmExtraCon()), 
				toDto.isUseAtr(), 
				EnumAdaptor.valueOf(toDto.getTypeCheckItem(), TypeMonCheckItem.class),
				new HowDisplayMessage(toDto.isMessageBold(), 
				toDto.getMessageColor()), 
				new MessageDisplay(toDto.getDisplayMessage()), 
				attendanceItemCon);
	}

	private ErAlConditionsAttendanceItem setErAlConditionsAttendanceItem(int conditionOperator, List<ErAlAttendanceItemCondition<?>> conditions) {
		ErAlConditionsAttendanceItem group = ErAlConditionsAttendanceItem.init(conditionOperator);
		group.addAtdItemConditions(conditions);

		return group;

	}

	@SuppressWarnings("unchecked")
	private <V> ErAlAttendanceItemCondition<V> convertAtdIemConToDomain(ErAlAtdItemConAdapterPubDto atdItemCon, String companyId, String errorAlarmCode) {
		ErAlAttendanceItemCondition<V> atdItemConDomain = new ErAlAttendanceItemCondition<V>(companyId, errorAlarmCode,
				atdItemCon.getTargetNO(), atdItemCon.getConditionAtr(), atdItemCon.isUseAtr(),
				atdItemCon.getConditionType());
		
		// Set Target
		if (atdItemCon.getConditionAtr() == ConditionAtr.TIME_WITH_DAY.value || atdItemCon.getConditionType() == ErrorAlarmConditionType.INPUT_CHECK.value) {
			atdItemConDomain.setUncountableTarget(atdItemCon.getUncountableAtdItem());
		} else {
			atdItemConDomain.setCountableTarget(atdItemCon.getCountableAddAtdItems(), atdItemCon.getCountableSubAtdItems());
		}
		// Set Compare
		if (atdItemCon.getConditionType() < 2) {
			if (atdItemCon.getCompareOperator() > 5) {
				if (atdItemCon.getConditionAtr() == ConditionAtr.AMOUNT_VALUE.value ) {
					atdItemConDomain.setCompareRange(atdItemCon.getCompareOperator(), (V) new CheckedAmountValue(atdItemCon.getCompareStartValue().intValue()), (V) new CheckedAmountValue(atdItemCon.getCompareEndValue().intValue()));
				} else if (atdItemCon.getConditionAtr() == ConditionAtr.TIME_DURATION.value) {
					atdItemConDomain.setCompareRange(atdItemCon.getCompareOperator(), (V) new CheckedTimeDuration(atdItemCon.getCompareStartValue().intValue()), (V) new CheckedTimeDuration(atdItemCon.getCompareEndValue().intValue()));
				} else if (atdItemCon.getConditionAtr() == ConditionAtr.TIME_WITH_DAY.value) {
					atdItemConDomain.setCompareRange(atdItemCon.getCompareOperator(), (V) new TimeWithDayAttr(atdItemCon.getCompareStartValue().intValue()), (V) new TimeWithDayAttr(atdItemCon.getCompareEndValue().intValue()));
				} else if (atdItemCon.getConditionAtr() == ConditionAtr.TIMES.value ) {
					atdItemConDomain.setCompareRange(atdItemCon.getCompareOperator(), atdItemCon.getCompareStartValue() !=null? (V) new CheckedTimesValue(atdItemCon.getCompareStartValue().intValue()):(V) new CheckedTimesValue(0),
							atdItemCon.getCompareEndValue() !=null?(V) new CheckedTimesValue(atdItemCon.getCompareEndValue().intValue()):(V) new CheckedTimesValue(0));
				} else if(atdItemCon.getConditionAtr() == ConditionAtr.DAYS.value ){
					atdItemConDomain.setCompareRange(atdItemCon.getCompareOperator(),atdItemCon.getCompareStartValue() !=null? (V) new CheckedTimesValueDay(atdItemCon.getCompareStartValue().doubleValue()):(V) new CheckedTimesValueDay((double) 0),
							atdItemCon.getCompareEndValue() !=null?(V) new CheckedTimesValueDay(atdItemCon.getCompareEndValue().doubleValue()):(V) new CheckedTimesValueDay((double) 0));
				}
				
			} else {
				if (atdItemCon.getConditionType() == ConditionType.FIXED_VALUE.value) {
					if (atdItemCon.getConditionAtr() == ConditionAtr.AMOUNT_VALUE.value) {
						atdItemConDomain.setCompareSingleValue(atdItemCon.getCompareOperator(), atdItemCon.getConditionType(), (V) new CheckedAmountValue(atdItemCon.getCompareStartValue().intValue()));
					} else if (atdItemCon.getConditionAtr() == ConditionAtr.TIME_DURATION.value) {
						atdItemConDomain.setCompareSingleValue(atdItemCon.getCompareOperator(), atdItemCon.getConditionType(), (V) new CheckedTimeDuration(atdItemCon.getCompareStartValue().intValue()));
					} else if (atdItemCon.getConditionAtr() == ConditionAtr.TIME_WITH_DAY.value) {
						atdItemConDomain.setCompareSingleValue(atdItemCon.getCompareOperator(), atdItemCon.getConditionType(), (V) new TimeWithDayAttr(atdItemCon.getCompareStartValue().intValue()));
					} else if (atdItemCon.getConditionAtr() == ConditionAtr.TIMES.value) {
						atdItemConDomain.setCompareSingleValue(atdItemCon.getCompareOperator(), atdItemCon.getConditionType(), (V) new CheckedTimesValue(atdItemCon.getCompareStartValue().intValue()));
					}else if(atdItemCon.getConditionAtr() == ConditionAtr.DAYS.value){
						atdItemConDomain.setCompareSingleValue(atdItemCon.getCompareOperator(), atdItemCon.getConditionType(), (V) new CheckedTimesValueDay(atdItemCon.getCompareStartValue().doubleValue()));
					}
				} else {
					atdItemConDomain.setCompareSingleValue(atdItemCon.getCompareOperator(), atdItemCon.getConditionType(), (V) new AttendanceItemId(atdItemCon.getSingleAtdItem()));
				}
			}
		} else {
			atdItemConDomain.setInputCheck(atdItemCon.getInputCheckCondition().intValue());
		}
		return atdItemConDomain;
	}

}
