package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.ChildCareNurseUsedNumberRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.ChildCareLeaveRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.LeaveForCareData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.LeaveForCareDataRepo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveDataInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveRemainingInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.KrcdtHdNursingInfo;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.KrcdtHdNursingInfoPK;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.periodinformation.KshmtHdspGrantDeadlinePK;

/*
 * 介護用
 * */

public class JpaCareLeaveRemainingInfoRepository extends JpaRepository implements CareLeaveRemainingInfoRepository{

	/*子の看護　基本情報*/
	@Inject
	ChildCareLeaveRemInfoRepository childCareLeaveRemInfoRepository;

	/*介護　使用数*/
	@Inject
	LeaveForCareDataRepo leaveForCareDataRepo;

	/*子の看護　使用数*/
	ChildCareNurseUsedNumberRepository childCareNurseUsedNumberRepository;


	/*介護*/
	@Override
	public Optional<CareLeaveRemainingInfo> getCareByEmpId(String empId) {
		return this.queryProxy()
				.find(empId , KrcdtHdNursingInfo.class)
				.map(c -> toDomain(c));
	}

	/*介護*/
	@Override
	public List<CareLeaveRemainingInfo> getCareByEmpIdsAndCid(String cid, List<String> empIds) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	/*介護、看護*/
	@Override
	public Optional<CareLeaveDataInfo> getCareInfoDataBysId(String empId) {

		Optional<CareLeaveRemainingInfo> careRemaingInfo = this.getCareByEmpId(empId);
		Optional<LeaveForCareData>careUsedInfo = this.leaveForCareDataRepo.getCareByEmpId(empId);
		Optional<ChildCareLeaveRemainingInfo> childCareRemaingInfo = null;
		Optional<ChildCareLeaveRemainingData> childCareUsedInfo = null;

		CareLeaveDataInfo dto = new CareLeaveDataInfo(
				careRemaingInfo.orElse(null),
				careUsedInfo.orElse(null),
				childCareRemaingInfo.orElse(null),
				childCareUsedInfo.orElse(null));
		return Optional.of(dto);
	}

	/*介護、看護*/
	@Override
	public List<CareLeaveDataInfo> getAllCareInfoDataBysId(String cid, List<String> sids) {
		List<CareLeaveRemainingInfo> careRemaingInfo = this.getCareByEmpIdsAndCid(cid, sids);
		List<LeaveForCareData>careUsedInfo = this.leaveForCareDataRepo.getCareByEmpIds(cid, sids);
		List<ChildCareLeaveRemainingInfo> childCareRemaingInfo = null;
		List<ChildCareLeaveRemainingData> childCareUsedInfo = null;

		 List<CareLeaveDataInfo> dtoList = new ArrayList<>();

		return dtoList;
	}

