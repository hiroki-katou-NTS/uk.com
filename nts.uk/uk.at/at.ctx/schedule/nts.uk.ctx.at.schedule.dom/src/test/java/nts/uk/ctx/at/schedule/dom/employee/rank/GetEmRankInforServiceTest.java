package nts.uk.ctx.at.schedule.dom.employee.rank;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmpRankInfor;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRank;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.GetEmRankInforService;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.GetEmRankInforService.Require;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.Rank;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankCode;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankSymbol;

@RunWith(JMockit.class)
public class GetEmRankInforServiceTest {
	
	@Injectable
	Require require;
	
	@Test
	public void testGetEmRank_MakeWithoutRank() {
		
		
		List<String> listEmpId = Arrays.asList("003","004"); // dummy
		List<String> listIdNull = Arrays.asList("001","002");
		List<EmployeeRank> listEmpRank = new ArrayList<EmployeeRank>();
		listEmpRank.addAll(listIdNull.stream().map(mapper-> new EmployeeRank(mapper, new RankCode(mapper)))
				.collect(Collectors.toList()));
		
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
		assertNull(result.get(0).getRankCode());
		assertNull(result.get(1).getRankCode());
		assertNull(result.get(0).getRankSymbol());
		assertNull(result.get(1).getRankSymbol());
	}
	
	@Test
	public void testEmployeeRankNotNull_RankNotNull() {
		List<String> listEmpId = Arrays.asList("001","002"); // dummy
		List<EmployeeRank> listEmpRank = new ArrayList<EmployeeRank>();
		listEmpRank.addAll(listEmpId.stream().map(mapper-> new EmployeeRank(mapper, new RankCode(mapper)))
				.collect(Collectors.toList()));
		
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
		assertThat(result.get(0).getRankCode().v().equals("001")).isTrue();
		assertThat(result.get(1).getRankCode().v().equals("002")).isTrue();
		assertThat(result.get(0).getRankSymbol().v().equals("001")).isTrue();
		assertThat(result.get(1).getRankSymbol().v().equals("002")).isTrue();
	}
	
	@Test
	public void testEmployeeRankNotNull_RankNull() {
		List<String> listEmpId = Arrays.asList("001","002"); // dummy
		List<String> listIdNull = Arrays.asList("0011","0012");
		List<EmployeeRank> listEmpRank = new ArrayList<EmployeeRank>();
		listEmpRank.addAll(listEmpId.stream().map(mapper-> new EmployeeRank(mapper, new RankCode(mapper)))
				.collect(Collectors.toList()));
		
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
		assertNull(result.get(0).getRankCode());
		assertNull(result.get(1).getRankCode());
		assertNull(result.get(0).getRankSymbol());
		assertNull(result.get(1).getRankSymbol());
	}
}
