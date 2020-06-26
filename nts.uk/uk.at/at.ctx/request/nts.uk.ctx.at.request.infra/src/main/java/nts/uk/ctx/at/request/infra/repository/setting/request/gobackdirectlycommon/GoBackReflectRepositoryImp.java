package nts.uk.ctx.at.request.infra.repository.setting.request.gobackdirectlycommon;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.ApplicationStatus;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackReflect;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackReflectRepository;
import nts.uk.ctx.at.request.infra.entity.setting.request.gobackdirectlycommon.KrqstAppGoBackDirectly;
@Stateless
public class GoBackReflectRepositoryImp extends JpaRepository implements GoBackReflectRepository {
	// KRQST_APP_GOBACK_DIRECTLY
	public static final String FIND_BY_COMPANY = "SELECT *\r\n" + "  FROM KRQST_APP_GOBACK_DIRECTLY WHERE CID = @ID";
	
	@Override
	public Optional<GoBackReflect> findByCompany(String id) {
		return new NtsStatement(FIND_BY_COMPANY, this.jdbcProxy()).paramString("ID", id).getSingle(res -> {
			return new GoBackReflect(id, EnumAdaptor.valueOf(res.getInt("WORK_REFLECT_ATR"), ApplicationStatus.class));

		});

	}
	
	public KrqstAppGoBackDirectly toEntity(GoBackReflect goBackReflect) {
		KrqstAppGoBackDirectly entity = new KrqstAppGoBackDirectly();
		entity.getAppGoBackDirectlyPK().setCompanyID(goBackReflect.getCompanyId());
		entity.setReflectApp(goBackReflect.getReflectApplication().value);
		return entity;
	}

}
