/**
 * 
 */
package nts.uk.ctx.at.record.infra.repository.workrecord.operationsetting.old;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.authormanage.DailyPerformFuncRepo;
import nts.uk.ctx.at.record.dom.workrecord.authormanage.DailyPerformanceFunction;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.old.KrcmtDaiPerformanceFunOld;

/**
 * @author danpv
 *
 */
@Stateless
public class JpaDailyPerformanceFuctionRepository extends JpaRepository
		implements DailyPerformFuncRepo {

	private final String GET_ALL_DAI_PER_FUNC = "SELECT df FROM KrcmtDaiPerformanceFun df ORDER BY df.functionNo";

	@Override
	public List<DailyPerformanceFunction> getAll() {
		List<KrcmtDaiPerformanceFunOld> entities = this.queryProxy()
				.query(GET_ALL_DAI_PER_FUNC, KrcmtDaiPerformanceFunOld.class).getList();
		List<DailyPerformanceFunction> results = new ArrayList<>();
		entities.forEach(ent -> results.add(
				new DailyPerformanceFunction(ent.functionNo, ent.displayNameOfFunction, ent.descriptionOfFunction)));
		return results;
	}

}
