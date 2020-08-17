package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.qualificationhistory;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HoldingQualificationDto {

	private String employeeId;

	private List<Eligibility> eligibility;
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class Eligibility {
	private String qualificationCd;

	private String qualificationId;

	private String categoryCd;

	private String divisionName;

	private GeneralDate endDate;

	private String qualificationName;

	private String qualificationRank;

	private String qualificationOrganization;

	private String qualificationNumber;
}