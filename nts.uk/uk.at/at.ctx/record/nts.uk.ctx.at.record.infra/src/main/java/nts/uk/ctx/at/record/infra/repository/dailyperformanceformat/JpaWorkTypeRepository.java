package nts.uk.ctx.at.record.infra.repository.dailyperformanceformat;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.WorkType;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.WorkTypesRepository;
import nts.uk.ctx.at.record.infra.entity.dailyperformanceformat.KdwmtWorkType;

@Stateless
public class JpaWorkTypeRepository extends JpaRepository implements WorkTypesRepository {

	private static final String FIND;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KdwmtWorkType a ");
		builderString.append("WHERE a.kdwmtWorkTypePK.companyId = :companyId ");
		FIND = builderString.toString();
	}

	@Override
	public List<WorkType> findAll(String companyId) {
		return this.queryProxy().query(FIND, KdwmtWorkType.class).setParameter("companyId", companyId)
				.getList(f -> toDomain(f));
	}

	private static WorkType toDomain(KdwmtWorkType kdwmtWorkType) {
		WorkType workType = WorkType.createFromJavaType(kdwmtWorkType.kdwmtWorkTypePK.companyId,
				kdwmtWorkType.kdwmtWorkTypePK.workTypeCode, kdwmtWorkType.workTypeName);

		return workType;
	}

}
