package nts.uk.ctx.at.request.infra.repository.application.lateorleaveearly;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyCancelAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyCancelAppSetRepository;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.applicationlatearrival.KrqmtAppLateOrEarly;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.lateearlycancellation.LateEarlyCancelReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.lateearlycancellation.LateEarlyCancelReflectRepository;
import org.apache.commons.lang3.BooleanUtils;

/**
 * @author anhnm
 *
 */
@Stateless
public class LateEarlyCancelAppSetRepositoryImp extends JpaRepository implements LateEarlyCancelAppSetRepository, LateEarlyCancelReflectRepository {
	private final String SELECT_ALL = "SELECT * FROM KRQMT_APP_LATE_OR_LEAVE WHERE CID = @companyId";

	@Override
	public LateEarlyCancelAppSet getByCId(String companyId) {
		Optional<LateEarlyCancelAppSet> result = new NtsStatement(SELECT_ALL, this.jdbcProxy())
				.paramString("companyId", companyId).getSingle(e -> KrqmtAppLateOrEarly.MAPPER.toEntity(e).toSettingDomain());
		return result.isPresent() ? result.get() : null;
	}

	@Override
	public LateEarlyCancelReflect getByCompanyId(String companyId) {
		return this.queryProxy().find(companyId, KrqmtAppLateOrEarly.class).map(KrqmtAppLateOrEarly::toReflectDomain).orElse(null);
	}

	@Override
	public void save(String companyId, LateEarlyCancelAppSet setting, LateEarlyCancelReflect reflect) {
		Optional<KrqmtAppLateOrEarly> optEntity = this.queryProxy().find(companyId, KrqmtAppLateOrEarly.class);
		if (optEntity.isPresent()) {
			KrqmtAppLateOrEarly entity = optEntity.get();
			entity.setCancelAtr(setting.getCancelAtr().value);
			entity.setLateAlClearAtr(BooleanUtils.toInteger(reflect.isClearLateReportWarning()));
			this.commandProxy().update(entity);
		} else {
			KrqmtAppLateOrEarly entity = KrqmtAppLateOrEarly.create(companyId, setting.getCancelAtr().value, BooleanUtils.toInteger(reflect.isClearLateReportWarning()));
			this.commandProxy().insert(entity);
		}
	}
}
