package nts.uk.ctx.at.shared.infra.repository.workrule.closure;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

	private final String DELETE_ALL = "DELETE FROM KclmtClosureEmployment c WHERE c.kclmpClosureEmploymentPK.companyId = :companyId";
	
	private static final String FIND;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KclmtClosureEmployment a ");
		builderString.append("WHERE a.kclmpClosureEmploymentPK.companyId = :companyId ");
		builderString.append("WHERE a.kclmpClosureEmploymentPK.employmentCD IN :employmentCDs ");
		FIND = builderString.toString();
	}
	
	/**
	 * Add list ClosureEmployment by delete all and insert.
	 */
	@Override
	public void addListClousureEmp(String companyID, List<ClosureEmployment> listClosureEmpDom) {
		//List Clousure Employment to add new.
		List<KclmtClosureEmployment> lstEntityAdd = listClosureEmpDom.stream().map(item -> {
			return new KclmtClosureEmployment(new KclmpClosureEmploymentPK(companyID, item.getEmploymentCD()), item.getClosureId());
		}).collect(Collectors.toList());
		
		//List Clousure Employment to delete all
		this.getEntityManager().createQuery(DELETE_ALL).setParameter("companyId", companyID)
			.executeUpdate();
		//Then, add new all row in table.
		this.commandProxy().insertAll(lstEntityAdd);
	}

	@Override
	public Optional<ClosureEmployment> findByEmploymentCD(String companyID, String employmentCD) {
		return this.queryProxy()
				.find(new KclmpClosureEmploymentPK(companyID, employmentCD), KclmtClosureEmployment.class)
				.map(x -> convertToDomain(x));
	}

	/**
	 * get list by list employmentCD for KIF 001
	 */
	@Override
	public List<ClosureEmployment> findListEmployment(String companyId, List<String> employmentCDs) {
		return this.queryProxy().query(FIND, KclmtClosureEmployment.class).setParameter("companyId", companyId)
				.setParameter("employmentCDs", employmentCDs).getList(f -> convertToDomain(f));
	}

	private ClosureEmployment convertToDomain(KclmtClosureEmployment kclmtClosureEmployment) {
		return new ClosureEmployment(kclmtClosureEmployment.kclmpClosureEmploymentPK.companyId,
				kclmtClosureEmployment.kclmpClosureEmploymentPK.employmentCD, 
				kclmtClosureEmployment.closureId);
	}

}
