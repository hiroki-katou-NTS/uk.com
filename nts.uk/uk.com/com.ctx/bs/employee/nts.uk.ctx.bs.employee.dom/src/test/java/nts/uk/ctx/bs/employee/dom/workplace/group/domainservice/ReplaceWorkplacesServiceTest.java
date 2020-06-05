package nts.uk.ctx.bs.employee.dom.workplace.group.domainservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.enums.EnumAdaptor;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.bs.employee.dom.workplace.group.AffWorkplaceGroup;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroup;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroupCode;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroupName;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroupType;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceReplaceResult;
import nts.uk.ctx.bs.employee.dom.workplace.group.domainservice.ReplaceWorkplacesService.Require;

/**
 * 
 * @author phongtq
 *
 */
@RunWith(JMockit.class)
public class ReplaceWorkplacesServiceTest {
	@Injectable
	private Require require;
	
	AtomTask atomTakss = AtomTask.of(() -> {});

	@Test
	public void testRep() {
		WorkplaceGroup group = new WorkplaceGroup(
				"000000000000000000000000000000000011", // dummy
				"00000000000001", // dummy
				new WorkplaceGroupCode("0000000001"), // dummy
				new WorkplaceGroupName("00000000000000000011"), // dummy
				EnumAdaptor.valueOf(1, WorkplaceGroupType.class));// dummy
		List<String> lstWorkplaceId = DomainServiceHelper.getLstId();
		List<AffWorkplaceGroup> lstFormerAffInfo = DomainServiceHelper.getHelper();
		new Expectations() {
			{
				require.getByWKPGRPID("00000000000001");// dummy
				result = lstFormerAffInfo;
			}
		};
		Map<String, WorkplaceReplaceResult> workplacesService = ReplaceWorkplacesService.getWorkplace(require, group,
				lstWorkplaceId);
		workplacesService.forEach((WKPID, result) -> {
			result.getPersistenceProcess().get().run();
		});

		assertThat(workplacesService.isEmpty()).isFalse();
	}

	@Test
	public void testDelete() {
		WorkplaceGroup group = new WorkplaceGroup(
				"000000000000000000000000000000000011", // dummy
				"00000000000001", // dummy
				new WorkplaceGroupCode("0000000001"), // dummy
				new WorkplaceGroupName("00000000000000000011"), // dummy
				EnumAdaptor.valueOf(1, WorkplaceGroupType.class));// dummy
		List<String> lstWorkplaceId = DomainServiceHelper.getLstId();
		List<AffWorkplaceGroup> lstFormerAffInfo = DomainServiceHelper.getHelper();
		new Expectations() {
			{
				require.getByWKPGRPID("00000000000001");// dummy
				result = lstFormerAffInfo;
			}
		};
		List<AffWorkplaceGroup> lstDel = lstFormerAffInfo.stream().filter(x -> !lstWorkplaceId.contains(x.getWKPID()))
				.collect(Collectors.toList());
		Map<String, WorkplaceReplaceResult> dateHistLst = ReplaceWorkplacesService.getWorkplace(require, group,
				lstWorkplaceId);
		List<AffWorkplaceGroup> lstFormerAffInfo2 = DomainServiceHelper.getHelper2();
		lstDel.forEach(wKPID -> {
			require.deleteByWKPID("000000000000000000000000000000000013");
			dateHistLst.put(wKPID.getWKPID(), WorkplaceReplaceResult.delete(atomTakss));
		});
		Optional<AffWorkplaceGroup> affInfo = lstFormerAffInfo2.stream()
				.filter(predicate -> predicate.getWKPGRPID().equals("000000000000000000000000000000000013"))
				.findFirst();
		assertThat(affInfo.isPresent()).isFalse();
	}

	@Test
	public void testAdd() {
		WorkplaceGroup group = new WorkplaceGroup(
				"000000000000000000000000000000000011", // dummy
				"00000000000001", // dummy
				new WorkplaceGroupCode("0000000001"), // dummy
				new WorkplaceGroupName("00000000000000000011"), // dummy
				EnumAdaptor.valueOf(1, WorkplaceGroupType.class));// dummy
		List<String> lstWorkplaceId = DomainServiceHelper.getLstId();
		List<AffWorkplaceGroup> lstFormerAffInfo = DomainServiceHelper.getHelper2();
		new Expectations() {
			{
				require.getByWKPGRPID("00000000000001");// dummy
				result = lstFormerAffInfo;
			}
		};
		List<AffWorkplaceGroup> lstDel = lstFormerAffInfo.stream().filter(x -> !lstWorkplaceId.contains(x.getWKPID()))
				.collect(Collectors.toList());
		Map<String, WorkplaceReplaceResult> dateHistLst = ReplaceWorkplacesService.getWorkplace(require, group,
				lstWorkplaceId);
		lstDel.forEach(wKPID -> {
			dateHistLst.put("000000000000000000000000000000000013", WorkplaceReplaceResult.delete(atomTakss));
		});

		List<String> resultProcessData = dateHistLst.entrySet().stream().map(x -> (String) x.getKey())
				.collect(Collectors.toList());
		String affInfo = resultProcessData.stream()
				.filter(predicate -> predicate.equals("000000000000000000000000000000000013")).findFirst().get();
		assertThat(affInfo.equals("000000000000000000000000000000000013")).isTrue();
	}

	@Test
	public void testAddWorkplace() {
		WorkplaceGroup group = new WorkplaceGroup(
				"000000000000000000000000000000000011", // dummy
				"00000000000001", // dummy
				new WorkplaceGroupCode("0000000001"), // dummy
				new WorkplaceGroupName("00000000000000000011"), // dummy
				EnumAdaptor.valueOf(1, WorkplaceGroupType.class));// dummy
		List<String> lstWorkplaceId = DomainServiceHelper.getLstId();
		List<AffWorkplaceGroup> lstFormerAffInfo = DomainServiceHelper.getHelper2();
		new Expectations() {
			{
				require.getByWKPGRPID("00000000000001");// dummy
				result = lstFormerAffInfo;
			}
		};

		Map<String, WorkplaceReplaceResult> dateHistLst = ReplaceWorkplacesService.getWorkplace(require, group,
				lstWorkplaceId);
		lstWorkplaceId.forEach(wKPID -> {
			WorkplaceReplaceResult workplaceReplaceResult = AddWplOfWorkGrpService.addWorkplace(require,
					DomainServiceHelper.Helper.DUMMY, wKPID);
			dateHistLst.put("000000000000000000000000000000000013", workplaceReplaceResult);
		});

		List<String> resultProcessData = dateHistLst.entrySet().stream().map(x -> (String) x.getKey())
				.collect(Collectors.toList());
		String affInfo = resultProcessData.stream()
				.filter(predicate -> predicate.equals("000000000000000000000000000000000013")).findFirst().get();
		assertThat(affInfo.equals("000000000000000000000000000000000013")).isTrue();
	}

}
