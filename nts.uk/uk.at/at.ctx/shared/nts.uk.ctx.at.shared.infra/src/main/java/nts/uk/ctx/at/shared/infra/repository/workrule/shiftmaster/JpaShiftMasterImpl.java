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
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.dto.ShiftMasterDto;
import nts.uk.ctx.at.shared.infra.entity.workrule.shiftmaster.KshmtShiftMater;
import nts.uk.ctx.at.shared.infra.entity.workrule.shiftmaster.KshmtShiftMaterPK;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaShiftMasterImpl extends JpaRepository implements ShiftMasterRepository {
	private static final String SELECT_ALL = "SELECT c FROM KshmtShiftMater c ";

	private static final String SELECT_ALL_DTO = " SELECT " 
			+ " new nts.uk.ctx.at.shared.dom.workrule.shiftmaster.dto.ShiftMasterDto(c.kshmtShiftMaterPK.companyId, c.name, c.kshmtShiftMaterPK.shiftMaterCode, c.color, c.remarks, c.workTypeCd, wt.abbreviationName, wt.kshmtWorkTypePK.companyId, c.workTimeCd, wts.abname, wts.kshmtWorkTimeSetPK.cid ) "
			+ " FROM KshmtShiftMater c "
			+ " LEFT JOIN KshmtWorkType wt on c.workTypeCd = wt.kshmtWorkTypePK.workTypeCode and c.kshmtShiftMaterPK.companyId = wt.kshmtWorkTypePK.companyId "
			+ " LEFT JOIN KshmtWorkTimeSet wts on c.workTimeCd = wts.kshmtWorkTimeSetPK.worktimeCd and c.kshmtShiftMaterPK.companyId = wts.kshmtWorkTimeSetPK.cid "
			+ " WHERE c.kshmtShiftMaterPK.companyId = :companyId ";

	private static final String SELECT_BY_CID = SELECT_ALL
			+ " WHERE c.kshmtShiftMaterPK.companyId = :companyId";

	private static final String SELECT_BY_CD_AND_CID = SELECT_BY_CID
			+ " AND c.kshmtShiftMaterPK.shiftMaterCode = :shiftMaterCode";

	private static final String SELECT_BY_WORKTYPE_AND_WORKTIME = SELECT_BY_CID + " AND c.workTypeCd = :workTypeCd"
			+ " AND c.workTimeCd = :workTimeCd";

	private static final String SELECT_BY_LIST_CD_AND_CID = SELECT_ALL_DTO
			+ " AND c.kshmtShiftMaterPK.shiftMaterCode IN :listShiftMaterCode";

	@Override
	public List<ShiftMasterDto> getAllByCid(String companyId) {
		List<ShiftMasterDto> datas = this.queryProxy()
				.query(SELECT_ALL_DTO, ShiftMasterDto.class)
				.setParameter("companyId", companyId)
				.getList();
		return datas;
	}

	@Override
	public List<ShiftMasterDto> getAllDtoByCid(String companyId) {
		List<ShiftMasterDto> datas = this.queryProxy()
				.query(SELECT_ALL_DTO, ShiftMasterDto.class)
				.setParameter("companyId", companyId)
				.getList();
		return datas;
	}

	@Override
	public Optional<ShiftMaster> getByShiftMaterCd(String companyId, String shiftMaterCode) {
		Optional<ShiftMaster> data = this.queryProxy().query(SELECT_BY_CD_AND_CID, KshmtShiftMater.class)
				.setParameter("companyId", companyId)
				.setParameter("shiftMaterCode", shiftMaterCode)
				.getSingle(c -> c.toDomain());
		return data;
	}

	@Override
	public Optional<ShiftMaster> getByWorkTypeAndWorkTime(String companyId, String workTypeCd, String workTimeCd) {
		Optional<ShiftMaster> data = this.queryProxy().query(SELECT_BY_WORKTYPE_AND_WORKTIME, KshmtShiftMater.class)
				.setParameter("companyId", companyId).setParameter("workTypeCd", workTypeCd)
				.setParameter("workTimeCd", workTimeCd).getSingle(c -> c.toDomain());
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
		KshmtShiftMater oldData = this.queryProxy().query(SELECT_BY_CD_AND_CID, KshmtShiftMater.class)
				.setParameter("companyId", shiftMater.getCompanyId())
				.setParameter("shiftMaterCode", shiftMater.getShiftMasterCode()).getSingle().get();
		KshmtShiftMater newData = KshmtShiftMater.toEntity(shiftMater);
		oldData.name = newData.name;
		oldData.color = newData.color;
		oldData.remarks = newData.remarks;
		oldData.workTypeCd = newData.workTypeCd;
		oldData.workTimeCd = newData.workTimeCd;
		this.commandProxy().update(oldData);
	}

	@Override
	public void delete(String companyId, String shiftMaterCode) {
		this.commandProxy().remove(KshmtShiftMater.class, new KshmtShiftMaterPK(companyId, shiftMaterCode));

	}

	@Override
	public List<ShiftMasterDto> getByListShiftMaterCd(String companyId, List<String> listShiftMaterCode) {
		if (listShiftMaterCode.isEmpty()) {
			return Collections.emptyList();
		}
		List<ShiftMasterDto> datas = this.queryProxy().query(SELECT_BY_LIST_CD_AND_CID, ShiftMasterDto.class)
				.setParameter("companyId", companyId)
				.setParameter("listShiftMaterCode", listShiftMaterCode)
				.getList();
		return datas;
	}

}
