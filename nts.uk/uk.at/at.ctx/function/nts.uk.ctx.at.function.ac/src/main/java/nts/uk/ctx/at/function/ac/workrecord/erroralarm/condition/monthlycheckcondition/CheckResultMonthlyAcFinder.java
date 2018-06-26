package nts.uk.ctx.at.function.ac.workrecord.erroralarm.condition.monthlycheckcondition;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.adapter.checkresultmonthly.CheckResultMonthlyAdapter;
import nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto.AttendanceItemConAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto.ErAlAtdItemConAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto.ErAlConAttendanceItemAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.AgreementCheckCon36FunImport;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.SpecHolidayCheckConFunImport;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.find.AttendanceItemConditionPubExport;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.find.ErAlAtdItemConditionPubExport;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.find.ErAlConditionsAttendanceItemPubExport;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.AgreementCheckCon36PubEx;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.SpecHolidayCheckConPubEx;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.CheckResultMonthlyPub;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;

@Stateless
public class CheckResultMonthlyAcFinder implements CheckResultMonthlyAdapter {

	@Inject
	private CheckResultMonthlyPub  checkResultMonthlyPub;
	
	@Override
	public boolean checkPublicHoliday(String companyId, String employeeCd, String employeeId, String workplaceId,
			boolean isManageComPublicHd, YearMonth yearMonth, SpecHolidayCheckConFunImport specHolidayCheckCon) {
		return checkResultMonthlyPub.checkPublicHoliday(companyId, employeeCd, employeeId, workplaceId, isManageComPublicHd, yearMonth,
				convertToSpecHolidayCheckConFunImport(specHolidayCheckCon));
	}
	
	private SpecHolidayCheckConPubEx convertToSpecHolidayCheckConFunImport(SpecHolidayCheckConFunImport specHolidayCheckCon) {
		return new SpecHolidayCheckConPubEx(
				specHolidayCheckCon.getErrorAlarmCheckID(),
				specHolidayCheckCon.getCompareOperator(),
				specHolidayCheckCon.getNumberDayDiffHoliday1(),
				specHolidayCheckCon.getNumberDayDiffHoliday2()
				);
	}

	@Override
	public boolean check36AgreementCondition(String companyId, String employeeId, GeneralDate date, YearMonth yearMonth,
			Year year, AgreementCheckCon36FunImport agreementCheckCon36) {
		return checkResultMonthlyPub.check36AgreementCondition(companyId, employeeId, date, yearMonth, year, 
				convertToAgreementCheckCon36Import(agreementCheckCon36));
	}
	
	private AgreementCheckCon36PubEx convertToAgreementCheckCon36Import(AgreementCheckCon36FunImport agreementCheckCon36) {
		return new AgreementCheckCon36PubEx(
				agreementCheckCon36.getErrorAlarmCheckID(),
				agreementCheckCon36.getClassification(),
				agreementCheckCon36.getCompareOperator(),
				agreementCheckCon36.getEralBeforeTime()
				);
	}

	@Override
	public boolean checkPerTimeMonActualResult(YearMonth yearMonth, int closureID, Integer closureDate,
			String employeeID, AttendanceItemConAdapterDto attendanceItemCondition) {
		return checkResultMonthlyPub.checkPerTimeMonActualResult(yearMonth, closureID, closureDate, employeeID, 
				convertToAttendanceItemCon(attendanceItemCondition));
	}
	
	private AttendanceItemConditionPubExport convertToAttendanceItemCon (AttendanceItemConAdapterDto export) {
		return new AttendanceItemConditionPubExport(
				export.getOperatorBetweenGroups(),
				convertToErAlConAttendanceItem(export.getGroup1()),
				export.getGroup2() ==null ?null:convertToErAlConAttendanceItem(export.getGroup2()),
				export.isGroup2UseAtr()
				);
	}
	
	private ErAlConditionsAttendanceItemPubExport convertToErAlConAttendanceItem(ErAlConAttendanceItemAdapterDto export) {
		return new  ErAlConditionsAttendanceItemPubExport(
				export.getAtdItemConGroupId(),
				export.getConditionOperator(),
				export.getLstErAlAtdItemCon().stream().map(c->convertToErAlAtdItemCon(c)).collect(Collectors.toList())
				);
	}
	private ErAlAtdItemConditionPubExport convertToErAlAtdItemCon(ErAlAtdItemConAdapterDto export) {
		return new ErAlAtdItemConditionPubExport(
				export.getTargetNO(),
				export.getConditionAtr(),
				export.isUseAtr(),
				export.getUncountableAtdItem(),
				export.getCountableAddAtdItems(),
				export.getCountableSubAtdItems(),
				export.getConditionType(),
				export.getCompareOperator(),
				export.getSingleAtdItem(),
				export.getCompareStartValue(),
				export.getCompareEndValue()
				);
	}

}
