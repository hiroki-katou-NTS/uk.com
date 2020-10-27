package nts.uk.ctx.at.function.infra.repository.dailyfix;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.dailyfix.ApplicationCall;
import nts.uk.ctx.at.function.dom.dailyfix.IAppliCalDaiCorrecRepository;
import nts.uk.ctx.at.function.infra.entity.dailymodification.KfnmtDayAppCall;
import nts.uk.ctx.at.function.infra.entity.dailymodification.KfnmtDayAppCallPK;

@Stateless
public class JpaAppliCalDaiCorrecRepository extends JpaRepository implements IAppliCalDaiCorrecRepository{
	
	private static final String SELECT_NO_WHERE = "SELECT c FROM KfnmtDayAppCall c ";
	private static final String SELECT_BY_COM = SELECT_NO_WHERE + "WHERE c.kfnmtDayAppCallPK.companyId = :companyId ";
	private static final String DELETE_BY_COM = "DELETE FROM KfnmtDayAppCall c "
												+ "WHERE c.kfnmtDayAppCallPK.companyId = :companyId ";
	
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<ApplicationCall> findByCom(String companyId) {
		return this.queryProxy().query(SELECT_BY_COM, KfnmtDayAppCall.class)
				.setParameter("companyId", companyId)
				.getList().stream().map(x -> ApplicationCall.createFromJavaType(x.kfnmtDayAppCallPK.companyId, 
																				x.kfnmtDayAppCallPK.applicationType))
				.collect(Collectors.toList());
	}

	@Override
	public void delete(String companyId) {
		this.getEntityManager().createQuery(DELETE_BY_COM)
								.setParameter("companyId", companyId)
								.executeUpdate();
	}

	@Override
	public void insert(ApplicationCall appliCalDaiCorrec) {
		this.commandProxy().insert(new KfnmtDayAppCall(new KfnmtDayAppCallPK(appliCalDaiCorrec.getCompanyId(), 
																appliCalDaiCorrec.getAppType().value)));
	}

}
