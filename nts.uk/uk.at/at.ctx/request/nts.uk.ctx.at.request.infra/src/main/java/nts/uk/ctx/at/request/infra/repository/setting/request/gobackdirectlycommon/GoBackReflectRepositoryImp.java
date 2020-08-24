package nts.uk.ctx.at.request.infra.repository.setting.request.gobackdirectlycommon;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.ApplicationStatus;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackReflect;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackReflectRepository;
import nts.uk.ctx.at.request.infra.entity.setting.request.gobackdirectlycommon.KrqmtAppGoBackReflect;
import nts.uk.ctx.at.request.infra.entity.setting.request.gobackdirectlycommon.KrqmtAppGoBackDirectlyPK;
@Stateless
public class GoBackReflectRepositoryImp extends JpaRepository implements GoBackReflectRepository {

	public static final String FIND_BY_COMPANY = "SELECT *\r\n" + "  FROM KRQMT_APP_GOBACK_DIRECTLY WHERE CID = @ID";
	
	@Override
	public Optional<GoBackReflect> findByCompany(String id) {
		return new NtsStatement(FIND_BY_COMPANY, this.jdbcProxy()).paramString("ID", id).getSingle(res -> {
			return new GoBackReflect(id, EnumAdaptor.valueOf(res.getInt("WORK_REFLECT_ATR"), ApplicationStatus.class));

		});

	}
	
	public KrqmtAppGoBackReflect toEntity(GoBackReflect goBackReflect) {
		KrqmtAppGoBackReflect entity = new KrqmtAppGoBackReflect();
		entity.setAppGoBackDirectlyPK(new KrqmtAppGoBackDirectlyPK(goBackReflect.getCompanyId()));
		entity.setReflectApp(goBackReflect.getReflectApplication().value);
		return entity;
	}

	@Override
	public void add(GoBackReflect domain) {
		this.commandProxy().insert(toEntity(domain));
		
	}

	@Override
	public void update(GoBackReflect domain) {
		KrqmtAppGoBackReflect krqstAppGoBackDirectly = toEntity(domain);
		Optional<KrqmtAppGoBackReflect> update = this.queryProxy().find(
				krqstAppGoBackDirectly.getAppGoBackDirectlyPK(), 
				KrqmtAppGoBackReflect.class);
		if (!update.isPresent()) return;
		update.get().setReflectApp(domain.getReflectApplication().value);
		this.commandProxy().update(update.get());
	}

	@Override
	public void remove(GoBackReflect domain) {
		this.commandProxy().remove(
				KrqmtAppGoBackReflect.class,
				new KrqmtAppGoBackDirectlyPK(domain.getCompanyId()));;
		
	}


}
