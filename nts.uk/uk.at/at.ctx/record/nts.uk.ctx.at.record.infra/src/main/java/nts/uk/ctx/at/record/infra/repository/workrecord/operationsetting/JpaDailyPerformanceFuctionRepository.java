/**
 * 
 */
package nts.uk.ctx.at.record.infra.repository.workrecord.operationsetting;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.authormanage.DailyPerformFuncRepo;
import nts.uk.ctx.at.record.dom.workrecord.authormanage.DailyPerformanceFunction;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcmtDaiPerformanceFun;

/**
 * @author danpv
 *
 */
@Stateless
public class JpaDailyPerformanceFuctionRepository extends JpaRepository
		implements DailyPerformFuncRepo {

	private static final String GET_ALL_DAI_PER_FUNC = "SELECT df FROM KrcmtDaiPerformanceFun df ORDER BY df.displayOrder";

	@Override
	public List<DailyPerformanceFunction> getAll() {
		List<KrcmtDaiPerformanceFun> entities = this.queryProxy()
				.query(GET_ALL_DAI_PER_FUNC, KrcmtDaiPerformanceFun.class).getList();
		List<DailyPerformanceFunction> results = new ArrayList<>();
		entities.forEach(ent -> results.add(
				new DailyPerformanceFunction(ent.functionNo, ent.displayNameOfFunction, ent.descriptionOfFunction, ent.displayOrder, ent.initValue  == 1 ? true : false)));
		return results;
	}

}
