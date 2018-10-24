package nts.uk.ctx.at.request.infra.repository.application.overtime;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetailRepository;
import nts.uk.ctx.at.request.infra.entity.application.overtime.KrqdtAppOvertimeDetail;

@Stateless
public class JpaAppOvertimeDetailRepository extends JpaRepository implements AppOvertimeDetailRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM KrqdtAppOvertimeDetail f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.appOvertimeDetailPk.cid =:cid AND  f.appOvertimeDetailPk.appId =:appId ";

	@Override
	public Optional<AppOvertimeDetail> getAppOvertimeDetailById(String cid, String appId) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, KrqdtAppOvertimeDetail.class).setParameter("cid", cid)
				.setParameter("appId", appId).getSingle(c -> c.toDomain());
	}
}
