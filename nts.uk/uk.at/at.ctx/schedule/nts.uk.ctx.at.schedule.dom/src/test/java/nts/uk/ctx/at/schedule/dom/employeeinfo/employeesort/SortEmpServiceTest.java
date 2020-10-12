package nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.adapter.jobtitle.PositionImport;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.SortEmpService.Require;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.EmpLicenseClassification;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.GetEmpLicenseClassificationService;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.LicenseClassification;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRank;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankCode;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankPriority;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamCd;

@RunWith(JMockit.class)
public class SortEmpServiceTest {

	@Injectable
	private Require require;

	/**
	 * require.並び替え設定を取得する() is empty
	 */
	@Test
	public void testSortEmpTheirOrder() {
		GeneralDate ymd = GeneralDate.today();
		List<String> lstEmpId = Arrays.asList("emp1", "emp2" , "emp3");

		new Expectations() {
			{
				require.get();
			}
		};

		List<String> listData = SortEmpService.sortEmpTheirOrder(require, ymd, lstEmpId);

		assertThat(listData).extracting(d -> d).containsExactly("emp1", "emp2", "emp3");
	}

	/**
	 * require.並び替え設定を取得する() is not empty case スケジュールチーム: [prv-2] スケジュールチームで社員を並び替える
	 * require.所属スケジュールチームを取得する($社員IDリスト) is empty
	 * 
	 */
	@Test
	public void testSortEmpTheirOrder_team1() {
		GeneralDate ymd = GeneralDate.today();
		List<String> lstEmpId = Arrays.asList("emp1", "emp2", "emp3");
		List<OrderedList> listOrderedList = Arrays.asList(new OrderedList(SortType.SCHEDULE_TEAM, SortOrder.SORT_ASC));
		SortSetting sortSetting = new SortSetting("cid", listOrderedList);
		new Expectations() {
			{
				require.get();
				result = Optional.of(sortSetting);

				require.get(lstEmpId);
			}
		};

		List<String> listData = SortEmpService.sortEmpTheirOrder(require, ymd, lstEmpId);

		assertThat(listData).extracting(d -> d).containsExactly("emp1", "emp2", "emp3");
	}

	/**
	 * require.並び替え設定を取得する() is not empty case スケジュールチーム: [prv-2] スケジュールチームで社員を並び替える
	 * require.所属スケジュールチームを取得する($社員IDリスト) not empty
	 */
	@Test
	public void testSortEmpTheirOrder_team2() {
		GeneralDate ymd = GeneralDate.today();
		List<String> lstEmpId = Arrays.asList("emp1", "emp2", "emp3", "emp4");
		List<OrderedList> listOrderedList = Arrays.asList(new OrderedList(SortType.SCHEDULE_TEAM, SortOrder.SORT_ASC));
		SortSetting sortSetting = new SortSetting("cid", listOrderedList);
		List<BelongScheduleTeam> listBelongScheduleTeam = Arrays.asList(
				new BelongScheduleTeam("emp1", "wkp1", new ScheduleTeamCd("S2")),
				new BelongScheduleTeam("emp2", "wkp2", new ScheduleTeamCd("S1")),
				new BelongScheduleTeam("emp3", "wkp3", new ScheduleTeamCd("S3")),
				new BelongScheduleTeam("emp4", "wkp4", null));

		new Expectations() {
			{
				require.get();
				result = Optional.of(sortSetting);

				require.get(lstEmpId);
				result = listBelongScheduleTeam;
			}
		};

		List<String> listData = SortEmpService.sortEmpTheirOrder(require, ymd, lstEmpId);

		assertThat(listData).extracting(d -> d).containsExactly("emp2", "emp1", "emp3", "emp4");
	}

