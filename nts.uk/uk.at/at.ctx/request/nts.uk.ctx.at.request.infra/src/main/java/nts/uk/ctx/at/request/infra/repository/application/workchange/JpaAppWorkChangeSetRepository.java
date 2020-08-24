package nts.uk.ctx.at.request.infra.repository.application.workchange;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChangeSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.workchange.AppWorkChangeSet;
import nts.uk.ctx.at.request.infra.entity.application.workchange.KrqstAppWorkChangeSet;
import nts.uk.ctx.at.request.infra.entity.application.workchange.KrqstAppWorkChangeSetPk;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.workchangeapp.ReflectWorkChangeApp;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
public class JpaAppWorkChangeSetRepository extends JpaRepository implements AppWorkChangeSetRepository {

	public static final String FIND_BY_ID = "SELECT \r\n" + "  * FROM KRQMT_APP_WORK_CHANGE WHERE CID = @companyId";

	@Override
	public Optional<AppWorkChangeSet> findByCompanyId(String companyId) {
		return new NtsStatement(FIND_BY_ID, this.jdbcProxy()).paramString("companyId", companyId)
				.getSingle(res -> KrqstAppWorkChangeSet.MAPPER.toEntity(res).toDomain());
	}

	@Override
	public void add(AppWorkChangeSet domain) {
		this.commandProxy().insert(toEntity(domain));

	}

	@Override
	public void update(AppWorkChangeSet domain) {
		KrqstAppWorkChangeSet krqstAppWorkChangeSet = toEntity(domain);
		Optional<KrqstAppWorkChangeSet> upAppWorkChangeSet = this.queryProxy()
				.find(krqstAppWorkChangeSet.appWorkChangeSetPk, KrqstAppWorkChangeSet.class);
		if (!upAppWorkChangeSet.isPresent()) {
			return;
		}
		upAppWorkChangeSet.get().initDisplayWorktime = krqstAppWorkChangeSet.initDisplayWorktime;
		upAppWorkChangeSet.get().commentContent1 = krqstAppWorkChangeSet.commentContent1;
		upAppWorkChangeSet.get().commentFontWeight1 = krqstAppWorkChangeSet.commentFontWeight1;
		upAppWorkChangeSet.get().commentFontColor1 = krqstAppWorkChangeSet.commentFontColor1;

		upAppWorkChangeSet.get().commentContent2 = krqstAppWorkChangeSet.commentContent2;
		upAppWorkChangeSet.get().commentFontWeight2 = krqstAppWorkChangeSet.commentFontWeight2;
		upAppWorkChangeSet.get().commentFontColor2 = krqstAppWorkChangeSet.commentFontColor2;

		this.commandProxy().update(upAppWorkChangeSet.get());
	}

	@Override
	public void remove(String companyId) {
		this.commandProxy().remove(KrqstAppWorkChangeSet.class,
				new KrqstAppWorkChangeSetPk(companyId));

	}

	public KrqstAppWorkChangeSet toEntity(AppWorkChangeSet appWorkChangeSet) {
		return new KrqstAppWorkChangeSet(new KrqstAppWorkChangeSetPk(appWorkChangeSet.getCompanyID()),
				appWorkChangeSet.getInitDisplayWorktimeAtr().value, appWorkChangeSet.getComment1().getComment().v(),
				appWorkChangeSet.getComment1().isBold() ? 1 : 0, appWorkChangeSet.getComment1().getColorCode().v(),
				appWorkChangeSet.getComment2().getComment().v(), appWorkChangeSet.getComment2().isBold() ? 1 : 0,
				appWorkChangeSet.getComment2().getColorCode().v(), null);
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
