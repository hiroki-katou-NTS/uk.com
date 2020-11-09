/**
 *
 */
package nts.uk.ctx.at.record.infra.repository.workrecord.operationsetting;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.authormanage.DailyPerformAuthorRepo;
import nts.uk.ctx.at.record.dom.workrecord.authormanage.DailyPerformanceAuthority;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcmtDaiPerformanceAut;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcmtDaiPerformanceAutPk;

/**
 * @author danpv
 *
 */
@Stateless
public class JpaDailyPerformanceAuthorityRepository extends JpaRepository
		implements DailyPerformAuthorRepo {

	private static final String GET_DAI_PER_AUTH_WITH_ROLE = "SELECT da FROM KrcmtDaiPerformanceAut da WHERE da.pk.roleId =:roleId";

	@Override
	public List<DailyPerformanceAuthority> get(String roleId) {
		List<KrcmtDaiPerformanceAut> entities = this.queryProxy()
				.query(GET_DAI_PER_AUTH_WITH_ROLE, KrcmtDaiPerformanceAut.class)
				.setParameter("roleId", roleId).getList();
		List<DailyPerformanceAuthority> results = new ArrayList<>();
		entities.forEach(ent -> {
			BigDecimal avaiBigDecimal = ent.availability;
			boolean availability = false;
			if (avaiBigDecimal.intValue() == 1) {
				availability = true;
			}
			results.add(new DailyPerformanceAuthority(ent.pk.companyId, roleId, ent.pk.functionNo, availability));
		});
		return results;
	}

	@Override
	public void save(DailyPerformanceAuthority daiPerAuthority) {
		KrcmtDaiPerformanceAutPk primaryKey = new KrcmtDaiPerformanceAutPk(daiPerAuthority.getCompanyId(), daiPerAuthority.getRoleID(),
				daiPerAuthority.getFunctionNo().v());
		Optional<KrcmtDaiPerformanceAut> daiPerAthrOptional = this.queryProxy().find(primaryKey,
				KrcmtDaiPerformanceAut.class);
		if (daiPerAthrOptional.isPresent()) {
			KrcmtDaiPerformanceAut entity = daiPerAthrOptional.get();
			entity.availability = bigDecimalValue(daiPerAuthority.isAvailability());
			this.commandProxy().update(entity);
		} else {
			KrcmtDaiPerformanceAut entity = new KrcmtDaiPerformanceAut();
			entity.pk = primaryKey;
			entity.availability = bigDecimalValue(daiPerAuthority.isAvailability());
			this.commandProxy().insert(entity);
		}
	}

	private BigDecimal bigDecimalValue(boolean check) {
		if (check) {
			return new BigDecimal(1);
		} else {
			return new BigDecimal(0);
		}
	}

}
