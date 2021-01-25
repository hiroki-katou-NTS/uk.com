package nts.uk.ctx.at.request.infra.repository.application.holidayshipment.compltleavesimmng;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.AppHdsubRec;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.AppHdsubRecRepository;
import nts.uk.ctx.at.request.infra.entity.application.holidayshipment.compltleavesimmana.KrqdtAppHdsubRec;
import nts.uk.ctx.at.request.infra.entity.application.holidayshipment.compltleavesimmana.KrqdtAppHdsubRecPK;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb
 */
@Stateless
public class JpaAppHdsubRecRepository extends JpaRepository implements AppHdsubRecRepository {

	private static final String FIND_ALL = "SELECT m FROM KrqdtAppHdsubRec m";
	private static final String FIND_BY_REC_APP_ID = FIND_ALL + " WHERE m.pk.recAppID=:recAppID ";
	private static final String FIND_BY_ABS_APP_ID = FIND_ALL + " WHERE m.pk.absenceLeaveAppID=:absenceLeaveAppID ";
	private static final String FIND_BY_APPID = FIND_ALL 
			+ " WHERE (m.pk.recAppID = :appId or m.pk.absenceLeaveAppID = :appId)"
			+ " AND m.syncing = 1";

	@Override
	public void insert(AppHdsubRec domain) {
		this.commandProxy().insert(new KrqdtAppHdsubRec(domain));

	}

	@Override
	public Optional<AppHdsubRec> findByRecID(String recAppID) {
		return this.queryProxy().query(FIND_BY_REC_APP_ID, KrqdtAppHdsubRec.class)
				.setParameter("recAppID", recAppID).getSingle().map(x -> x.toDomain());
	}

	@Override
	public Optional<AppHdsubRec> findByAbsID(String absAppID) {
		return this.queryProxy().query(FIND_BY_ABS_APP_ID, KrqdtAppHdsubRec.class)
				.setParameter("absenceLeaveAppID", absAppID).getSingle().map(x -> x.toDomain());
	}

	@Override
	public void remove(String absAppID, String recAppID) {
		this.commandProxy().remove(KrqdtAppHdsubRec.class, new KrqdtAppHdsubRecPK(AppContexts.user().companyId(), recAppID, absAppID));
	}

	@Override
	public void update(AppHdsubRec compltLeaveSimMng) {
		this.commandProxy().update(new KrqdtAppHdsubRec(compltLeaveSimMng));

	}

	/**
	 * find CompltLeaveSimMng By AppId
	 * @author hoatt
	 * @param appId
	 * @return
	 */
	@Override
	public Optional<AppHdsubRec> findByAppId(String appId) {
		return this.queryProxy().query(FIND_BY_APPID, KrqdtAppHdsubRec.class)
				.setParameter("appId", appId).getSingle().map(x -> x.toDomain());
	}

}
