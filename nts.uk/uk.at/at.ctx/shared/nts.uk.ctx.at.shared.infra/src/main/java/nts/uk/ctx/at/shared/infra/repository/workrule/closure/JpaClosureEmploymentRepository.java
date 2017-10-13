package nts.uk.ctx.at.shared.infra.repository.workrule.closure;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.infra.entity.workrule.closure.KclmpClosureEmploymentPK;
import nts.uk.ctx.at.shared.infra.entity.workrule.closure.KclmtClosureEmployment;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaClosureEmploymentRepository extends JpaRepository implements ClosureEmploymentRepository {
	
	@Override
	public Optional<ClosureEmployment> findByEmploymentCD(String companyID, String employmentCD) {
		return this.queryProxy().find(new KclmpClosureEmploymentPK(companyID, employmentCD), KclmtClosureEmployment.class).map(x -> convertToDomain(x));
	}

	private ClosureEmployment convertToDomain(KclmtClosureEmployment kclmtClosureEmployment){
		return new ClosureEmployment(
				kclmtClosureEmployment.kclmpClosureEmploymentPK.companyId, 
				kclmtClosureEmployment.kclmpClosureEmploymentPK.employmentCD, 
				kclmtClosureEmployment.closureId);
	}
}
