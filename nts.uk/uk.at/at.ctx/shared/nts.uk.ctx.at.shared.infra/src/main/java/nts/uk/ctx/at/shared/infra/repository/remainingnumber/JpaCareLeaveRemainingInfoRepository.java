package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.CareUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.CareUsedNumberRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareUsedNumberRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveDataInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveRemainingInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.NursingCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.UpperLimitSetting;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareNurseUpperLimit;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.KrcdtHdNursingInfo;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.KrcdtHdNursingInfoPK;
import nts.uk.ctx.at.shared.infra.repository.remainingnumber.nursingcareleavemanagement.JpaChildCareNurseLevRemainInfoRepo;

/*
 * 介護用
 * */
@Stateless
public class JpaCareLeaveRemainingInfoRepository extends JpaChildCareNurseLevRemainInfoRepo
	implements CareLeaveRemainingInfoRepository{

	/*子の看護　基本情報*/
	@Inject
	ChildCareLeaveRemInfoRepository childCareLeaveRemInfoRepository;

	/*介護　使用数*/
	@Inject
	CareUsedNumberRepository careUsedNumberRepo;

	/*子の看護　使用数*/
	@Inject
	ChildCareUsedNumberRepository childCareUsedNumberRepo;


	/*介護*/
	@Override
	public Optional<CareLeaveRemainingInfo> getCareByEmpId(String empId) {

		Optional<KrcdtHdNursingInfo> krcdtHdNursingInfo
			= this.getByEmpIdAndNursingType(empId, NursingCategory.Nursing.value);

		if ( krcdtHdNursingInfo.isPresent() ) {
			return Optional.of(toDomain(krcdtHdNursingInfo.get()));
		} else {
			return Optional.empty();
		}
	}

	/*介護*/
	@Override
	public List<CareLeaveRemainingInfo> getCareByEmpIdsAndCid(String cid, List<String> empIds) {

		// 共通関数呼び出し
		List<NursingCareLeaveRemainingInfo> remaingInfoList
			= getDataByEmpIdsAndCidAndNursingCategory(cid, empIds, NursingCategory.Nursing);

		// 型変換
		List<CareLeaveRemainingInfo> careLeaveRemainingInfoList
			= remaingInfoList.stream().map(mapper->CareLeaveRemainingInfo.of(mapper)).collect(Collectors.toList());

		return careLeaveRemainingInfoList;
	}


	@Override
	public void add(CareLeaveRemainingInfo domain, String cId) {
		KrcdtHdNursingInfo entity = toEntity(domain, cId);
		this.commandProxy().insert(entity);
	}

	@Override
	public void addAll(String cid, List<CareLeaveRemainingInfo> domains) {

		// 型変換
		List<NursingCareLeaveRemainingInfo> commonDomains
			= domains.stream().map(e -> (NursingCareLeaveRemainingInfo)e).collect(Collectors.toList());

		// 共通関数呼び出し
		addAllList(cid, commonDomains);
	}

	@Override
	public void update(CareLeaveRemainingInfo domain, String cId) {
		KrcdtHdNursingInfoPK key = new KrcdtHdNursingInfoPK(domain.getSId(), NursingCategory.Nursing.value);
		Optional<KrcdtHdNursingInfo> entityOpt = this.queryProxy().find(key, KrcdtHdNursingInfo.class);
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

		// 型変換
		List<NursingCareLeaveRemainingInfo> commonDomains
			= domains.stream().map(e -> (NursingCareLeaveRemainingInfo)e).collect(Collectors.toList());

		// 共通関数呼び出し
		updateAllList(cid, commonDomains);
	}

	/*介護、看護*/
	@Override
	public Optional<CareLeaveDataInfo> getCareInfoDataBysId(String empId) {

		Optional<CareLeaveRemainingInfo> careRemaingInfo = this.getCareByEmpId(empId);
		Optional<CareUsedNumberData> careUsedInfo = this.careUsedNumberRepo.find(empId);
		Optional<ChildCareLeaveRemainingInfo> childCareRemaingInfo = this.childCareLeaveRemInfoRepository.getChildCareByEmpId(empId);
		Optional<ChildCareUsedNumberData> childCareUsedInfo = this.childCareUsedNumberRepo.find(empId);

		CareLeaveDataInfo dto = new CareLeaveDataInfo(
				careRemaingInfo.orElse(null),
				childCareRemaingInfo.orElse(null),
				careUsedInfo.orElse(null),
				childCareUsedInfo.orElse(null));
		return Optional.of(dto);
	}

	/*介護、看護*/
	@Override
	public List<CareLeaveDataInfo> getAllCareInfoDataBysId(String cid, List<String> sids) {
		List<CareLeaveRemainingInfo> careRemaingInfo = this.getCareByEmpIdsAndCid(cid, sids);
		List<ChildCareLeaveRemainingInfo> childCareRemaingInfo = this.childCareLeaveRemInfoRepository.getChildCareByEmpIdsAndCid(cid, sids);
		List<CareUsedNumberData>careUsedInfo = this.careUsedNumberRepo.find(sids);
		List<ChildCareUsedNumberData> childCareUsedInfo = this.childCareUsedNumberRepo.find(sids);;

		List<CareLeaveDataInfo> dtoList = new ArrayList<>();
		sids.stream().forEach(c->{
			Optional<CareLeaveRemainingInfo>careRemain = careRemaingInfo.stream().filter(a->a.getSId().equals(c)).findFirst();
			Optional<ChildCareLeaveRemainingInfo>childcareRemain = childCareRemaingInfo.stream().filter(a->a.getSId().equals(c)).findFirst();
			Optional<CareUsedNumberData>careUsed = careUsedInfo.stream().filter(a->a.getEmployeeId().equals(c)).findFirst();
			Optional<ChildCareUsedNumberData>childcareUsed = childCareUsedInfo.stream().filter(a->a.getEmployeeId().equals(c)).findFirst();

			if ((careRemain.isPresent() && careUsed.isPresent())  || (childcareRemain.isPresent() && childcareUsed.isPresent())) {
				dtoList.add(new CareLeaveDataInfo(
						careRemain.orElse(null),
						childcareRemain.orElse(null),
						careUsed.orElse(null),
						childcareUsed.orElse(null)
						));
			}
		});

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

					CareUsedNumberData careUsedNumberData = new CareUsedNumberData(rec.getString("DSID"));
					careUsedNumberData.setUsedDay(new DayNumberOfUse(rec.getDouble("DUSED_DAYS")));

					ChildCareLeaveRemainingInfo childCareLeaveRemainingInfo =  ChildCareLeaveRemainingInfo.createChildCareLeaveInfoCps013(
							rec.getString("CISID"),
							rec.getInt("CIUSE_ATR"),
							rec.getInt("CIUPPER_LIM_SET_ART"),
							rec.getInt("CIMAX_DAY_THIS_FISCAL_YEAR"),
							rec.getInt("CIMAX_DAY_NEXT_FISCAL_YEAR"));
							enums.put("IS00375", rec.getInt("CIUSE_ATR"));
							enums.put("IS00376", rec.getInt("CIUPPER_LIM_SET_ART"));

					ChildCareUsedNumberData childCareUsedNumberData = new ChildCareUsedNumberData(rec.getString("CDSID"));
					childCareUsedNumberData.setUsedDay(new DayNumberOfUse(rec.getDouble("CDUSED_DAYS")));

					return new CareLeaveDataInfo(leaveForCareInfo, childCareLeaveRemainingInfo, careUsedNumberData, childCareUsedNumberData);
				});
				result.addAll(data);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		return result;
	}
	
	
	
	@Override
	public void updateMaxDay(String cId, ChildCareNurseUpperLimit ThisFiscalYear) {
		KrcdtHdNursingInfoPK key = new KrcdtHdNursingInfoPK(cId, NursingCategory.Nursing.value);
		Optional<KrcdtHdNursingInfo> entityOpt = this.queryProxy().find(key, KrcdtHdNursingInfo.class);
		if (entityOpt.isPresent()) {
			KrcdtHdNursingInfo entity = entityOpt.get();
			entity.setCId(cId);
			entity.setMaxDayNextFiscalYear(ThisFiscalYear.v());
			entity.setMaxDayThisFiscalYear(null);
			this.commandProxy().update(entity);
		}
	}
	
	
	

	/**
	 * エンティティをドメインへ変換
	 * @param krcdtHdNursingInfo　エンティティ（KrcdtHdNursingInfoクラス）
	 * @return
	 */
	private CareLeaveRemainingInfo toDomain(KrcdtHdNursingInfo entity) {
		CareLeaveRemainingInfo childCareLeaveRemainingInfo
			= new CareLeaveRemainingInfo(
					entity.getPk().employeeId,
					EnumAdaptor.valueOf(entity.getPk().nursingType, NursingCategory.class),
					entity.getUseAtr()==1,
					EnumAdaptor.valueOf(entity.getUpperLimSetAtr(), UpperLimitSetting.class),
					entity.getMaxDayThisFiscalYear()==null ? Optional.empty() : Optional.of(new ChildCareNurseUpperLimit(entity.getMaxDayThisFiscalYear())),
					entity.getMaxDayNextFiscalYear()==null ? Optional.empty() : Optional.of(new ChildCareNurseUpperLimit(entity.getMaxDayNextFiscalYear()))
					);
		return childCareLeaveRemainingInfo;
	}

	/**
	 * ドメインをエンティティに変換
	 * @param domain ドメイン（ChildCareLeaveRemainingInfoクラス）
	 * @param cId　会社ID
	 * @return
	 */
	private KrcdtHdNursingInfo toEntity(CareLeaveRemainingInfo domain, String cId) {
		KrcdtHdNursingInfo krcdtHdNursingInfo
			= new KrcdtHdNursingInfo(
					new KrcdtHdNursingInfoPK(domain.getSId(), domain.getLeaveType().value),
					cId,
					domain.isUseClassification()?1:0,
					domain.getUpperlimitSetting().value,
					domain.getMaxDayForThisFiscalYear().map(mapper->mapper.v()).orElse(null),
					domain.getMaxDayForNextFiscalYear().map(mapper->mapper.v()).orElse(null)
					);
		return krcdtHdNursingInfo;
	}


}
