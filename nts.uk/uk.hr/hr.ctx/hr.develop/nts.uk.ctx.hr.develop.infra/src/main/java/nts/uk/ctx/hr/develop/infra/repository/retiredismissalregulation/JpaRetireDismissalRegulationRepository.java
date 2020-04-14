/**
 * 
 */
package nts.uk.ctx.hr.develop.infra.repository.retiredismissalregulation;

import java.util.Optional;

import javax.ejb.Stateless;

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
	
	private static final String GET_DOMAIN_BY_CID_HISTID = "SELECT c FROM JshmtRetireDismissReg c "
			+ "WHERE c.cId = :cId and c.histId =:histId ";

	@Override
	public Optional<RetireDismissalRegulation> getDomain(String cId, String histId) {
		Optional<JshmtRetireDismissReg> entity = this.queryProxy().query(GET_DOMAIN_BY_CID_HISTID, JshmtRetireDismissReg.class)
				.setParameter("cId", cId)
				.setParameter("histId", histId).getSingle();
		
		if (!entity.isPresent()) {
			return Optional.empty();
		}
		
		RetireDismissalRegulation domain = entity.get().toDomain();
		
		return Optional.of(domain);
	}

}
