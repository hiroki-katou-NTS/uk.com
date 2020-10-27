package nts.uk.ctx.at.shared.infra.repository.workrule.shiftmaster;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ColorCodeChar6;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.Remarks;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterDisInfor;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterName;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterRepository;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.dto.ShiftMasterDto;
import nts.uk.ctx.at.shared.infra.entity.workrule.shiftmaster.KshmtShiftMater;
import nts.uk.ctx.at.shared.infra.entity.workrule.shiftmaster.KshmtShiftMaterPK;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaShiftMasterImpl extends JpaRepository implements ShiftMasterRepository {
	private static final String SELECT_ALL = "SELECT c FROM KshmtShiftMater c ";

	private static final String SELECT_ALL_DTO = " SELECT " 
			+ " new nts.uk.ctx.at.shared.dom.workrule.shiftmaster.dto.ShiftMasterDto(c.kshmtShiftMaterPK.companyId, c.name, c.kshmtShiftMaterPK.shiftMaterCode, c.color, c.remarks, c.workTypeCd, wt.name, wt.kshmtWorkTypePK.companyId, c.workTimeCd, wts.name, wts.kshmtWtPK.cid ) "
			+ " FROM KshmtShiftMater c "
			+ " LEFT JOIN KshmtWorkType wt on c.workTypeCd = wt.kshmtWorkTypePK.workTypeCode and c.kshmtShiftMaterPK.companyId = wt.kshmtWorkTypePK.companyId "
			+ " LEFT JOIN KshmtWt wts on c.workTimeCd = wts.kshmtWtPK.worktimeCd and c.kshmtShiftMaterPK.companyId = wts.kshmtWtPK.cid "
			+ " WHERE c.kshmtShiftMaterPK.companyId = :companyId ";

	private static final String SELECT_BY_CID = SELECT_ALL
			+ " WHERE c.kshmtShiftMaterPK.companyId = :companyId";

	private static final String SELECT_BY_CD_AND_CID = SELECT_BY_CID
			+ " AND c.kshmtShiftMaterPK.shiftMaterCode = :shiftMaterCode";
	
	private static final String SELECT_BY_LISTCD_AND_CID = SELECT_BY_CID
			+ " AND c.kshmtShiftMaterPK.shiftMaterCode IN :shiftMaterCodes";

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
	public List<ShiftMaster> getByListShiftMaterCd2(String companyId, List<String> shiftMaterCodes) {
		List<ShiftMaster> data = this.queryProxy().query(SELECT_BY_LISTCD_AND_CID, KshmtShiftMater.class)
				.setParameter("companyId", companyId)
				.setParameter("shiftMaterCodes", shiftMaterCodes)
				.getList(c -> c.toDomain());
		return data;
	}

	@Override
	public Optional<ShiftMaster> getByWorkTypeAndWorkTime(String companyId, String workTypeCd, String workTimeCd) {
		String sql = " SELECT * FROM KSHMT_SHIFT_MASTER WHERE  CID = ?  AND WORKTYPE_CD = ? AND WORKTIME_CD ";  
		sql = workTimeCd == null ? sql + " IS NULL ": sql + "= ?";
		try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
			stmt.setString(1, companyId);
			stmt.setString(2, workTypeCd);
			if(workTimeCd != null) {
				stmt.setString(3, workTimeCd);				
			}
			return new NtsResultSet(stmt.executeQuery()).getSingle(rec -> {
				return new ShiftMaster(rec.getString("CID"),
						new ShiftMasterCode(rec.getString("CD")),
						new ShiftMasterDisInfor(new ShiftMasterName(rec.getString("NAME")),
												new ColorCodeChar6(rec.getString("COLOR")),
													rec.getString("NOTE") == null ? null : new Remarks(rec.getString("NOTE"))),
													rec.getString("WORKTYPE_CD"),
													rec.getString("WORKTIME_CD"));
			});

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	
	
	}

	@Override
	public boolean checkExists(String companyId, String workTypeCd, String workTimeCd) {
		String sql = " SELECT count(*) FROM KSHMT_SHIFT_MASTER WHERE  CID = ?  AND WORKTYPE_CD = ? AND WORKTIME_CD ";  
		sql = workTimeCd.equals("") ? sql + " IS NULL ": sql + "= ?";
		try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
			stmt.setString(1, companyId);
			stmt.setString(2, workTypeCd);
			if(!workTimeCd.equals("")) {
				stmt.setString(3, workTimeCd);				
			}
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				if (result.getInt(1) > 0) {
					// co data
					return true;
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return false;
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

	@Override
	public boolean checkExistsByCd(String companyId, String shiftMaterCode) {
		return getByShiftMaterCd(companyId, shiftMaterCode).isPresent();
	}

	@Override
	public List<ShiftMaster> get(String companyID, List<WorkInformation> lstWorkInformation) {
		List<ShiftMaster> listData = new ArrayList<>();
		for(WorkInformation wi :lstWorkInformation ) {
			Optional<ShiftMaster> optSm =  getByWorkTypeAndWorkTime(companyID, wi.getWorkTypeCode()!=null?wi.getWorkTypeCode().v():null, 
					wi.getWorkTimeCode()!=null?wi.getWorkTimeCode().v():null);
			if(optSm.isPresent()) {
				listData.add(optSm.get());
			}
		}
		return listData;
	}

}
