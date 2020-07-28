package nts.uk.ctx.at.request.infra.repository.application.lateorleaveearly;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyCancelAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyCancelAppSetRepository;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.applicationlatearrival.KrqstAppLateOrEarly;

/**
 * @author anhnm
 *
 */
@Stateless
public class LateEarlyCancelAppSetRepositoryImp extends JpaRepository implements LateEarlyCancelAppSetRepository {
	private final String SELECT_ALL = "SELECT * FROM KRQST_APP_LATE_OR_LEAVE WHERE CID = @companyId";

	@Override
	public LateEarlyCancelAppSet getByCId(String companyId) {
		Optional<LateEarlyCancelAppSet> result = new NtsStatement(SELECT_ALL, this.jdbcProxy())
				.paramString("companyId", companyId).getSingle(e -> KrqstAppLateOrEarly.MAPPER.toEntity(e).toDomain());
		return result.isPresent() ? result.get() : null;
	}
}
