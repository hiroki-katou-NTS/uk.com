package nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.adapter.jobtitle.PositionImport;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.SortSetting.Require;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.EmpLicenseClassification;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.GetEmpLicenseClassificationService;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.LicenseClassification;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRank;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankCode;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankPriority;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamCd;
@RunWith(JMockit.class)
public class SortSettingTest {
	@Injectable
	private Require require;
	
	@Test
	public void getters() {
		SortSetting sortSetting = new SortSetting("cid", Arrays.asList(new OrderedList(SortType.CLASSIFY, SortOrder.SORT_ASC)));
		NtsAssert.invokeGetters(sortSetting);
	}

	/**
	 * inv-1: @並び替え優先順.種類は重複しないこと
	 */
	@Test
	public void check_inv1_duplicate() {
		List<OrderedList> orderedLists = Arrays.asList(
				  new OrderedList(SortType.CLASSIFY, SortOrder.SORT_ASC)
				, new OrderedList(SortType.CLASSIFY, SortOrder.SORT_ASC)
				);
		
		NtsAssert.businessException("Msg_1612", () -> SortSetting.create("cid", orderedLists));
	}
	
	/**
	 * inv-2: 1 <= @並び替え優先順.Size <= 5
	 * case @並び替え優先順.size = 0
	 */
	@Test
	public void check_inv2_emptyList() {
		NtsAssert.businessException("Msg_1613",
				() -> SortSetting.create("cid", Collections.emptyList()));
	}
	
	/**
	 * inv-2: 1 <= @並び替え優先順.Size <= 5
	 * @並び替え優先順.size = 6
	 */
	@Test
	public void check_inv2_after5() {
		List<OrderedList> orderedLists = Arrays.asList(
				  new OrderedList(SortType.CLASSIFY, SortOrder.SORT_ASC)
				, new OrderedList(SortType.POSITION, SortOrder.SORT_ASC)
				, new OrderedList(SortType.LISENCE_ATR, SortOrder.SORT_ASC)
				, new OrderedList(SortType.RANK, SortOrder.SORT_ASC)
				, new OrderedList(SortType.SCHEDULE_TEAM, SortOrder.SORT_ASC)
				, new OrderedList(SortType.SCHEDULE_TEAM, SortOrder.SORT_DESC)
				);
		
		NtsAssert.businessException("Msg_1613",
				() -> SortSetting.create("cid",orderedLists));
	}
	
	/**
	 * 作る: 成功
	 * @並び替え優先順.size = 5
	 */
	@Test
	public void createSortSettinng_success() {
		val orderLists = Arrays.asList(
				  new OrderedList(SortType.CLASSIFY, SortOrder.SORT_ASC)
				, new OrderedList(SortType.POSITION, SortOrder.SORT_ASC)
				, new OrderedList(SortType.LISENCE_ATR, SortOrder.SORT_ASC)
				, new OrderedList(SortType.RANK, SortOrder.SORT_ASC)
				, new OrderedList(SortType.SCHEDULE_TEAM, SortOrder.SORT_ASC));
		val sortSetting = SortSetting.create("cid", orderLists);
		
		assertThat(sortSetting.getCompanyID()).isEqualTo("cid");
		assertThat(orderLists).extracting(d -> d.getSortOrder(), d -> d.getType())
				.containsExactly(
						  tuple(SortOrder.SORT_ASC, SortType.CLASSIFY)
						, tuple(SortOrder.SORT_ASC, SortType.POSITION)
						, tuple(SortOrder.SORT_ASC, SortType.LISENCE_ATR)
						, tuple(SortOrder.SORT_ASC, SortType.RANK)
						, tuple(SortOrder.SORT_ASC, SortType.SCHEDULE_TEAM));
		
	}
	
