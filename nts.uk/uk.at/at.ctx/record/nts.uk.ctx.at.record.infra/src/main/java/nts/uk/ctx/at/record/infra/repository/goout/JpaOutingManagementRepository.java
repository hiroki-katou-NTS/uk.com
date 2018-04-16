package nts.uk.ctx.at.record.infra.repository.goout;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.goout.OutingManagement;
import nts.uk.ctx.at.record.dom.goout.repository.OutingManagementRepository;
import nts.uk.ctx.at.record.infra.entity.goout.KrcdtOutingManagement;

@Stateless
public class JpaOutingManagementRepository extends JpaRepository implements OutingManagementRepository {

	private static final String FIND_BY_KEY;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtOutingManagement a ");
		builderString.append("WHERE a.krcdtOutingManagementPK.companyID = :companyID ");
		FIND_BY_KEY = builderString.toString();
	}

	@Override
	public Optional<OutingManagement> findByKey(String companyId) {
		return this.queryProxy().query(FIND_BY_KEY, KrcdtOutingManagement.class).setParameter("companyID", companyId)
				.getSingle(f -> f.toDomain());
	}

}
