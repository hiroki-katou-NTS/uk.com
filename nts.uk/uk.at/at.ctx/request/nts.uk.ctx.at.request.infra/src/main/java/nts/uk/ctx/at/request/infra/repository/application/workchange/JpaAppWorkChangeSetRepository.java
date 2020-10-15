package nts.uk.ctx.at.request.infra.repository.application.workchange;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChangeSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.workchange.AppWorkChangeSet;
import nts.uk.ctx.at.request.infra.entity.application.workchange.KrqmtAppWorkChangeSet;
import nts.uk.ctx.at.request.infra.entity.application.workchange.KrqmtAppWorkChangeSetPk;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.workchangeapp.ReflectWorkChangeApp;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import org.apache.commons.lang3.BooleanUtils;

@Stateless
public class JpaAppWorkChangeSetRepository extends JpaRepository implements AppWorkChangeSetRepository {

	public static final String FIND_BY_ID = "SELECT \r\n" + "  * FROM KRQMT_APP_WORK_CHANGE WHERE CID = @companyId";

	@Override
	public Optional<AppWorkChangeSet> findByCompanyId(String companyId) {
		return new NtsStatement(FIND_BY_ID, this.jdbcProxy()).paramString("companyId", companyId)
				.getSingle(res -> KrqmtAppWorkChangeSet.MAPPER.toEntity(res).toSettingDomain());
	}

	@Override
	public void add(AppWorkChangeSet domain, int workTimeReflectAtr) {
		this.commandProxy().insert(toEntity(domain, workTimeReflectAtr));

	}

	@Override
	public void update(AppWorkChangeSet domain, int workTimeReflectAtr) {
		Optional<KrqmtAppWorkChangeSet> upAppWorkChangeSet = this.queryProxy()
				.find(new KrqmtAppWorkChangeSetPk(domain.getCompanyID()), KrqmtAppWorkChangeSet.class);
		if (!upAppWorkChangeSet.isPresent()) {
			return;
		}
		upAppWorkChangeSet.get().initDisplayWorktime = domain.getInitDisplayWorktimeAtr().value;
		upAppWorkChangeSet.get().commentContent1 = domain.getComment1().getComment().v();
		upAppWorkChangeSet.get().commentFontWeight1 = BooleanUtils.toInteger(domain.getComment1().isBold());
		upAppWorkChangeSet.get().commentFontColor1 = domain.getComment1().getColorCode().v();

		upAppWorkChangeSet.get().commentContent2 = domain.getComment2().getComment().v();
		upAppWorkChangeSet.get().commentFontWeight2 = BooleanUtils.toInteger(domain.getComment2().isBold());
		upAppWorkChangeSet.get().commentFontColor2 = domain.getComment2().getColorCode().v();

		upAppWorkChangeSet.get().workTimeReflectAtr = workTimeReflectAtr;

		this.commandProxy().update(upAppWorkChangeSet.get());
	}

	@Override
	public void remove(String companyId) {
		this.commandProxy().remove(KrqmtAppWorkChangeSet.class,
				new KrqmtAppWorkChangeSetPk(companyId));

	}

	public KrqmtAppWorkChangeSet toEntity(AppWorkChangeSet appWorkChangeSet, int workTimeReflectAtr) {
		return new KrqmtAppWorkChangeSet(new KrqmtAppWorkChangeSetPk(appWorkChangeSet.getCompanyID()),
				appWorkChangeSet.getInitDisplayWorktimeAtr().value, appWorkChangeSet.getComment1().getComment().v(),
				appWorkChangeSet.getComment1().isBold() ? 1 : 0, appWorkChangeSet.getComment1().getColorCode().v(),
				appWorkChangeSet.getComment2().getComment().v(), appWorkChangeSet.getComment2().isBold() ? 1 : 0,
				appWorkChangeSet.getComment2().getColorCode().v(), workTimeReflectAtr);
	}
	
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
