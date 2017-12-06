package nts.uk.ctx.bs.employee.app.find.employee.history;

import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHist;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistByEmployee;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistItem;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyInfo;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Setter
public class AffCompanyHistInfoDto extends PeregDomainDto {
	@PeregItem("IS00020")
	private GeneralDate jobEntryDate;

	@PeregItem("IS00021")
	private GeneralDate retirementDate;

	@PeregItem("IS00022")
	private GeneralDate adoptionDate;

	@PeregItem("IS00023")
	private String recruitmentClassification;

	@PeregItem("IS00024")
	private GeneralDate retirementAllowanceCalcStartDate;

	public static AffCompanyHistInfoDto fromDomain(AffCompanyHist domain, AffCompanyInfo info) {
		if (domain == null || info == null) {
			return null;
		}

		AffCompanyHistInfoDto dto = new AffCompanyHistInfoDto();
		AffCompanyHistByEmployee empHist = domain.getLstAffCompanyHistByEmployee().get(0);

		if (empHist == null) {
			return null;
		}

		AffCompanyHistItem histItem = empHist.getLstAffCompanyHistoryItem().get(0);

		if (histItem == null) {
			return null;
		}

		dto.setJobEntryDate(histItem.getDatePeriod().start());
		dto.setRetirementDate(histItem.getDatePeriod().end());

		dto.setAdoptionDate(info.getAdoptionDate());
		dto.setRecruitmentClassification(info.getRecruitmentClassification().v());
		dto.setRetirementAllowanceCalcStartDate(info.getRetirementAllowanceCalcStartDate());

		return dto;
	}
}