	/**
	 * 並び替え設定: team
	 * 
	 */
	@Test
	public void testSort_team() {
		val baseDate = GeneralDate.today();
		val sids = Arrays.asList("emp1", "emp2", "emp3", "emp4", "emp5", "emp6", "emp7", "emp8", "emp9", "emp10");
		val orderedLists = Arrays.asList(new OrderedList(SortType.SCHEDULE_TEAM, SortOrder.SORT_ASC));
		val sortSetting = new SortSetting("cid", orderedLists);
		
		val belongScheduleTeams = Arrays.asList(
				new BelongScheduleTeam("emp1", "wkp1", new ScheduleTeamCd("01")),
				new BelongScheduleTeam("emp2", "wkp2", new ScheduleTeamCd("02")),
				new BelongScheduleTeam("emp3", "wkp3", new ScheduleTeamCd("03")),
				new BelongScheduleTeam("emp4", "wkp3", new ScheduleTeamCd("01")),
				new BelongScheduleTeam("emp5", "wkp3", new ScheduleTeamCd("03")),
				// emp6 empty
				new BelongScheduleTeam("emp7", "wkp3", new ScheduleTeamCd("04")),
				// emp8 empty
				new BelongScheduleTeam("emp9", "wkp3", new ScheduleTeamCd("02")),
				new BelongScheduleTeam("emp10", "wkp3", new ScheduleTeamCd("03"))
				);

		new Expectations() {
			{
				require.getScheduleTeam(sids);
				result = belongScheduleTeams;
			}
		};

		List<String> listData = sortSetting.sort(require, baseDate, sids);

		assertThat(listData).extracting(d -> d)
							.containsExactly(
									"emp1", 
									"emp4", 
									"emp2",
									"emp9",
									"emp3",
									"emp5",
									"emp10",
									"emp7",
									"emp6",
									"emp8");
	}
	/**
	 * 並び替え設定: rank priority = empty
	 * 
	 */
	@Test
	public void testSort_testSort_rank_priority_empty() {
		val baseDate = GeneralDate.today();
		val sids = Arrays.asList("emp1", "emp2", "emp3", "emp4", "emp5", "emp6", "emp7", "emp8", "emp9", "emp10");
		val orderedLists = Arrays.asList(new OrderedList(SortType.RANK, SortOrder.SORT_ASC));
		val sortSetting = new SortSetting("cid", orderedLists);
		
		val listEmployeeRank = Arrays.asList(
				  new EmployeeRank("emp1", new RankCode("01"))
				, new EmployeeRank("emp2", new RankCode("02"))
				, new EmployeeRank("emp3", new RankCode("03"))
				, new EmployeeRank("emp4", new RankCode("01"))
				, new EmployeeRank("emp5", new RankCode("03"))
				// emp6 empty
				, new EmployeeRank("emp7", new RankCode("04"))
				// emp8 empty
				, new EmployeeRank("emp9", new RankCode("02"))
				, new EmployeeRank("emp10", new RankCode("05")));
		
		new Expectations() {
			{
				require.getRankPriorities();

				require.getEmployeeRanks(sids);
				result = listEmployeeRank;
			}
		};

		List<String> datas = sortSetting.sort(require, baseDate, sids);

		assertThat(datas).extracting(d -> d)
							.containsExactly(
									 "emp1" 
									,"emp2"
									,"emp3"
									,"emp4"
									,"emp5"
									,"emp6"
									,"emp7"
									,"emp8"
									,"emp9" 
									,"emp10");
	}
	
	/**
	 * 並び替え設定: rank priority not empty
	 * 
	 */
	@Test
	public void testSort_rank_priority_not_empty() {
		val baseDate = GeneralDate.today();
		val sids = Arrays.asList("emp1", "emp2", "emp3", "emp4", "emp5", "emp6", "emp7", "emp8", "emp9", "emp10");
		val orderedLists = Arrays.asList(new OrderedList(SortType.RANK, SortOrder.SORT_ASC));
		val sortSetting = new SortSetting("cid", orderedLists);
		
		val rankPriority = new RankPriority(
				"cid", 
				Arrays.asList(
						  new RankCode("02")
						, new RankCode("03") 
						, new RankCode("01")
						, new RankCode("04")));
		
		val listEmployeeRank = Arrays.asList(
				  new EmployeeRank("emp1", new RankCode("01"))
				, new EmployeeRank("emp2", new RankCode("02"))
				, new EmployeeRank("emp3", new RankCode("03"))
				, new EmployeeRank("emp4", new RankCode("01"))
				, new EmployeeRank("emp5", new RankCode("03"))
				// emp6 empty
				, new EmployeeRank("emp7", new RankCode("04"))
				// emp8 empty
				, new EmployeeRank("emp9", new RankCode("02"))
				, new EmployeeRank("emp10", new RankCode("05")));
		
		new Expectations() {
			{
				require.getRankPriorities();
				result = Optional.of(rankPriority);

				require.getEmployeeRanks(sids);
				result = listEmployeeRank;
			}
		};

		List<String> datas = sortSetting.sort(require, baseDate, sids);

		assertThat(datas).extracting(d -> d)
							.containsExactly(
									 "emp2" 
									,"emp9"
									,"emp3"
									,"emp5"
									,"emp1"
									,"emp4"
									,"emp7"
									,"emp6"
									,"emp8" 
									,"emp10");
	}

