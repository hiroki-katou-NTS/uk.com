package nts.uk.ctx.at.request.infra.repository.application.lateorleaveearly;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarlyRepository;
import nts.uk.ctx.at.request.infra.entity.application.lateorleaveearly.KrqdtAppLateEarly;
import nts.uk.ctx.at.request.infra.entity.application.lateorleaveearly.KrqdtAppLateEarlyPK;

@Stateless
public class JpaLateOrLeaveEarlyRepository extends JpaRepository implements LateOrLeaveEarlyRepository {

	// private final String SELECT= "SELECT c FROM KrqdtAppLateEarly c";
	// private final String SELECT_ALL_BY_COMPANY = SELECT + " WHERE
	// c.KrqdtAppLateEarlyPK.companyID = :companyID";
	private static final String SELECT_SINGLE = "SELECT c" + " FROM KrqdtAppLateEarly c"
			+ " WHERE c.krqdtAppLateEarlyPK.appID = :appID AND c.krqdtAppLateEarlyPK.companyID = :companyID";
	private static final String SELECT_LIST_CANCEL_ATR = "SELECT c FROM KrqdtAppLateEarly c "
			+ "WHERE c.krqdtAppLateEarlyPK.appID IN :listAppID AND c.actualCancelAtr = :actualCancelAtr";

	@Override
	public Optional<LateOrLeaveEarly> findByCode(String companyID, String appID) {
		return this.queryProxy().query(SELECT_SINGLE, KrqdtAppLateEarly.class).setParameter("companyID", companyID)
				.setParameter("appID", appID).getSingle(c -> c.toDomain());
	}

	/**
	 * Add
	 * 
	 * @param lateOrLeaveEarly
	 * @return
	 */
	@Override
	public void add(LateOrLeaveEarly lateOrLeaveEarly) {
		this.commandProxy().insert(KrqdtAppLateEarly.toEntity(lateOrLeaveEarly));

	}

	/**
	 * Update
	 * 
	 * @param lateOrLeaveEarly
	 * @return
	 */
	@Override
	public void update(LateOrLeaveEarly lateOrLeaveEarly) {
		this.commandProxy().update(KrqdtAppLateEarly.toEntity(lateOrLeaveEarly));

	}

	@Override
	public void remove(String companyID, String appID) {
		this.commandProxy().remove(KrqdtAppLateEarly.class, new KrqdtAppLateEarlyPK(companyID, appID));
		this.getEntityManager().flush();

	}

//	@Override
//	public ApplicationReason findApplicationReason(String companyID, ApplicationType applicationType) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<LateOrLeaveEarly> findByActualCancelAtr(List<String> listAppID, Integer actualCancelAtr) {
		List<LateOrLeaveEarly> resultList = new ArrayList<>();
		CollectionUtil.split(listAppID, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(this.queryProxy().query(SELECT_LIST_CANCEL_ATR, KrqdtAppLateEarly.class)
								  .setParameter("listAppID", subList)
								  .setParameter("actualCancelAtr", actualCancelAtr)
								  .getList(c -> c.toDomain()));
		});
		return resultList;
	};

}
