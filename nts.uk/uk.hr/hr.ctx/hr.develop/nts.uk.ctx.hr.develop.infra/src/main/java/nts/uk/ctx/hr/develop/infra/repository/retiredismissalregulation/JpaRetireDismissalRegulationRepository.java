/**
 * 
 */
package nts.uk.ctx.hr.develop.infra.repository.retiredismissalregulation;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.hr.develop.dom.retiredismissalregulation.RetireDismissalRegulation;
import nts.uk.ctx.hr.develop.dom.retiredismissalregulation.algorithm.RetireDismissalRegulationRepository;
import nts.uk.ctx.hr.develop.infra.entity.retiredismissalregulation.JshmtRetireDismissReg;

/**
 * @author laitv
 *
 */

@Stateless
public class JpaRetireDismissalRegulationRepository extends JpaRepository implements RetireDismissalRegulationRepository{
	
	private static final String getDomain_RetireDismissalRegulation_by_hisid_cid = "SELECT c FROM JshmtRetireDismissReg c "
			+ "WHERE c.cId = :cId and c.histId =:histId ";
	
	@Override
	public Optional<RetireDismissalRegulation> getDomain(String cId, String histId) {
		Optional<JshmtRetireDismissReg> entity = this.queryProxy().query(getDomain_RetireDismissalRegulation_by_hisid_cid, JshmtRetireDismissReg.class)
				.setParameter("cId", cId)
				.setParameter("histId", histId).getSingle();
		
		if (!entity.isPresent()) {
			return Optional.empty();
		}
		
		RetireDismissalRegulation domain = entity.get().toDomain();
		
		return Optional.of(domain);
	}

	@Override
	public void addRetireDismissalRegulation(RetireDismissalRegulation domain) {
		Optional<JshmtRetireDismissReg> checkExit = this.queryProxy().query(getDomain_RetireDismissalRegulation_by_hisid_cid, JshmtRetireDismissReg.class)
				.setParameter("cId", domain.getCompanyId())
				.setParameter("histId", domain.getHistoryId()).getSingle();
		if (checkExit.isPresent()) {
			throw new BusinessException("entity JshmtRetireDismissReg Exit");
		} else {
			JshmtRetireDismissReg entity = new JshmtRetireDismissReg(domain);
			this.commandProxy().insert(entity);
		}
	}

	@Override
	public void updateRetireDismissalRegulation(RetireDismissalRegulation domain) {
		Optional<JshmtRetireDismissReg> checkExit = this.queryProxy().query(getDomain_RetireDismissalRegulation_by_hisid_cid, JshmtRetireDismissReg.class)
				.setParameter("cId", domain.getCompanyId())
				.setParameter("histId", domain.getHistoryId()).getSingle();
		if (checkExit.isPresent()) {
			JshmtRetireDismissReg entity = new JshmtRetireDismissReg(domain);
			this.commandProxy().update(entity);
		} 
		
	}
	
}