	/**
	 * 並び替え設定: lisence
	 * 
	 */
	@Test
	public void testSort_lisence() {
		val baseDate = GeneralDate.today();
		val sids = Arrays.asList("emp1", "emp2", "emp3", "emp4", "emp5", "emp6", "emp7", "emp8", "emp9", "emp10");
		val orderedLists = Arrays.asList(new OrderedList(SortType.LISENCE_ATR, SortOrder.SORT_ASC));
		val sortSetting = new SortSetting("cid", orderedLists);
		
		val empLicenses = 
				Arrays.asList(
						  new EmpLicenseClassification("emp1", Optional.of(LicenseClassification.NURSE))
						, new EmpLicenseClassification("emp2", Optional.of(LicenseClassification.NURSE_ASSOCIATE))
						, new EmpLicenseClassification("emp3", Optional.of(LicenseClassification.NURSE_ASSIST))
						, new EmpLicenseClassification("emp4", Optional.of(LicenseClassification.NURSE))
						, new EmpLicenseClassification("emp5", Optional.of(LicenseClassification.NURSE_ASSIST))
						, new EmpLicenseClassification("emp6", Optional.empty())
						, new EmpLicenseClassification("emp7", Optional.of(LicenseClassification.NURSE_ASSIST))
						, new EmpLicenseClassification("emp8", Optional.empty())
						, new EmpLicenseClassification("emp9", Optional.of(LicenseClassification.NURSE_ASSOCIATE))
						, new EmpLicenseClassification("emp10", Optional.of(LicenseClassification.NURSE))
						);
		
		new MockUp<GetEmpLicenseClassificationService>() {
			@Mock
			public List<EmpLicenseClassification> get(
					GetEmpLicenseClassificationService.Require require,
					GeneralDate generalDate, 
					List<String> listEmp) {
				
				return empLicenses;
			}
		};
		
		// ACT
		List<String> datas = sortSetting.sort(require, baseDate, sids);

		// ASSERT
		assertThat(datas).extracting(d -> d)
							.containsExactly(
									"emp1", 
									"emp4",
									"emp10", 
									"emp2", 
									"emp9", 
									"emp3", 
									"emp5", 
									"emp7", 
									"emp6", 
									"emp8");
	}
	
	/**
	 * 並び替え設定: position
	 * 
	 */
	@Test
	public void testSort_position() {
		val baseDate = GeneralDate.today();
		val sids = Arrays.asList("emp1", "emp2", "emp3", "emp4", "emp5", "emp6", "emp7", "emp8", "emp9", "emp10");
		val orderedLists = Arrays.asList(new OrderedList(SortType.POSITION, SortOrder.SORT_ASC));
		val sortSetting = new SortSetting("cid", orderedLists);
		
		val employeePositions = Arrays.asList(
				new EmployeePosition("emp1", "job-id1","01"),
				new EmployeePosition("emp2", "job-id2","02"),
				new EmployeePosition("emp3", "job-id3","03"),
				new EmployeePosition("emp4", "job-id1","01"),
				new EmployeePosition("emp5", "job-id5","05"),
				// emp6 empty
				new EmployeePosition("emp7", "job-id4","04"),
				// emp8 empty
				new EmployeePosition("emp9", "job-id2","02"),
				new EmployeePosition("emp10", "job-id3","03")
				);
		
		val positionImports = Arrays.asList(
				new PositionImport("job-id3", "03", "職位３"),
				new PositionImport("job-id1", "01", "職位１"),
				new PositionImport("job-id2", "02", "職位２"),
				new PositionImport("job-id4", "04", "職位４"));
		
		new Expectations() {
			{
				require.getPositionEmps(baseDate, sids);
				result = employeePositions;

				require.getCompanyPosition(baseDate);
				result = positionImports;
			}
		};
		
		List<String> result = sortSetting.sort(require, baseDate, sids);

		assertThat(result).extracting(d -> d).containsExactly(
				"emp3", 
				"emp10", 
				"emp1",
				"emp4",  
				"emp2",  
				"emp9",  
				"emp7",  
				"emp5",  
				"emp6",  
				"emp8");
	}
	
