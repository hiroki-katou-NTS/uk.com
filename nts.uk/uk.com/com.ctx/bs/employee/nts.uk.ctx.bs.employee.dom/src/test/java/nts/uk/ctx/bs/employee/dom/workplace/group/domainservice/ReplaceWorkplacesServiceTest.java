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
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.bs.employee.dom.workplace.group.AffWorkplaceGroup;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroup;
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

	@Test
	public void testRep() {
		WorkplaceGroup group = DomainServiceHelper.Helper.DUMMY;
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
		WorkplaceGroup group = DomainServiceHelper.Helper.DUMMY;
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
			Optional<AtomTask> atomTakss = Optional.of(AtomTask.of(() -> {
			new Expectations() {
				{
					require.deleteByWKPID("000000000000000000000000000000000013");
				}
			};
			}));
			dateHistLst.put(wKPID.getWKPID(), WorkplaceReplaceResult.delete(atomTakss.get()));
		});
		Optional<AffWorkplaceGroup> affInfo = lstFormerAffInfo2.stream()
				.filter(predicate -> predicate.getWKPGRPID().equals("000000000000000000000000000000000013"))
				.findFirst();
		assertThat(affInfo.isPresent()).isFalse();
	}

	@Test
	public void testAdd() {
		WorkplaceGroup group = DomainServiceHelper.Helper.DUMMY;
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
			Optional<AtomTask> atomTakss = Optional.of(AtomTask.of(() -> {}));
			dateHistLst.put("000000000000000000000000000000000013", WorkplaceReplaceResult.delete(atomTakss.get()));
		});

		List<String> resultProcessData = dateHistLst.entrySet().stream().map(x -> (String) x.getKey())
				.collect(Collectors.toList());
		String affInfo = resultProcessData.stream()
				.filter(predicate -> predicate.equals("000000000000000000000000000000000013")).findFirst().get();
		assertThat(affInfo.equals("000000000000000000000000000000000013")).isTrue();
	}

	@Test
	public void testAddWorkplace() {
		WorkplaceGroup group = DomainServiceHelper.Helper.DUMMY;
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
			Optional<AtomTask> atomTakss = Optional.of(AtomTask.of(() -> {}));
			WorkplaceReplaceResult workplaceReplaceResult = AddWplOfWorkGrpService.addWorkplace(require,
					DomainServiceHelper.Helper.DUMMY, wKPID);
			dateHistLst.put("000000000000000000000000000000000013", workplaceReplaceResult.add(atomTakss.get()));
		});

		List<String> resultProcessData = dateHistLst.entrySet().stream().map(x -> (String) x.getKey())
				.collect(Collectors.toList());
		String affInfo = resultProcessData.stream()
				.filter(predicate -> predicate.equals("000000000000000000000000000000000013")).findFirst().get();
		assertThat(affInfo.equals("000000000000000000000000000000000013")).isTrue();
	}

}
