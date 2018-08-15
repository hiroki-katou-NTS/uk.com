package nts.uk.ctx.at.function.pubimp.alarm.checkcondition;

import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.dom.event.DomainEventSubscriber;
import nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto.AttendanceItemConAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto.ErAlAtdItemConAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto.ErAlConAttendanceItemAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.AgreementCheckCon36FunImport;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.SpecHolidayCheckConFunImport;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.checkremainnumber.CheckConValueRemainNumberImport;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.checkremainnumber.CheckRemainNumberMonFunImport;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.checkremainnumber.CompareRangeImport;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.checkremainnumber.CompareSingleValueImport;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.monthly.MonAlarmCheckConEvent;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.monthly.dtoevent.ExtraResultMonthlyDomainEventDto;
import nts.uk.ctx.at.function.pub.alarm.checkcondition.MonAlarmCheckConEventPub;
import nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto.AgreementCheckCon36AdapterPubDto;
import nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto.AttendanceItemConAdapterPubDto;
import nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto.CheckConValueRemainNumberAdapterPubDto;
import nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto.CheckRemainNumberMonAdapterPubDto;
import nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto.CompareRangeAdapterPubDto;
import nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto.CompareSingleValueAdapterPubDto;
import nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto.ErAlAtdItemConAdapterPubDto;
import nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto.ErAlConAttendanceItemAdapterPubDto;
import nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto.ExtraResultMonthlyDomainEventPubDto;
import nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto.SpecHolidayCheckConAdapterPubDto;

@Stateless
public class MonAlarmCheckConEventSubscriber implements DomainEventSubscriber<MonAlarmCheckConEvent> {

	@Override
	public Class<MonAlarmCheckConEvent> subscribedToEventType() {
		return MonAlarmCheckConEvent.class;
	}

	@Override
	public void handle(MonAlarmCheckConEvent domainEvent) {
		MonAlarmCheckConEventPub p = convertToMonAlarmCheckConEvent(domainEvent);
		p.toBePublished();
	}
	
	private MonAlarmCheckConEventPub convertToMonAlarmCheckConEvent(MonAlarmCheckConEvent export) {
		return new MonAlarmCheckConEventPub(
				export.getMonAlarmCheckConID(),
				export.isCheckUpdate(),
				export.isCheckAdd(),
				export.isCheckDelete(),
				export.getListExtraResultMonthly().stream()
				.map(c->convertToExtraResultMonthlyDomainEventDto(c)).collect(Collectors.toList()),
				export.getListEralCheckIDOld()
				);
	}
	
	private ExtraResultMonthlyDomainEventPubDto convertToExtraResultMonthlyDomainEventDto(ExtraResultMonthlyDomainEventDto export) {
		return new ExtraResultMonthlyDomainEventPubDto(
				export.getErrorAlarmCheckID(),
				export.getSortBy(),
				export.getNameAlarmExtraCon(),
				export.isUseAtr(),
				export.getTypeCheckItem(),
				export.isMessageBold(),
				export.getMessageColor(),
				export.getDisplayMessage(),
				export.getCheckConMonthly() == null?null: convertToAttendanceItemCon(export.getCheckConMonthly()),
				export.getSpecHolidayCheckCon() == null?null: convertToSpecHolidayCheckConFunImport(export.getSpecHolidayCheckCon()),
				export.getCheckRemainNumberMon() == null?null: convertToCheckRemainNumberMonFunImport(export.getCheckRemainNumberMon()),
				export.getAgreementCheckCon36() == null?null: convertToAgreementCheckCon36Import(export.getAgreementCheckCon36())
				);
	}
	
	private SpecHolidayCheckConAdapterPubDto convertToSpecHolidayCheckConFunImport(SpecHolidayCheckConFunImport specHolidayCheckCon) {
		return new SpecHolidayCheckConAdapterPubDto(
				specHolidayCheckCon.getErrorAlarmCheckID(),
				specHolidayCheckCon.getCompareOperator(),
				specHolidayCheckCon.getNumberDayDiffHoliday1(),
				specHolidayCheckCon.getNumberDayDiffHoliday2()
				);
	}
	
