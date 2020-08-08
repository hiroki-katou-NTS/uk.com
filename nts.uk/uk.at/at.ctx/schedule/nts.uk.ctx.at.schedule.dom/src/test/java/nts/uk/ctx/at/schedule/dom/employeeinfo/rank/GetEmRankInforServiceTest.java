package nts.uk.ctx.at.schedule.dom.employeeinfo.rank;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
		List<String> listIdNull = Arrays.asList("001","002");
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
		lstRank.addAll(listIdNull.stream().map(mapper-> new Rank(mapper, new RankCode(mapper), new RankSymbol(mapper))).collect(Collectors.toList()));
		new Expectations() {
			{
				require.getListRank();// dummy
				result = lstRank;
			}
		};
		
		List<EmpRankInfor> result =  GetEmRankInforService.get(require, listEmpId);
		assertThat(result).extracting(x-> x.getEmpId(), x-> x.getRankCode(),
				x-> x.getRankSymbol())
		.containsExactly(tuple("003",null,null), tuple("004",null,null));
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
		lstRank.addAll(listEmpId.stream().map(mapper-> new Rank(mapper, new RankCode(mapper), new RankSymbol(mapper))).collect(Collectors.toList()));
		new Expectations() {
			{
				require.getListRank();// dummy
				result = lstRank;
			}
		};

		List<EmpRankInfor> result  = GetEmRankInforService.get(require, listEmpId);
		assertThat(result).extracting(x-> x.getEmpId(), x-> x.getRankCode(),
				x-> x.getRankSymbol())
		.containsExactly(tuple("11",new RankCode("11"), new RankSymbol("11")), tuple("12",new RankCode("12"), new RankSymbol("12")));
	}
	
	@Test
	public void testEmployeeRankNotNull_RankNull() {
		List<String> listEmpId = Arrays.asList("001","002");
		List<String> listIdNull = Arrays.asList("0011","0012");
		
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
		lstRank.addAll(listIdNull.stream().map(mapper-> new Rank(mapper, new RankCode(mapper), new RankSymbol(mapper))).collect(Collectors.toList()));
		new Expectations() {
			{
				require.getListRank();// dummy
				result = lstRank;
			}
		};
		List<EmpRankInfor> result = GetEmRankInforService.get(require, listEmpId);
		assertThat(result).extracting(x-> x.getEmpId(), x-> x.getRankCode(),
				x-> x.getRankSymbol())
		.containsExactly(tuple("001",null,null), tuple("002",null,null));
	}
}
