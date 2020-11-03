package nts.uk.ctx.sys.portal.infra.repository.toppagesetting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPagePersonSetting;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPagePersonSettingRepository;
import nts.uk.ctx.sys.portal.infra.entity.toppagesetting.SptmtTopPagePerson;
import nts.uk.ctx.sys.portal.infra.entity.toppagesetting.SptmtTopPagePersonPK;

/**
 * The Class JpaTopPagePersonSettingRepository.
 */
@Stateless
public class JpaTopPagePersonSettingRepository extends JpaRepository implements TopPagePersonSettingRepository {

	/** The Constant SEL. */
	private static final String SEL = "SELECT c FROM SptmtTopPagePerson c ";
	
	/** The Constant SELECT_BY_LIST_SID. */
	private static final String SELECT_BY_LIST_SID = SEL + "WHERE c.companyID = :companyId "
			+ " AND c.pk.employeeId IN :employeeId";
	
	/** The Constant SELECT_BY_SID. */
	private static final String SELECT_BY_SID = SEL + "WHERE c.companyID = :companyId "
			+ " AND c.pk.employeeId = :employeeId";
	
	/**
	 * Insert.
	 *
	 * @param domain the domain
	 */
	@Override
	public void insert(String contractCd, String companyId, TopPagePersonSetting domain) {
		this.commandProxy().insert(this.toEntity(contractCd, companyId, domain));
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the sptmt top page person
	 */
	private SptmtTopPagePerson toEntity(String contractCd, String companyId, TopPagePersonSetting domain) {
		SptmtTopPagePerson entity = new SptmtTopPagePerson();
		entity.setContractCd(contractCd);
		entity.setCompanyID(companyId);
		domain.setMemento(entity);
		return entity;
	}

	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	@Override
	public void update(String contractCd, String companyId, TopPagePersonSetting domain) {
		this.commandProxy().updateWithCharPrimaryKey(this.toEntity(contractCd, companyId, domain));
	}

	/**
	 * Delete.
	 *
	 * @param domain the domain
	 */
	@Override
	public void delete(TopPagePersonSetting domain) {
		SptmtTopPagePersonPK pk = new SptmtTopPagePersonPK(
				domain.getEmployeeId());
		this.commandProxy().remove(SptmtTopPagePerson.class, pk);
	}

	/**
	 * Gets the by company id and employee ids.
	 *
	 * @param companyId the company id
	 * @param employeeIds the employee ids
	 * @return the by company id and employee ids
	 */
	@Override
	public List<TopPagePersonSetting> getByCompanyIdAndEmployeeIds(String companyId, List<String> employeeIds) {
		return this.queryProxy()
			.query(SELECT_BY_LIST_SID, SptmtTopPagePerson.class)
			.setParameter("companyId", companyId)
			.setParameter("employeeId", employeeIds)
			.getList(TopPagePersonSetting::createFromMemento);
	}

	/**
	 * Gets the by company id and employee id.
	 *
	 * @param companyId the company id
	 * @param employeeId the employee id
	 * @return the by company id and employee id
	 */
	@Override
	public Optional<TopPagePersonSetting> getByCompanyIdAndEmployeeId(String companyId, String employeeId) {
		return this.queryProxy()
			.query(SELECT_BY_SID, SptmtTopPagePerson.class)
			.setParameter("companyId", companyId)
			.setParameter("employeeId", employeeId)
			.getSingle(TopPagePersonSetting::createFromMemento);
	}

}
