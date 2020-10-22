package nts.uk.ctx.sys.portal.infra.repository.toppagesetting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPagePersonSetting;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPagePersonSettingRepository;
import nts.uk.ctx.sys.portal.infra.entity.toppagesetting.SptmtTopPagePerson;
import nts.uk.ctx.sys.portal.infra.entity.toppagesetting.SptmtTopPagePersonPK;

@Stateless
public class JpaTopPagePersonSettingRepository extends JpaRepository implements TopPagePersonSettingRepository {

	private static final String SEL = "SELECT c FROM SptmtTopPagePerson c ";
	private static final String SELECT_BY_LIST_SID = SEL + "WHERE c.SptmtTopPagePersonPK.companyId = :companyId "
			+ " AND c.SptmtTopPagePersonPK.employeeId IN :employeeId";
	
	private static final String SELECT_BY_SID = SEL + "WHERE c.SptmtTopPagePersonPK.companyId = :companyId "
			+ " AND c.SptmtTopPagePersonPK.employeeId = :employeeId";
	
	@Override
	public void insert(TopPagePersonSetting domain) {
		this.commandProxy().insert(this.toEntity(domain));
	}

	private SptmtTopPagePerson toEntity(TopPagePersonSetting domain) {
		SptmtTopPagePerson entity = new SptmtTopPagePerson();
		domain.setMemento(entity);
		return entity;
	}

	@Override
	public void update(TopPagePersonSetting domain) {
		this.commandProxy().updateWithCharPrimaryKey(this.toEntity(domain));
	}

	@Override
	public void delete(TopPagePersonSetting domain) {
		SptmtTopPagePersonPK pk = new SptmtTopPagePersonPK(
				domain.getEmployeeId());
		this.commandProxy().remove(SptmtTopPagePerson.class, pk);
	}

	@Override
	public List<TopPagePersonSetting> getByCompanyIdAndEmployeeIds(String companyId, List<String> employeeIds) {
		return this.queryProxy()
			.query(SELECT_BY_LIST_SID, TopPagePersonSetting.class)
			.setParameter("companyId", companyId)
			.setParameter("employeeId", employeeIds)
			.getList();
	}

	@Override
	public Optional<TopPagePersonSetting> getByCompanyIdAndEmployeeId(String companyId, String employeeId) {
		return null;
//		return this.queryProxy()
//			.query(SELECT_BY_SID, TopPagePersonSetting.class)
//			.setParameter("companyId", companyId)
//			.setParameter("employeeId", employeeId)
//			.getSingle(TopPagePersonSetting::createFromMemento);
	}

}