	/**
	 * require.並び替え設定を取得する() is not empty case ランク: [prv-3] ランクで社員を並び替える(require,
	 * 社員IDリスト, $リストのリスト) require.ランクの優先順を取得する() is empty
	 */
	@Test
	public void testSortEmpTheirOrder_rank1() {
		GeneralDate ymd = GeneralDate.today();
		List<String> lstEmpId = Arrays.asList("emp1", "emp2", "emp3");
		List<OrderedList> listOrderedList = Arrays.asList(new OrderedList(SortType.RANK, SortOrder.SORT_ASC));
		SortSetting sortSetting = new SortSetting("cid", listOrderedList);

		new Expectations() {
			{
				require.get();
				result = Optional.of(sortSetting);

				require.getRankPriority();
			}
		};

		List<String> listData = SortEmpService.sortEmpTheirOrder(require, ymd, lstEmpId);

		assertThat(listData).extracting(d -> d).containsExactly("emp1", "emp2", "emp3");
	}

	/**
	 * require.並び替え設定を取得する() is not empty case ランク: [prv-3] ランクで社員を並び替える(require,
	 * 社員IDリスト, $リストのリスト) require.ランクの優先順を取得する() is not empty
	 * require.社員ランクを取得する(社員IDリスト) is empty
	 */
	@Test
	public void testSortEmpTheirOrder_rank2() {
		GeneralDate ymd = GeneralDate.today();
		List<String> lstEmpId = Arrays.asList("emp1", "emp2", "emp3");
		List<OrderedList> listOrderedList = Arrays.asList(new OrderedList(SortType.RANK, SortOrder.SORT_ASC));
		SortSetting sortSetting = new SortSetting("cid", listOrderedList);
		RankPriority rankPriority = new RankPriority("000000000000-0001", // dummy
				new ArrayList<RankCode>(Arrays.asList(new RankCode("R1"), new RankCode("R2"), new RankCode("R3"))));
		new Expectations() {
			{
				require.get();
				result = Optional.of(sortSetting);

				require.getRankPriority();
				result = Optional.of(rankPriority);

				require.getAll(lstEmpId);
			}
		};

		List<String> listData = SortEmpService.sortEmpTheirOrder(require, ymd, lstEmpId);

		assertThat(listData).extracting(d -> d).containsExactly("emp1", "emp2", "emp3");
	}

	/**
	 * require.並び替え設定を取得する() is not empty case ランク: [prv-3] ランクで社員を並び替える(require,
	 * 社員IDリスト, $リストのリスト) require.ランクの優先順を取得する() is not empty
	 * require.社員ランクを取得する(社員IDリスト) is not empty
	 */
	@Test
	public void testSortEmpTheirOrder_rank3() {
		GeneralDate ymd = GeneralDate.today();
		List<String> lstEmpId = Arrays.asList("emp1", "emp2", "emp3");
		List<OrderedList> listOrderedList = Arrays.asList(new OrderedList(SortType.RANK, SortOrder.SORT_ASC));
		SortSetting sortSetting = new SortSetting("cid", listOrderedList);
		RankPriority rankPriority = new RankPriority("000000000000-0001", // dummy
				new ArrayList<RankCode>(Arrays.asList(new RankCode("R1"), new RankCode("R2"), new RankCode("R3"))));
		List<EmployeeRank> listEmployeeRank = Arrays.asList(
				new EmployeeRank("emp1", new RankCode("R1")),
				new EmployeeRank("emp3", new RankCode("R3")),
				new EmployeeRank("emp2", new RankCode("R2")),
				new EmployeeRank("emp3", new RankCode("R6")),
				new EmployeeRank("emp2", null));
		new Expectations() {
			{
				require.get();
				result = Optional.of(sortSetting);

				require.getRankPriority();
				result = Optional.of(rankPriority);

				require.getAll(lstEmpId);
				result = listEmployeeRank;
			}
		};

		List<String> listData = SortEmpService.sortEmpTheirOrder(require, ymd, lstEmpId);

		assertThat(listData).extracting(d -> d).containsExactly("emp1", "emp2", "emp3");
	}

