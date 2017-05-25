package nts.uk.ctx.at.shared.infra.repository.vacation.setting.retentionyearly;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySettingRepository;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KmfmtRetentionYearly;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KmfmtRetentionYearly_;

@Stateless
public class JpaRetentionYearlySettingRepo extends JpaRepository implements RetentionYearlySettingRepository{

	@Override
	public void insert(RetentionYearlySetting setting) {
		KmfmtRetentionYearly entity = new KmfmtRetentionYearly();
		setting.saveToMemento(new JpaRetentionYearlySetMemento(entity));
		this.commandProxy().insert(entity);
	}

	@Override
	public void update(RetentionYearlySetting setting) {
		KmfmtRetentionYearly entity = new KmfmtRetentionYearly();
		setting.saveToMemento(new JpaRetentionYearlySetMemento(entity));
		this.commandProxy().update(entity);
	}

	@Override
	public Optional<RetentionYearlySetting> findByCompanyId(String companyId) {
		return this.queryProxy()
				.find(companyId , KmfmtRetentionYearly.class)
				.map(c -> this.toDomain(c));
	}
	
	
	private RetentionYearlySetting toDomain(KmfmtRetentionYearly entity) {
		return new RetentionYearlySetting(new JpaRetentionYearlyGetMemento(entity));
		
	}
}