	private CheckRemainNumberMonAdapterPubDto convertToCheckRemainNumberMonFunImport(CheckRemainNumberMonFunImport checkRemainNumberMon) {
		return new CheckRemainNumberMonAdapterPubDto(
				checkRemainNumberMon.getErrorAlarmCheckID(),
				checkRemainNumberMon.getCheckVacation(),
				checkRemainNumberMon.getCheckOperatorType(),
				checkRemainNumberMon.getCompareRangeEx()==null?null: convertToCompareRangeImport(checkRemainNumberMon.getCompareRangeEx()),
				checkRemainNumberMon.getCompareSingleValueEx()==null?null:convertToCompareSingleValueImport(checkRemainNumberMon.getCompareSingleValueEx()),
				checkRemainNumberMon.getListItemID()
				);
	}
	
	private CompareRangeAdapterPubDto convertToCompareRangeImport (CompareRangeImport compareRange) {
		return new CompareRangeAdapterPubDto(
				compareRange.getCompareOperator(),
				convertToCheckConValueRemainNumberFunImport(compareRange.getStartValue()),
				convertToCheckConValueRemainNumberFunImport(compareRange.getEndValue())
				);
	}
	
	 private CompareSingleValueAdapterPubDto convertToCompareSingleValueImport (CompareSingleValueImport compareSingleValue) {
		 return new CompareSingleValueAdapterPubDto(
				 compareSingleValue.getCompareOperator(),
				 convertToCheckConValueRemainNumberFunImport(compareSingleValue.getValue())
				 );
	 }
	
	private CheckConValueRemainNumberAdapterPubDto convertToCheckConValueRemainNumberFunImport(CheckConValueRemainNumberImport checkConValueRemainNumber) {
		return new CheckConValueRemainNumberAdapterPubDto(
				checkConValueRemainNumber.getDaysValue(),
				checkConValueRemainNumber.getTimeValue()
				);
	}
	
	private AgreementCheckCon36AdapterPubDto convertToAgreementCheckCon36Import(AgreementCheckCon36FunImport agreementCheckCon36) {
		return new AgreementCheckCon36AdapterPubDto(
				agreementCheckCon36.getErrorAlarmCheckID(),
				agreementCheckCon36.getClassification(),
				agreementCheckCon36.getCompareOperator(),
				agreementCheckCon36.getEralBeforeTime()
				);
	}
	
	private AttendanceItemConAdapterPubDto convertToAttendanceItemCon (AttendanceItemConAdapterDto export) {
		return new AttendanceItemConAdapterPubDto(
				export.getOperatorBetweenGroups(),
				convertToErAlConAttendanceItem(export.getGroup1()),
				export.getGroup2() ==null ?null:convertToErAlConAttendanceItem(export.getGroup2()),
				export.isGroup2UseAtr()
				);
	}
	
	private ErAlConAttendanceItemAdapterPubDto convertToErAlConAttendanceItem(ErAlConAttendanceItemAdapterDto export) {
		return new  ErAlConAttendanceItemAdapterPubDto(
				export.getAtdItemConGroupId(),
				export.getConditionOperator(),
				export.getLstErAlAtdItemCon().stream().map(c->convertToErAlAtdItemCon(c)).collect(Collectors.toList())
				);
	}
	private ErAlAtdItemConAdapterPubDto convertToErAlAtdItemCon(ErAlAtdItemConAdapterDto export) {
		return new ErAlAtdItemConAdapterPubDto(export.getTargetNO(), export.getConditionAtr(), export.isUseAtr(),
				export.getUncountableAtdItem(), export.getCountableAddAtdItems(), export.getCountableSubAtdItems(),
				export.getConditionType(), export.getCompareOperator(), export.getSingleAtdItem(),
				export.getCompareStartValue(), export.getCompareEndValue(), export.getInputCheckCondition());
	}
	

}