	/**
	 * require.並び替え設定を取得する() is not empty case 免許区分: [prv-4] 免許区分で社員を並び替える
	 * DS.社員の免許区分を取得する.取得する(基準日, 社員IDリスト) is empty
	 *
	 */
	@Test
	public void testSortEmpTheirOrder_category1() {
		GeneralDate ymd = GeneralDate.today();
		List<String> lstEmpId = Arrays.asList("emp1", "emp2", "emp3");
		List<OrderedList> listOrderedList = Arrays.asList(new OrderedList(SortType.LISENCE_ATR, SortOrder.SORT_ASC));
		SortSetting sortSetting = new SortSetting("cid", listOrderedList);
		new Expectations() {
			{
				require.get();
				result = Optional.of(sortSetting);
			}
		};
		new MockUp<GetEmpLicenseClassificationService>() {
			@Mock
			public List<EmpLicenseClassification> get(GetEmpLicenseClassificationService.Require require,
					GeneralDate generalDate, List<String> listEmp) {
				return new ArrayList<>();
			}
		};
		List<String> listData = SortEmpService.sortEmpTheirOrder(require, ymd, lstEmpId);

		assertThat(listData).extracting(d -> d).containsExactly("emp1", "emp2", "emp3");
	}

	/**
	 * require.並び替え設定を取得する() is not empty case 免許区分: [prv-4] 免許区分で社員を並び替える
	 * DS.社員の免許区分を取得する.取得する(基準日, 社員IDリスト) is not empty
	 *
	 */
	@Test
	public void testSortEmpTheirOrder_category2() {
		GeneralDate ymd = GeneralDate.today();
		List<String> lstEmpId = Arrays.asList("emp1", "emp2", "emp3");
		List<OrderedList> listOrderedList = Arrays.asList(new OrderedList(SortType.LISENCE_ATR, SortOrder.SORT_ASC));
		SortSetting sortSetting = new SortSetting("cid", listOrderedList);
		new Expectations() {
			{
				require.get();
				result = Optional.of(sortSetting);
			}
		};
		List<EmpLicenseClassification> lstEmpLicense = lstEmpId.stream()
				.map(x -> new EmpLicenseClassification(x,
						Optional.of(LicenseClassification.valueOf(x.equals("emp1") ? LicenseClassification.NURSE.value : LicenseClassification.NURSE_ASSIST.value))))
				.collect(Collectors.toList());
		new MockUp<GetEmpLicenseClassificationService>() {
			@Mock
			public List<EmpLicenseClassification> get(GetEmpLicenseClassificationService.Require require,
					GeneralDate generalDate, List<String> listEmp) {
				return lstEmpLicense;
			}
		};
		List<String> listData = SortEmpService.sortEmpTheirOrder(require, ymd, lstEmpId);

		assertThat(listData).extracting(d -> d).containsExactly("emp1", "emp2", "emp3");
	}

	/**
	 * require.並び替え設定を取得する() is not empty case 職位: [prv-5] 職位で社員を並び替える
	 * 
	 * require.社員の職位を取得する(基準日, 社員IDリスト) is empty require.会社の職位を取得する(基準日) is empty
	 */
	@Test
	public void testSortEmpTheirOrder_position1() {
		GeneralDate ymd = GeneralDate.today();
		List<String> lstEmpId = Arrays.asList("emp1", "emp2", "emp3");
		List<OrderedList> listOrderedList = Arrays.asList(new OrderedList(SortType.POSITION, SortOrder.SORT_ASC));
		SortSetting sortSetting = new SortSetting("cid", listOrderedList);
		new Expectations() {
			{
				require.get();
				result = Optional.of(sortSetting);

				require.getPositionEmp(ymd, lstEmpId);

				require.getCompanyPosition(ymd);
			}
		};
		List<String> listData = SortEmpService.sortEmpTheirOrder(require, ymd, lstEmpId);

		assertThat(listData).extracting(d -> d).containsExactly("emp1", "emp2", "emp3");
	}

