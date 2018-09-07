package nts.uk.ctx.at.shared.infra.repository.employmentrules.temporarywork;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.employmentrules.temporarywork.TemporaryWorkUseManage;
import nts.uk.ctx.at.shared.dom.employmentrules.temporarywork.repository.TempWorkUseManageRepository;
import nts.uk.ctx.at.shared.infra.entity.employmentrules.temporarywork.KrcdtTemporaryWorkUseManage;

@Stateless
public class JpaTempWorkUseManageRepository extends JpaRepository implements TempWorkUseManageRepository{
	
	private static final String FIND_BY_KEY;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtTemporaryWorkUseManage a ");
		builderString.append("WHERE a.krcdtTemporaryWorkUseManagePK.companyID = :companyID ");
		FIND_BY_KEY = builderString.toString();
	}

	@Override
	public Optional<TemporaryWorkUseManage> findByKey(String companyId) {
		return this.queryProxy().query(FIND_BY_KEY, KrcdtTemporaryWorkUseManage.class).setParameter("companyID", companyId)
				.getSingle(f -> f.toDomain());
	}

}
