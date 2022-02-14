package nts.uk.ctx.at.aggregation.dom.form9;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.GetEmpCanReferService;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

@RunWith(JMockit.class)
public class GetForm9OutputEmployeeInfoServiceTest {

	@Injectable
	private GetForm9OutputEmployeeInfoService.Require require;

	/**
	 * Target	: get
	 * Pattern	: 様式９の出力社員情報 = empty
	 */
	@Test
	public void testGet_empty() {
		TargetOrgIdenInfor targetOrg = TargetOrgIdenInfor.creatIdentifiWorkplaceGroup("workplaceGroupId");
		GeneralDate baseDate = GeneralDate.ymd(2021, 1, 1);
		String loginEmployeeId = "employeeLoginID";
		List<String> employeeRangeIds = Arrays.asList("sid1", "sid3", "sid5", "sid2", "sid4");

		List<String> employeeSortedIds = Arrays.asList("sid5", "sid2", "sid3", "sid1", "sid4");
		new Expectations(
					SortByForm9Service.class
				,	Form9OutputEmployeeInfo.class
				,	GetEmpCanReferService.class) {
			{
				require.getLoginEmployeeId();
				result = loginEmployeeId;

				GetEmpCanReferService.getByOrg(require, loginEmployeeId, baseDate, DatePeriod.oneDay(baseDate), targetOrg);
				result = employeeRangeIds;

				SortByForm9Service.sort(require, baseDate, employeeRangeIds);
				result = employeeSortedIds;

			}
		};

		val result = GetForm9OutputEmployeeInfoService.get(require, "workplaceGroupId", baseDate);

		assertThat( result.getEmployeeInfoList() ).isEmpty();

	}

	/**
	 * Target	: get
	 * Pattern	: 様式９の出力社員情報  not empty
	 */
	@Test
	public void testGet(
				@Injectable Form9OutputEmployeeInfo outputEmployee_1
			,	@Injectable Form9OutputEmployeeInfo outputEmployee_2
			,	@Injectable Form9OutputEmployeeInfo outputEmployee_3
			,	@Injectable Form9OutputEmployeeInfo outputEmployee_4
			,	@Injectable Form9OutputEmployeeInfo outputEmployee_5
			,	@Injectable Form9OutputEmployeeInfo outputEmployee_8) {

		val targetOrg = TargetOrgIdenInfor.creatIdentifiWorkplaceGroup("workplaceGroupId");
		val baseDate = GeneralDate.ymd(2021, 1, 1);
		val loginEmployeeId = "employeeLoginID";
		val employeeRangeIds = Arrays.asList("sid1", "sid2", "sid3", "sid4", "sid7", "sid6", "sid9", "sid11", "sid12", "sid8");
		val employeeSortedIds = Arrays.asList("sid5", "sid2", "sid1", "sid3");

		new Expectations(
					SortByForm9Service.class
				,	Form9OutputEmployeeInfo.class
				,	GetEmpCanReferService.class) {
			{
				require.getLoginEmployeeId();
				result = loginEmployeeId;

				GetEmpCanReferService.getByOrg(require, loginEmployeeId, (GeneralDate) any, (DatePeriod)any, targetOrg);
				result = employeeRangeIds;

				SortByForm9Service.sort(require, (GeneralDate) any, employeeRangeIds);
				result = employeeSortedIds;

				Form9OutputEmployeeInfo.create(require, (GeneralDate) any , "sid1");
				result = Optional.of(outputEmployee_1);

				Form9OutputEmployeeInfo.create(require, (GeneralDate) any , "sid2");
				result = Optional.of(outputEmployee_2);

				Form9OutputEmployeeInfo.create(require, (GeneralDate) any , "sid3");
				result = Optional.of(outputEmployee_3);

				Form9OutputEmployeeInfo.create(require, (GeneralDate) any , "sid5");
				result = Optional.of(outputEmployee_5);

			}
		};

		val result = GetForm9OutputEmployeeInfoService.get(require, "workplaceGroupId", baseDate);

		assertThat(result.getEmployeeInfoList())
			.containsExactlyElementsOf(
					Arrays.asList(
								outputEmployee_5
							,	outputEmployee_2
							,	outputEmployee_1
							,	outputEmployee_3
								));
	}

}