	/**
	 * require.並び替え設定を取得する() is not empty case 職位: [prv-5] 職位で社員を並び替える
	 * 
	 * require.社員の職位を取得する(基準日, 社員IDリスト) is not empty require.会社の職位を取得する(基準日) is
	 * empty
	 */
	@Test
	public void testSortEmpTheirOrder_position2() {
		GeneralDate ymd = GeneralDate.today();
		List<String> lstEmpId = Arrays.asList("emp1", "emp2", "emp3");
		List<OrderedList> listOrderedList = Arrays.asList(new OrderedList(SortType.POSITION, SortOrder.SORT_ASC));
		SortSetting sortSetting = new SortSetting("cid", listOrderedList);
		List<EmployeePosition> listEmployeePosition = Arrays.asList(new EmployeePosition("emp1", "job2","jobtitleCode"),
				new EmployeePosition("emp2", "job1","jobtitleCode"));
		new Expectations() {
			{
				require.get();
				result = Optional.of(sortSetting);

				require.getPositionEmp(ymd, lstEmpId);
				result = listEmployeePosition;

				require.getCompanyPosition(ymd);
			}
		};
		List<String> listData = SortEmpService.sortEmpTheirOrder(require, ymd, lstEmpId);

		assertFalse(listData.isEmpty());
		assertThat(listData).extracting(d -> d).containsExactly("emp1", "emp2", "emp3");
	}

	/**
	 * require.並び替え設定を取得する() is not empty case 職位: [prv-5] 職位で社員を並び替える
	 * 
	 * require.社員の職位を取得する(基準日, 社員IDリスト) is empty require.会社の職位を取得する(基準日) is not
	 * empty
	 */
	@Test
	public void testSortEmpTheirOrder_position3() {
		GeneralDate ymd = GeneralDate.today();
		List<String> lstEmpId = Arrays.asList("emp1", "emp2", "emp3");
		List<OrderedList> listOrderedList = Arrays.asList(new OrderedList(SortType.POSITION, SortOrder.SORT_ASC));
		SortSetting sortSetting = new SortSetting("cid", listOrderedList);

		List<PositionImport> listPositionImport = Arrays.asList(
				new PositionImport("job0", "jobCd0", "jobName0"),
				new PositionImport("job3", "jobCd3", "jobName3"),
				new PositionImport("job4", "jobCd4", "jobName4"));
		new Expectations() {
			{
				require.get();
				result = Optional.of(sortSetting);

				require.getPositionEmp(ymd, lstEmpId);

				require.getCompanyPosition(ymd);
				result = listPositionImport;
			}
		};
		List<String> listData = SortEmpService.sortEmpTheirOrder(require, ymd, lstEmpId);

		assertThat(listData).extracting(d -> d).containsExactly("emp1", "emp2", "emp3");
	}

	/**
	 * require.並び替え設定を取得する() is not empty case 職位: [prv-5] 職位で社員を並び替える
	 * 
	 * require.社員の職位を取得する(基準日, 社員IDリスト) is not empty require.会社の職位を取得する(基準日) is not
	 * empty
	 */
	@Test
	public void testSortEmpTheirOrder_position4() {
		GeneralDate ymd = GeneralDate.today();
		List<String> lstEmpId = Arrays.asList("emp1", "emp2", "emp3", "emp4");
		List<OrderedList> listOrderedList = Arrays.asList(new OrderedList(SortType.POSITION, SortOrder.SORT_ASC));
		SortSetting sortSetting = new SortSetting("cid", listOrderedList);
		List<EmployeePosition> listEmployeePosition = Arrays.asList(new EmployeePosition("emp3", "job2","jobName2"),
				new EmployeePosition("emp2", "job1","jobtitleCode"), new EmployeePosition("emp1", "job3","jobtitleCode"));
		List<PositionImport> listPositionImport = Arrays.asList(
				new PositionImport("job0", "jobCd0", "jobName0"),
				new PositionImport("job2", "jobCd2", "jobName1"),
				new PositionImport("job1", "jobCd1", "jobName2"),
				new PositionImport("job3", "jobtitleCode", "jobName3"));
		new Expectations() {
			{
				require.get();
				result = Optional.of(sortSetting);

				require.getPositionEmp(ymd, lstEmpId);
				result = listEmployeePosition;

				require.getCompanyPosition(ymd);
				result = listPositionImport;
			}
		};
		List<String> listData = SortEmpService.sortEmpTheirOrder(require, ymd, lstEmpId);

		assertThat(listData).extracting(d -> d).containsExactly("emp1", "emp2", "emp3",  "emp4");
	}

