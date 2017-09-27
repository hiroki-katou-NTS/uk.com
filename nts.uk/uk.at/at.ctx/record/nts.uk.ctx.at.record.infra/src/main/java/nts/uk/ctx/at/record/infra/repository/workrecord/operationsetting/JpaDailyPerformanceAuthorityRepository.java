/**
 * 
 */
package nts.uk.ctx.at.record.infra.repository.workrecord.operationsetting;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.authormanage.DailyPerformanceAuthority;
import nts.uk.ctx.at.record.dom.workrecord.authormanage.DailyPerformanceAuthorityRepoInterface;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcmtDaiPerformanceAut;

/**
 * @author danpv
 *
 */
@Stateless
public class JpaDailyPerformanceAuthorityRepository extends JpaRepository
		implements DailyPerformanceAuthorityRepoInterface {

	private final String GET_DAI_PER_AUTH_WITH_ROLE = "SELECT da FROM KrcmtDaiPerformanceAut WHERE da.pk.roleId =: roleId";

	@Override
	public List<DailyPerformanceAuthority> getDailyPerformanceAuthorities(String roleId) {
		List<KrcmtDaiPerformanceAut> entities = this.queryProxy()
				.query(GET_DAI_PER_AUTH_WITH_ROLE, KrcmtDaiPerformanceAut.class).getList();
		List<DailyPerformanceAuthority> results = new ArrayList<>();
		entities.forEach(ent -> {
			BigDecimal avaiBigDecimal = ent.availability;
			boolean availability = false;
			if (avaiBigDecimal.intValue() == 1) {
				availability = true;
			}
			results.add(new DailyPerformanceAuthority(roleId, ent.pk.functionNo, availability));
		});
		return results;
	}

}
