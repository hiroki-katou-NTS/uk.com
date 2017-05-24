package nts.uk.ctx.pr.core.infra.repository.vacation.setting.acquisitionrule;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule.AcquisitionRuleRepository;
import nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule.AcquisitionRule;
import nts.uk.ctx.pr.core.infra.entity.vacation.setting.acquisitionrule.KmfstAcquisitionRule;

/**
 * The Class JpaAcquisitionRuleRepository.
 */
@Stateless
public class JpaAcquisitionRuleRepository extends JpaRepository implements AcquisitionRuleRepository{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule.VaAcRuleRepository#create(nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule.VacationAcquisitionRule)
	 */
	@Override
	public void create(AcquisitionRule acquisitionRule) {
		EntityManager em = this.getEntityManager();
		KmfstAcquisitionRule entity = new KmfstAcquisitionRule();
		acquisitionRule.saveToMemento(new JpaAcquisitionRuleSetMemento(entity));
		em.persist(entity);		
	}

	@Override
	public void update(AcquisitionRule acquisitionRule) {
		EntityManager em = this.getEntityManager();
		KmfstAcquisitionRule entity = new KmfstAcquisitionRule();
		acquisitionRule.saveToMemento(new JpaAcquisitionRuleSetMemento(entity));
		em.merge(entity);	
	}

	@Override
	public void remove(String companyId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<AcquisitionRule> findById(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AcquisitionRule> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
