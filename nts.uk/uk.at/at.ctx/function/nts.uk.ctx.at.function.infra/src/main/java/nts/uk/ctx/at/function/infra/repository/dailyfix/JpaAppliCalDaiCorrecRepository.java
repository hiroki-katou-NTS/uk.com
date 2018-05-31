package nts.uk.ctx.at.function.infra.repository.dailyfix;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.dailyfix.AppliCalDaiCorrec;
import nts.uk.ctx.at.function.dom.dailyfix.IAppliCalDaiCorrecRepository;
import nts.uk.ctx.at.function.infra.entity.dailyfix.KfnstAppliCalDaiCorrec;

@Stateless
public class JpaAppliCalDaiCorrecRepository extends JpaRepository implements IAppliCalDaiCorrecRepository{
	
	private final String SELECT_NO_WHERE = "SELECT c FROM KfnstAppliCalDaiCorrec c ";
	private final String SELECT_BY_COM = SELECT_NO_WHERE + "WHERE c.companyId = :companyId ";
	private final String DELETE_BY_COM = "DELETE FROM KfnstAppliCalDaiCorrec c "
												+ "WHERE c.companyId = :companyId ";
	
	@Override
	public List<AppliCalDaiCorrec> findByCom(String companyId) {
		return this.queryProxy().query(SELECT_BY_COM, KfnstAppliCalDaiCorrec.class)
				.setParameter("companyId", companyId)
				.getList().stream().map(x -> AppliCalDaiCorrec.createFromJavaType(x.companyId, x.appType))
				.collect(Collectors.toList());
	}

	@Override
	public void delete(String companyId) {
		this.getEntityManager().createQuery(DELETE_BY_COM)
								.setParameter("companyId", companyId)
								.executeUpdate();
	}

	@Override
	public void insert(AppliCalDaiCorrec appliCalDaiCorrec) {
		this.commandProxy().insert(new KfnstAppliCalDaiCorrec(appliCalDaiCorrec.getCompanyId(), 
																appliCalDaiCorrec.getAppType().value));
	}

}
