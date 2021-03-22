package nts.uk.ctx.at.request.infra.repository.application.workchange.setting;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.workchangeapp.ReflectWorkChangeApp;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.workchangeapp.ReflectWorkChangeAppRepository;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
public class JpaReflectWorkChangeAppRepository extends JpaRepository implements ReflectWorkChangeAppRepository {

	public static final String FIND_BY_ID = "SELECT \r\n" + "  * FROM KRQMT_APP_WORK_CHANGE WHERE CID = @companyId";

	public ReflectWorkChangeApp toDomainReflect(NtsResultRecord res) {
		ReflectWorkChangeApp reflectWorkChangeApp = new ReflectWorkChangeApp();
		reflectWorkChangeApp.setCompanyID(res.getString("CID"));
		if (res.getInt("WORK_TIME_REFLECT_ATR") != null) {
			reflectWorkChangeApp.setWhetherReflectAttendance(NotUseAtr.valueOf(res.getInt("WORK_TIME_REFLECT_ATR")));
		}
		return reflectWorkChangeApp;
	}

	@Override
	public Optional<ReflectWorkChangeApp> findByCompanyIdReflect(String companyId) {
		return new NtsStatement(FIND_BY_ID, this.jdbcProxy()).paramString("companyId", companyId)
				.getSingle(res -> toDomainReflect(res));
	}

}
