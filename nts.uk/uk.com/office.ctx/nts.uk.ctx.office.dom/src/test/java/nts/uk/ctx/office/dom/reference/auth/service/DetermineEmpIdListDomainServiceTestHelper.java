package nts.uk.ctx.office.dom.reference.auth.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.office.dom.favorite.adapter.EmployeeJobHistImport;
import nts.uk.ctx.office.dom.reference.auth.SpecifyAuthInquiry;

public class DetermineEmpIdListDomainServiceTestHelper {
	/**
	 * Mock [R-1] 参照できる権限を取得する
	 * 見られる職位ID EMPTY
	 */
	public static Optional<SpecifyAuthInquiry> mockRequireGetByCidAndRoleId() {
		return Optional.ofNullable(SpecifyAuthInquiry.builder()
						.cid("cid")
						.employmentRoleId("employmentRoleId")
						.positionIdSeen(Collections.emptyList())
						.build());
	}
	
	/**
	 * Mock [R-1] 参照できる権限を取得する
	 */
	public static Optional<SpecifyAuthInquiry> mockRequireGetByCidAndRoleId(String positionIdSeen) {
		List<String> positionIdSeens = new ArrayList<String>();
		positionIdSeens.add(positionIdSeen);
		return Optional.ofNullable(SpecifyAuthInquiry.builder()
						.cid("cid")
						.employmentRoleId("employmentRoleId")
						.positionIdSeen(positionIdSeens)
						.build());
	}

	/**
	 * Mock [R-2] 社員の職位を取得する
	 */
	public static Map<String, EmployeeJobHistImport> mockRequireGetPositionBySidsAndBaseDate(String jobTitleId) {
		EmployeeJobHistImport emp = EmployeeJobHistImport.builder()
				.employeeId("employeeId")
				.jobTitleID(jobTitleId)
				.jobTitleName("jobTitleName")
				.sequenceCode("sequenceCode")
				.startDate(GeneralDate.today())
				.endDate(GeneralDate.today())
				.jobTitleCode("jobTitleCode")
				.build();
		Map<String, EmployeeJobHistImport> positionBySidsAndBaseDate = new HashMap<>();
		positionBySidsAndBaseDate.put("loginSid", emp);
		return positionBySidsAndBaseDate;
	}
}
