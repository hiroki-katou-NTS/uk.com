package nts.uk.ctx.at.function.infra.repository.holidaysremaining;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.holidaysremaining.HolidaysRemainingManagement;
import nts.uk.ctx.at.function.dom.holidaysremaining.repository.HolidaysRemainingManagementRepository;
import nts.uk.ctx.at.function.infra.entity.holidaysremaining.KfnmtHdRemainManage;
import nts.uk.ctx.at.function.infra.entity.holidaysremaining.KfnmtHdRemainManagePk;

@Stateless
public class JpaHdRemainManageRepository extends JpaRepository implements HolidaysRemainingManagementRepository{

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM KfnmtHdRemainManage f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.hdRemainManagePk.cid =:cid AND  f.hdRemainManagePk.cd =:cd ";
	private static final String SELECT_BY_COMPANY_ID = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.hdRemainManagePk.cid =:cid ";
	@Override
	public Optional<HolidaysRemainingManagement> getHolidayManagerByCidAndExecCd(String companyID, String code) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, KfnmtHdRemainManage.class)
				.setParameter("cd", code)
				.setParameter("cid", companyID).getSingle(c -> c.toDomain());
	}
	@Override
	public void insert(HolidaysRemainingManagement domain) {
		this.commandProxy().insert(KfnmtHdRemainManage.toEntity(domain));
		
	}
	@Override
	public void update(HolidaysRemainingManagement domain) {
		KfnmtHdRemainManage updateData = KfnmtHdRemainManage.toEntity(domain);
		KfnmtHdRemainManage oldData = this.queryProxy().find(updateData.hdRemainManagePk, KfnmtHdRemainManage.class).get();
		this.commandProxy().update(oldData);
		
	}
	@Override
	public void remove(String companyId, String code) {
		KfnmtHdRemainManagePk kfnmtSpecialHolidayPk = new KfnmtHdRemainManagePk(companyId, code);
		this.commandProxy().remove(KfnmtHdRemainManage.class, kfnmtSpecialHolidayPk);
		
	}
	@Override
	public List<HolidaysRemainingManagement> getHolidayManagerLogByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_BY_COMPANY_ID, KfnmtHdRemainManage.class)
				.setParameter("cid", companyId).getList(c -> c.toDomain());
	}

}