	@Override
	public List<CareLeaveDataInfo> getAllCareInfoDataBysIdCps013(String cid, List<String> sids,
			Map<String, Object> enums) {

		List<CareLeaveDataInfo> result = new ArrayList<>();
		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			String sql = String.join(" ",
					"SELECT i.SID AS ISID, i.USE_ATR AS IUSE_ATR, i.UPPER_LIM_SET_ART AS IUPPER_LIM_SET_ART, i.MAX_DAY_THIS_FISCAL_YEAR AS IMAX_DAY_THIS_FISCAL_YEAR, i.MAX_DAY_NEXT_FISCAL_YEAR as IMAX_DAY_NEXT_FISCAL_YEAR,",
					"d.SID AS DSID, d.USED_DAYS AS DUSED_DAYS,",
					"ci.SID AS CISID, ci.USE_ATR AS CIUSE_ATR, ci.UPPER_LIM_SET_ART AS CIUPPER_LIM_SET_ART, ci.MAX_DAY_THIS_FISCAL_YEAR AS CIMAX_DAY_THIS_FISCAL_YEAR, ci.MAX_DAY_NEXT_FISCAL_YEAR as CIMAX_DAY_NEXT_FISCAL_YEAR,",
					"cd.SID AS CDSID, cd.USED_DAYS as CDUSED_DAYS",
					"FROM KRCDT_HDNURSING_INFO i",
					"LEFT JOIN KRCDT_CARE_HD_REMAIN d",
					"ON i.SID = d.SID AND i.CID = CID_VAL",
					"LEFT JOIN KRCMT_CHILD_CARE_HD_INFO ci",
					"ON ci.SID = i.SID AND ci.CID = CID_VAL",
					"LEFT JOIN KRCDT_CHILDCARE_HD_REMAIN cd",
					"ON cd.SID = i.SID AND cd.CID = CID_VAL",
					"WHERE i.SID IN (",  NtsStatement.In.createParamsString(subList) + ")");
			sql = sql.replace("CID_VAL", "'"+ cid + "'");
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				//stmt.setString(1, cid);
				for (int i = 0; i < subList.size(); i++) {
					stmt.setString(1 + i, subList.get(i));
				}

				List<CareLeaveDataInfo> data = new NtsResultSet(stmt.executeQuery()).getList(rec -> {
					CareLeaveRemainingInfo leaveForCareInfo =  CareLeaveRemainingInfo.createCareLeaveInfoCps013(
							rec.getString("ISID"),
							rec.getInt("IUSE_ATR"),
							rec.getInt("IUPPER_LIM_SET_ART"),
							rec.getInt("IMAX_DAY_THIS_FISCAL_YEAR"),
							rec.getInt("IMAX_DAY_NEXT_FISCAL_YEAR"));

					enums.put("IS00380", rec.getInt("IUSE_ATR"));
					enums.put("IS00381", rec.getInt("IUPPER_LIM_SET_ART"));

					LeaveForCareData leaveForCareData = LeaveForCareData.getCareHDRemaining(rec.getString("DSID"), rec.getDouble("DUSED_DAYS"));

					ChildCareLeaveRemainingInfo childCareLeaveRemainingInfo =  ChildCareLeaveRemainingInfo.createChildCareLeaveInfoCps013(
							rec.getString("CISID"),
							rec.getInt("CIUSE_ATR"),
							rec.getInt("CIUPPER_LIM_SET_ART"),
							rec.getInt("CIMAX_DAY_THIS_FISCAL_YEAR"),
							rec.getInt("CIMAX_DAY_NEXT_FISCAL_YEAR"));
							enums.put("IS00375", rec.getInt("CIUSE_ATR"));
							enums.put("IS00376", rec.getInt("CIUPPER_LIM_SET_ART"));

					ChildCareLeaveRemainingData childCareLeaveRemainingData = ChildCareLeaveRemainingData.getChildCareHDRemaining(rec.getString("CDSID"), rec.getDouble("CDUSED_DAYS"));
					return new CareLeaveDataInfo(leaveForCareInfo, leaveForCareData, childCareLeaveRemainingInfo , childCareLeaveRemainingData);
				});
				result.addAll(data);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		return result;
	}

	@Override
	public void add(CareLeaveRemainingInfo domain, String cId) {
		KrcdtHdNursingInfo entity = toEntity(domain, cId);
		this.commandProxy().insert(entity);
	}

	@Override
	public void addAll(String cid, List<CareLeaveRemainingInfo> domains) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void update(CareLeaveRemainingInfo domain, String cId) {
		KrcdtHdNursingInfoPK nursingInfoPK = new KrcdtHdNursingInfoPK(
				domain.getSId(),
				domain.getLeaveType().value);
		Optional<KrcdtHdNursingInfo> entityOpt = this.queryProxy().find(nursingInfoPK, KrcdtHdNursingInfo.class);
		if (entityOpt.isPresent()) {
			KrcdtHdNursingInfo entity = entityOpt.get();
			entity.setCId(cId);
			entity.setUseAtr(domain.isUseClassification() ? 1 : 0);
			entity.setUpperLimSetAtr(domain.getUpperlimitSetting().value);
			entity.setMaxDayNextFiscalYear(
					domain.getMaxDayForNextFiscalYear().isPresent() ? domain.getMaxDayForNextFiscalYear().get().v() : null);
			entity.setMaxDayThisFiscalYear(
					domain.getMaxDayForThisFiscalYear().isPresent() ? domain.getMaxDayForThisFiscalYear().get().v() : null);

			this.commandProxy().update(entity);
		}
	}

	@Override
	public void updateAll(String cid, List<CareLeaveRemainingInfo> domains) {
		// TODO 自動生成されたメソッド・スタブ

	}

	private CareLeaveRemainingInfo toDomain(KrcdtHdNursingInfo entity, String cId) {
		//to do
		return null;
	}

	private KrcdtHdNursingInfo toEntity(CareLeaveRemainingInfo domain, String cId) {
		KrcdtHdNursingInfo entity = new KrcdtHdNursingInfo(
				new KrcdtHdNursingInfoPK(domain.getSId(), domain.getLeaveType().value),
				cId,
				domain.isUseClassification() ? 1 : 0,
				domain.getUpperlimitSetting().value,
				domain.getMaxDayForThisFiscalYear().isPresent() ? domain.getMaxDayForThisFiscalYear().get().v() : null,
				domain.getMaxDayForNextFiscalYear().isPresent() ? domain.getMaxDayForNextFiscalYear().get().v() : null);

		return entity;
	}

}
