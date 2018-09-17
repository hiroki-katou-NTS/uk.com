package nts.uk.ctx.core.infra.repository.socialinsurance.welfarepensioninsurance;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceClassification;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceClassificationRepository;
import nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance.QpbmtWelfarePensionInsuranceClassification;

@Stateless
public class JpaWelfarePensionInsuranceClassificationRepository extends JpaRepository
		implements WelfarePensionInsuranceClassificationRepository {

	@Override
	public Optional<WelfarePensionInsuranceClassification> getWelfarePensionInsuranceClassificationById( String historyId) {
		return this.queryProxy().find(historyId, QpbmtWelfarePensionInsuranceClassification.class).map(QpbmtWelfarePensionInsuranceClassification::toDomain);
	}

	@Override
	public void deleteByHistoryIds(List<String> historyIds) {
		this.commandProxy().removeAll(QpbmtWelfarePensionInsuranceClassification.class, historyIds);
	}
	
	@Override
	public void add(WelfarePensionInsuranceClassification domain) {
		this.commandProxy().insert(QpbmtWelfarePensionInsuranceClassification.toEntity(domain));
	}
	
	@Override
	public void update(WelfarePensionInsuranceClassification domain) {
		this.commandProxy().update(QpbmtWelfarePensionInsuranceClassification.toEntity(domain));
	}
	
	@Override
	public void remove(WelfarePensionInsuranceClassification domain) {
		this.commandProxy().remove(QpbmtWelfarePensionInsuranceClassification.toEntity(domain));
	}

}