	/**
	 * 並び替え設定: classification
	 * 
	 */
	@Test
	public void testSort_classification() {
		val baseDate = GeneralDate.today();
		val sids = Arrays.asList("emp1", "emp2", "emp3", "emp4", "emp5", "emp6", "emp7", "emp8", "emp9", "emp10");
		val orderLists = Arrays.asList(new OrderedList(SortType.CLASSIFY, SortOrder.SORT_ASC));
		val sortSetting = new SortSetting("cid", orderLists);
		val empClassImports = Arrays.asList(
				new EmpClassifiImport("emp1", "A01"),
				new EmpClassifiImport("emp2", "A02"),
				new EmpClassifiImport("emp3", "A03"),
				new EmpClassifiImport("emp4", "A01"),
				new EmpClassifiImport("emp5", "A03"),
				// emp6 empty
				new EmpClassifiImport("emp7", "A04"),
				// emp8 empty
				new EmpClassifiImport("emp9", "A02"),
				new EmpClassifiImport("emp10", "A03"));
		
		new Expectations() {
			{
				require.getEmpClassifications(baseDate, sids);
				result = empClassImports;
			}
		};
		
		List<String> result = sortSetting.sort(require, baseDate, sids);

		assertThat(result).extracting(d -> d).containsExactly(
				"emp1", 
				"emp4",
				"emp2",
				"emp9",
				"emp3",
				"emp5",
				"emp10",
				"emp7",
				"emp6",
				"emp8");
	}

	@Test
	public void testSort_team_classification() {
		
		val baseDate = GeneralDate.today();
		val sids = Arrays.asList(
				 "emp1", "emp2", "emp3", "emp4", "emp5" 
				,"emp6", "emp7", "emp8", "emp9", "emp10"
				,"emp11", "emp12", "emp13", "emp14", "emp15", "emp16");
		
		val orderLists = Arrays.asList(
				  new OrderedList(SortType.SCHEDULE_TEAM, SortOrder.SORT_ASC)
				, new OrderedList(SortType.CLASSIFY, SortOrder.SORT_ASC));
		
		val sortSetting = new SortSetting("cid", orderLists);
		
		val belongScheduleTeams = Arrays.asList(
				new BelongScheduleTeam("emp1", "wkp1", new ScheduleTeamCd("01")),
				new BelongScheduleTeam("emp2", "wkp1", new ScheduleTeamCd("02")),
				new BelongScheduleTeam("emp3", "wkp1", new ScheduleTeamCd("03")),
				new BelongScheduleTeam("emp4", "wkp1", new ScheduleTeamCd("01")),
				new BelongScheduleTeam("emp5", "wkp1", new ScheduleTeamCd("03")),
				// emp6 empty
				new BelongScheduleTeam("emp7", "wkp1", new ScheduleTeamCd("04")),
				// emp8 empty
				new BelongScheduleTeam("emp9", "wkp1", new ScheduleTeamCd("02")),
				new BelongScheduleTeam("emp9", "wkp1", new ScheduleTeamCd("02")),
				new BelongScheduleTeam("emp10", "wkp1", new ScheduleTeamCd("03")),
				new BelongScheduleTeam("emp11", "wkp1", new ScheduleTeamCd("04")),
				new BelongScheduleTeam("emp12", "wkp1", new ScheduleTeamCd("01")),
				new BelongScheduleTeam("emp13", "wkp1", new ScheduleTeamCd("02")),
				new BelongScheduleTeam("emp14", "wkp1", new ScheduleTeamCd("04")),
				new BelongScheduleTeam("emp15", "wkp1", new ScheduleTeamCd("03")),
				new BelongScheduleTeam("emp16", "wkp1", new ScheduleTeamCd("02"))
				);
		
		List<EmpClassifiImport> empClassImports = Arrays.asList(
				new EmpClassifiImport("emp1", "A01"),
				new EmpClassifiImport("emp2", "A02"),
				new EmpClassifiImport("emp3", "A03"),
				new EmpClassifiImport("emp4", "A03"),
				new EmpClassifiImport("emp5", "A04"),
				// emp6 empty
				new EmpClassifiImport("emp7", "A04"),
				new EmpClassifiImport("emp8", "A04"),
				new EmpClassifiImport("emp9", "A01"),
				new EmpClassifiImport("emp10", "A03"),
				// emp11 empty
				new EmpClassifiImport("emp12", "A02"),
				new EmpClassifiImport("emp13", "A04"),
				new EmpClassifiImport("emp14", "A01"),
				new EmpClassifiImport("emp15", "A02"),
				new EmpClassifiImport("emp16", "A03"));
		
		new Expectations() {
			{
				require.getScheduleTeam(sids);
				result = belongScheduleTeams;
				
				require.getEmpClassifications(baseDate, sids);
				result = empClassImports;
			}
		};

		List<String> result = sortSetting.sort(require, baseDate, sids);

		assertThat(result).extracting(d -> d).containsExactly(
				"emp1", "emp12", "emp4", "emp9", "emp2",
				"emp16", "emp13", "emp15", "emp3", "emp10",
				"emp5", "emp14", "emp7", "emp11", "emp8", "emp6");
	}


}
