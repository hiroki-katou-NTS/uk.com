package nts.uk.ctx.at.shared.infra.repository.remainingnumber.nursingcareleavemanagement.info;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.NursingCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.UpperLimitSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareNurseUpperLimit;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.KrcdtHdNursingInfo;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.KrcdtHdNursingInfoPK;
import nts.uk.ctx.at.shared.infra.repository.remainingnumber.nursingcareleavemanagement.JpaChildCareNurseLevRemainInfoRepo;

@Stateless
public class JpaChildCareLevRemainInfoRepo extends JpaChildCareNurseLevRemainInfoRepo
	implements ChildCareLeaveRemInfoRepository {

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Optional<ChildCareLeaveRemainingInfo> getChildCareByEmpId(String empId) {

		Optional<KrcdtHdNursingInfo> krcdtHdNursingInfo
			= this.getByEmpIdAndNursingType(empId, NursingCategory.ChildNursing.value);

		if ( krcdtHdNursingInfo.isPresent() ) {
			return Optional.of(toDomain(krcdtHdNursingInfo.get()));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void add(ChildCareLeaveRemainingInfo domain, String cId) {
		KrcdtHdNursingInfo entity = toEntity(domain, cId);
		this.commandProxy().insert(entity);
	}

	@Override
	public void update(ChildCareLeaveRemainingInfo obj, String cId) {
		KrcdtHdNursingInfoPK key = new KrcdtHdNursingInfoPK(obj.getSId(), NursingCategory.ChildNursing.value);
		Optional<KrcdtHdNursingInfo> entityOpt = this.queryProxy().find(key, KrcdtHdNursingInfo.class);
		if (entityOpt.isPresent()) {
			KrcdtHdNursingInfo entity = entityOpt.get();
			entity.setCId(cId);
			entity.setUseAtr(obj.isUseClassification() ? 1 : 0);
			entity.setUpperLimSetAtr(obj.getUpperlimitSetting().value);
			entity.setMaxDayNextFiscalYear(
					obj.getMaxDayForNextFiscalYear().isPresent() ? obj.getMaxDayForNextFiscalYear().get().v() : null);
			entity.setMaxDayThisFiscalYear(
					obj.getMaxDayForThisFiscalYear().isPresent() ? obj.getMaxDayForThisFiscalYear().get().v() : null);
			this.commandProxy().update(entity);
		}
	}

	@Override
	public List<ChildCareLeaveRemainingInfo> getChildCareByEmpIdsAndCid(String cid, List<String> empIds) {

		// 共通関数呼び出し
		List<NursingCareLeaveRemainingInfo> remaingInfoList
			= getDataByEmpIdsAndCidAndNursingCategory(cid, empIds, NursingCategory.ChildNursing);

		// 型変換
		List<ChildCareLeaveRemainingInfo> childCareLeaveRemainingInfoList
			= remaingInfoList.stream().map(mapper->ChildCareLeaveRemainingInfo.of(mapper)).collect(Collectors.toList());

		return childCareLeaveRemainingInfoList;
	}

	@Override
	public void addAll(String cid, List<ChildCareLeaveRemainingInfo> domains) {

		// 型変換
		List<NursingCareLeaveRemainingInfo> commonDomains
			= domains.stream().map(e -> (NursingCareLeaveRemainingInfo)e).collect(Collectors.toList());

		// 共通関数呼び出し
		addAllList(cid, commonDomains);
	}

	@Override
	public void updateAll(String cid, List<ChildCareLeaveRemainingInfo> domains) {

		// 型変換
		List<NursingCareLeaveRemainingInfo> commonDomains
			= domains.stream().map(e -> (NursingCareLeaveRemainingInfo)e).collect(Collectors.toList());

		// 共通関数呼び出し
		updateAllList(cid, commonDomains);
	}
	
	/*
	 * (非 Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemInfoRepository#updateMaxDay(java.lang.String, nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareNurseUpperLimit, nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareNurseUpperLimit)
	 */
	@Override
	public void updateMaxDay(String sid, ChildCareNurseUpperLimit ThisFiscalYear) {
		KrcdtHdNursingInfoPK key = new KrcdtHdNursingInfoPK(sid, NursingCategory.ChildNursing.value);
		Optional<KrcdtHdNursingInfo> entityOpt = this.queryProxy().find(key, KrcdtHdNursingInfo.class);
		if (entityOpt.isPresent()) {
			KrcdtHdNursingInfo entity = entityOpt.get();
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
	private ChildCareLeaveRemainingInfo toDomain(KrcdtHdNursingInfo entity) {
		ChildCareLeaveRemainingInfo childCareLeaveRemainingInfo
			= new ChildCareLeaveRemainingInfo(
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
	private KrcdtHdNursingInfo toEntity(ChildCareLeaveRemainingInfo domain, String cId) {
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

