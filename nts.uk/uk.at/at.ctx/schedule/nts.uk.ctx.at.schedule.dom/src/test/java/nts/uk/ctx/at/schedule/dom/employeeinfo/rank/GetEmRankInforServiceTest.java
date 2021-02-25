package nts.uk.ctx.at.schedule.dom.employeeinfo.rank;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.GetEmRankInforService.Require;

@RunWith(JMockit.class)
public class GetEmRankInforServiceTest {
	
	@Injectable
	Require require;
	
	@Test
	public void testEmployeeRankNull() {
		List<String> listEmpId = Arrays.asList("003","004");
		List<EmployeeRank> listEmpRank = new ArrayList<EmployeeRank>();

		EmployeeRank rank1 = new EmployeeRank("001", new RankCode("001"));
		EmployeeRank rank2 = new EmployeeRank("002", new RankCode("002"));
		
		listEmpRank.add(rank1);
		listEmpRank.add(rank2);
		
		new Expectations() {
			{
				require.getAll(listEmpId);// dummy
				result = listEmpRank;
			}
		};
		
		
		List<Rank> lstRank = new ArrayList<Rank>();
		lstRank.add(new Rank("001", new RankCode("001"), new RankSymbol("001")));
		lstRank.add(new Rank("002", new RankCode("002"), new RankSymbol("002")));
		new Expectations() {
			{
				require.getListRank();// dummy
				result = lstRank;
			}
		};
		
		List<EmpRankInfor> result =  GetEmRankInforService.get(require, listEmpId);
		assertThat(result).extracting(x-> x.getEmpId(), x-> x.getRankCode(),
				x-> x.getRankSymbol())
		.containsExactly(
				tuple("003",Optional.empty(),Optional.empty()), 
				tuple("004",Optional.empty(),Optional.empty()));
		}

	
	@Test
	public void testEmployeeRankNotNull_RankNotNull() {
		List<String> listEmpId = Arrays.asList("11","12");
		List<EmployeeRank> listEmpRank = new ArrayList<EmployeeRank>();
		
		EmployeeRank rank1 = new EmployeeRank("11", new RankCode("11"));
		EmployeeRank rank2 = new EmployeeRank("12", new RankCode("12"));
		
		listEmpRank.add(rank1);
		listEmpRank.add(rank2);
		
		new Expectations() {
			{
				require.getAll(listEmpId);// dummy
				result = listEmpRank;
			}
		};
		
		
		List<Rank> lstRank = new ArrayList<Rank>();
		lstRank.add(new Rank("11", new RankCode("11"), new RankSymbol("11")));
		lstRank.add(new Rank("12", new RankCode("12"), new RankSymbol("12")));
		new Expectations() {
			{
				require.getListRank();// dummy
				result = lstRank;
			}
		};

		List<EmpRankInfor> result  = GetEmRankInforService.get(require, listEmpId);
		assertThat(result).extracting(x-> x.getEmpId(), x-> x.getRankCode(),
				x-> x.getRankSymbol())
		.containsExactly(
				tuple("11",Optional.of(new RankCode("11")), Optional.of(new RankSymbol("11"))), 
				tuple("12",Optional.of(new RankCode("12")), Optional.of(new RankSymbol("12"))));
	}
	
	@Test
	public void testEmployeeRankNotNull_RankNull() {
		List<String> listEmpId = Arrays.asList("001","002");
		
		EmployeeRank rank1 = new EmployeeRank("001", new RankCode("001"));
		EmployeeRank rank2 = new EmployeeRank("002", new RankCode("002"));
		
		List<EmployeeRank> listEmpRank = new ArrayList<EmployeeRank>();
		
		listEmpRank.add(rank1);
		listEmpRank.add(rank2);
		
		new Expectations() {
			{
				require.getAll(listEmpId);// dummy
				result = listEmpRank;
			}
		};
		
		
		List<Rank> lstRank = new ArrayList<Rank>();
		lstRank.add(new Rank("0011", new RankCode("0011"), new RankSymbol("0011")));
		lstRank.add(new Rank("0012", new RankCode("0012"), new RankSymbol("0012")));
		new Expectations() {
			{
				require.getListRank();// dummy
				result = lstRank;
			}
		};
		List<EmpRankInfor> result = GetEmRankInforService.get(require, listEmpId);
		assertThat(result).extracting(x-> x.getEmpId(), x-> x.getRankCode(),
				x-> x.getRankSymbol())
		.containsExactly(
				tuple("001",Optional.empty(),Optional.empty()), 
				tuple("002",Optional.empty(),Optional.empty()));
	}
}
