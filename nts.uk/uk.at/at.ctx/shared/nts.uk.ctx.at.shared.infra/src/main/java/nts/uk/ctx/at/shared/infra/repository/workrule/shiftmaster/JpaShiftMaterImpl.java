package nts.uk.ctx.at.shared.infra.repository.workrule.shiftmaster;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterRepository;
import nts.uk.ctx.at.shared.infra.entity.workrule.shiftmaster.KshmtShiftMater;
import nts.uk.ctx.at.shared.infra.entity.workrule.shiftmaster.KshmtShiftMaterPK;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaShiftMaterImpl extends JpaRepository implements ShiftMasterRepository {
	private static final String SELECT_ALL = "SELECT c FROM KshmtShiftMater c ";
	
	private static final String SELECT_BY_CID = SELECT_ALL+ " WHERE c.kshmtShiftMaterPK.companyId = :companyId";
	
	private static final String SELECT_BY_CD_AND_CID = SELECT_BY_CID
			+ " AND c.kshmtShiftMaterPK.shiftMaterCode = :shiftMaterCode";
	
	private static final String SELECT_BY_WORKTYPE_AND_WORKTIME = SELECT_BY_CID
			+ " AND c.workTypeCd = :workTypeCd"
			+ " AND c.workTimeCd = :workTimeCd";
	
	private static final String SELECT_BY_LIST_CD_AND_CID = SELECT_BY_CID
			+ " AND c.kshmtShiftMaterPK.shiftMaterCode IN :listShiftMaterCode";
	
	@Override
	public List<ShiftMaster> getAllByCid(String companyId) {
		List<ShiftMaster> datas = this.queryProxy().query(SELECT_BY_CID,KshmtShiftMater.class)
				.setParameter("companyId", companyId).getList(c->c.toDomain());
		return datas;
	}

	@Override
	public Optional<ShiftMaster> getByShiftMaterCd(String companyId, String shiftMaterCode) {
		Optional<ShiftMaster> data = this.queryProxy().query(SELECT_BY_CD_AND_CID,KshmtShiftMater.class)
				.setParameter("companyId", companyId)
				.setParameter("shiftMaterCode", shiftMaterCode)
				.getSingle(c->c.toDomain());
		return data;
	}

	@Override
	public Optional<ShiftMaster> getByWorkTypeAndWorkTime(String companyId, String workTypeCd, String workTimeCd) {
		Optional<ShiftMaster> data = this.queryProxy().query(SELECT_BY_WORKTYPE_AND_WORKTIME,KshmtShiftMater.class)
				.setParameter("companyId", companyId)
				.setParameter("workTypeCd", workTypeCd)
				.setParameter("workTimeCd", workTimeCd)
				.getSingle(c->c.toDomain());
		return data;
	}

	@Override
	public boolean checkExists(String companyId, String workTypeCd, String workTimeCd) {
		return this.getByWorkTypeAndWorkTime(companyId, workTypeCd, workTimeCd).isPresent();
	}

	@Override
	public void insert(ShiftMaster shiftMater) {
		this.commandProxy().insert(KshmtShiftMater.toEntity(shiftMater));
	}

	@Override
	public void update(ShiftMaster shiftMater) {
		KshmtShiftMater oldData = this.queryProxy().query(SELECT_BY_CD_AND_CID,KshmtShiftMater.class)
				.setParameter("companyId", shiftMater.getCompanyId())
				.setParameter("shiftMaterCode", shiftMater.getShiftMaterCode())
				.getSingle().get();
		KshmtShiftMater newData =KshmtShiftMater.toEntity(shiftMater);
		oldData.name = newData.name;
		oldData.color = newData.color;
		oldData.remarks = newData.remarks;
		oldData.workTypeCd = newData.workTypeCd;
		oldData.workTimeCd = newData.workTimeCd;
		this.commandProxy().update(oldData);
	}

	@Override
	public void delete(String companyId, String shiftMaterCode) {
		this.commandProxy().remove(KshmtShiftMater.class,new KshmtShiftMaterPK(companyId,shiftMaterCode));
		
	}

	@Override
	public List<ShiftMaster> getByListShiftMaterCd(String companyId, List<String> listShiftMaterCode) {
		if(listShiftMaterCode.isEmpty()) {
			return Collections.emptyList();
		}
		List<ShiftMaster> datas = this.queryProxy().query(SELECT_BY_LIST_CD_AND_CID,KshmtShiftMater.class)
				.setParameter("companyId", companyId)
				.setParameter("listShiftMaterCode", listShiftMaterCode)
				.getList(c->c.toDomain());
		return datas;
	}

}
