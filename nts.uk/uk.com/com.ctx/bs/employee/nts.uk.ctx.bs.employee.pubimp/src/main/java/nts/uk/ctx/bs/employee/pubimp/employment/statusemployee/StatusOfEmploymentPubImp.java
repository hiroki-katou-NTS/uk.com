package nts.uk.ctx.bs.employee.pubimp.employment.statusemployee;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistByEmployee;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistItem;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsItemRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHisItem;
import nts.uk.ctx.bs.employee.pub.employment.statusemployee.StatusOfEmployment;
import nts.uk.ctx.bs.employee.pub.employment.statusemployee.StatusOfEmploymentExport;
import nts.uk.ctx.bs.employee.pub.employment.statusemployee.StatusOfEmploymentPub;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class StatusOfEmploymentPubImp implements StatusOfEmploymentPub {

	@Inject
	private TempAbsItemRepository temporaryAbsenceItemRepo;

	@Inject
	private AffCompanyHistRepository affCompanyHistRepo;

	@Override
	public StatusOfEmploymentExport getStatusOfEmployment(String employeeId, GeneralDate referenceDate) {
		AffCompanyHistByEmployee employeeHistory = affCompanyHistRepo.getAffCompanyHistoryOfEmployee(employeeId)
				.getAffCompanyHistByEmployee(employeeId);

		if (employeeHistory == null) {
			return null;
		}

		StatusOfEmploymentExport statusOfEmploymentExport = new StatusOfEmploymentExport();
		statusOfEmploymentExport.setEmployeeId(employeeId);
		statusOfEmploymentExport.setRefereneDate(referenceDate);

		// get HistoryItem match with referenceDate
		Optional<AffCompanyHistItem> historyItem = employeeHistory.getHistoryWithReferDate(referenceDate);

		if (historyItem.isPresent()) {
			// match startDate <= referenceDate <= endDate
			// lấy domain 休職休業 TemporaryAbsence theo employeeId và referenceDate
			Optional<TempAbsenceHisItem> temporaryAbsenceDomain = temporaryAbsenceItemRepo
					.getByEmpIdAndStandardDate(employeeId, referenceDate);
			if (temporaryAbsenceDomain.isPresent()) {
				// tốn tại domain
				// set LeaveHolidayType
				int tempAbsenceFrNo = temporaryAbsenceDomain.get().getTempAbsenceFrNo().v().intValue();
				statusOfEmploymentExport.setTempAbsenceFrNo(tempAbsenceFrNo);

				if (tempAbsenceFrNo == 1) {
					// trường hợp 休職休業区分＝休職 LeaveHolidayState = TEMP_LEAVE(1)

					// StatusOfEmployment = LEAVE_OF_ABSENCE
					statusOfEmploymentExport.setStatusOfEmployment(StatusOfEmployment.LEAVE_OF_ABSENCE.value);
				} else {
					// StatusOfEmployment = HOLIDAY
					statusOfEmploymentExport.setStatusOfEmployment(StatusOfEmployment.HOLIDAY.value);
				}

			} else {
				// StatusOfEmployment = INCUMBENT
				statusOfEmploymentExport.setStatusOfEmployment(StatusOfEmployment.INCUMBENT.value);

			}

		} else {
			// NOT match startDate <= referenceDate <= endDate
			Optional<AffCompanyHistItem> hitoryBefore = employeeHistory.getHistoryBeforeReferDate(referenceDate);
			
			if (hitoryBefore.isPresent()) {
				statusOfEmploymentExport.setStatusOfEmployment(StatusOfEmployment.RETIREMENT.value);
			} else {
				Optional<AffCompanyHistItem> hitoryAfter = employeeHistory.getHistoryAfterReferDate(referenceDate);
				if (hitoryAfter.isPresent()) {
					statusOfEmploymentExport.setStatusOfEmployment(StatusOfEmployment.BEFORE_JOINING.value);
				}
			}
		}

		return statusOfEmploymentExport;
	}

}
