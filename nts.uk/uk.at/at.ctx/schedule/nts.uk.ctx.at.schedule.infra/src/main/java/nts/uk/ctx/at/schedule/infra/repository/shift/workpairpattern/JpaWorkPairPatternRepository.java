package nts.uk.ctx.at.schedule.infra.repository.shift.workpairpattern;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.workpairpattern.ComPattern;
import nts.uk.ctx.at.schedule.dom.shift.workpairpattern.WorkPairPatternRepository;
import nts.uk.ctx.at.schedule.dom.shift.workpairpattern.WorkplacePattern;
import nts.uk.ctx.at.schedule.infra.entity.shift.workpairpattern.KscmtComPattern;
import nts.uk.ctx.at.schedule.infra.entity.shift.workpairpattern.KscmtWkpPattern;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class JpaWorkPairPatternRepository extends JpaRepository implements WorkPairPatternRepository {

	private static final String SEL_COM_PATTERN = "SELECT a FROM KscmtComPattern a WHERE a.kscmtComPatternPk.companyId = :companyId ORDER BY a.kscmtComPatternPk.groupNo ASC";

	private static final String SEL_WKP_PATTERN = "SELECT b FROM KscmtWkpPattern b WHERE b.kscmtWkpPatternPk.workplaceId = :workplaceId ORDER BY a.kscmtWkpPatternPk.groupNo ASC";

	private ComPattern toDomain(KscmtComPattern entity) {
		return ComPattern.convertFromJavaType(entity.kscmtComPatternPk.companyId, entity.kscmtComPatternPk.groupNo,
				entity.groupName, entity.groupUsageAtr, entity.note);
	}

	private WorkplacePattern toDomainWkpPattern(KscmtWkpPattern entity) {
		return WorkplacePattern.convertFromJavaType(entity.kscmtWkpPatternPk.workplaceId,
				entity.kscmtWkpPatternPk.groupNo, entity.groupName, entity.groupUsageAtr, entity.note);
	}

	@Override
	public List<ComPattern> getAllDataComPattern(String companyId) {
		return this.queryProxy().query(SEL_COM_PATTERN, KscmtComPattern.class).setParameter("companyId", companyId)
				.getList(x -> toDomain(x));
	}

	@Override
	public List<WorkplacePattern> getAllDataWkpPattern(String workplaceId) {
		return this.queryProxy().query(SEL_WKP_PATTERN, KscmtWkpPattern.class).setParameter("workplaceId", workplaceId)
				.getList(x -> toDomainWkpPattern(x));
	}
}
