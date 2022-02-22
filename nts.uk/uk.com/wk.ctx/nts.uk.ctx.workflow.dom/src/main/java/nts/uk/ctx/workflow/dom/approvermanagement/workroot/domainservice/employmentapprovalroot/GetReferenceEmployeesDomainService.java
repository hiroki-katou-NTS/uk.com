package nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.StatusOfEmployment;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.adapter.EmployeeInDesignatedImport;

public class GetReferenceEmployeesDomainService {

	/**
	 * [1]取得する
	 * 
	 * @param require
	 * @param baseDate 年月日
	 * @return List<社員ID>
	 */
	public static List<String> get(Require require, GeneralDate baseDate) {
		// ロールIDから参照可能な職場リストを取得する
		List<String> workplaceIds = require.findWorkplaceList(baseDate);
		if (workplaceIds.isEmpty()) {
			return Collections.emptyList();
		}

		// 指定職場の指定在籍状態の社員を取得
		List<Integer> empStatus = Arrays.asList(StatusOfEmployment.INCUMBENT.value,
				StatusOfEmployment.LEAVE_OF_ABSENCE.value, StatusOfEmployment.HOLIDAY.value);
		return require.findEmployeeList(workplaceIds, baseDate, empStatus).stream()
				.map(EmployeeInDesignatedImport::getSid).collect(Collectors.toList());
	}

	public interface Require {

		// [R-1]ロールIDから参照可能な職場リストを取得する
		List<String> findWorkplaceList(GeneralDate baseDate);

		// [R-2]指定職場の指定在籍状態の社員を取得
		List<EmployeeInDesignatedImport> findEmployeeList(List<String> workplaceIds, GeneralDate baseDate,
				List<Integer> empStatus);
	}
}