	/**
	 * require.並び替え設定を取得する() is not empty case 分類: [prv-6] 分類で社員を並び替える
	 * 
	 * require.社員の分類を取得する(年月日, 社員IDリスト) is empty
	 */
	@Test
	public void testSortEmpTheirOrder_classification1() {
		GeneralDate ymd = GeneralDate.today();
		List<String> lstEmpId = Arrays.asList("emp1", "emp2", "emp3");
		List<OrderedList> listOrderedList = Arrays.asList(new OrderedList(SortType.CLASSIFY, SortOrder.SORT_ASC));
		SortSetting sortSetting = new SortSetting("cid", listOrderedList);

		new Expectations() {
			{
				require.get();
				result = Optional.of(sortSetting);

				require.getEmpClassifications(ymd, lstEmpId);
			}
		};
		List<String> listData = SortEmpService.sortEmpTheirOrder(require, ymd, lstEmpId);

		assertThat(listData).extracting(d -> d).containsExactly("emp1", "emp2", "emp3");
	}

	/**
	 * require.並び替え設定を取得する() is not empty case 分類: [prv-6] 分類で社員を並び替える
	 * 
	 * require.社員の分類を取得する(年月日, 社員IDリスト) is not empty
	 */
	@Test
	public void testSortEmpTheirOrder_classification2() {
		GeneralDate ymd = GeneralDate.today();
		List<String> lstEmpId = Arrays.asList("emp1", "emp2", "emp3");
		List<OrderedList> listOrderedList = Arrays.asList(new OrderedList(SortType.CLASSIFY, SortOrder.SORT_ASC));
		SortSetting sortSetting = new SortSetting("cid", listOrderedList);
		List<EmpClassifiImport> listEmpClassifiImport = Arrays.asList(new EmpClassifiImport("emp3", "class1"),
				new EmpClassifiImport("emp2", "class2"), new EmpClassifiImport("emp1", "class3"));
		new Expectations() {
			{
				require.get();
				result = Optional.of(sortSetting);

				require.getEmpClassifications(ymd, lstEmpId);
				result = listEmpClassifiImport;
			}
		};
		List<String> listData = SortEmpService.sortEmpTheirOrder(require, ymd, lstEmpId);

		assertThat(listData).extracting(d -> d).containsExactly("emp3", "emp2", "emp1");
	}

	/**
	 * case スケジュールチーム: [prv-2] スケジュールチームで社員を並び替える require.所属スケジュールチームを取得する($社員IDリスト)
	 * is empty
	 */
	@Test
	public void testSortBySpecSortingOrder_team3() {
		GeneralDate ymd = GeneralDate.today();
		List<String> lstEmpId = Arrays.asList("emp1", "emp2", "emp3");
		List<OrderedList> listOrderedList = Arrays.asList(new OrderedList(SortType.RANK, SortOrder.SORT_ASC),new OrderedList(SortType.SCHEDULE_TEAM, SortOrder.SORT_ASC));
		SortSetting sortSetting = new SortSetting("cid", listOrderedList);
		new Expectations() {
			{
				require.getRankPriority();
			}
		};

		List<String> listData = SortEmpService.sortBySpecSortingOrder(require, ymd, lstEmpId, sortSetting);

		assertThat(listData).extracting(d -> d).containsExactly("emp1", "emp2", "emp3");
	}
	
	/**
	 * sortPriorities.size() == 0
	 */
	@Test
	public void testRearranges_1() {
		GeneralDate ymd = GeneralDate.today();
		List<String> lstEmpId = Arrays.asList("emp1", "emp2", "emp3");
		List<OrderedList> listOrderedList = new ArrayList<>();
		SortSetting sortSetting = new SortSetting("cid", listOrderedList);

		List<String> listData = SortEmpService.sortBySpecSortingOrder(require, ymd, lstEmpId, sortSetting);

		assertThat(listData).extracting(d -> d).containsExactly("emp1", "emp2", "emp3");
	}
}
