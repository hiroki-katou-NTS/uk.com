package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseUsedNumberRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.vacation.childcarenurse.KrcdtHdnursingUse;
import nts.uk.ctx.at.record.infra.entity.monthly.vacation.childcarenurse.KrcdtHdnursingUsePK;
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
		return null;
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

	/*介護、看護*/
	@Override
	public List<CareLeaveDataInfo> getAllCareInfoDataBysIdCps013(String cid, List<String> sids,
			Map<String, Object> enums) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void add(CareLeaveRemainingInfo obj, String cId) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void addAll(String cid, List<CareLeaveRemainingInfo> domains) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void update(CareLeaveRemainingInfo obj, String cId) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void updateAll(String cid, List<CareLeaveRemainingInfo> domains) {
		// TODO 自動生成されたメソッド・スタブ

	}

	private CareLeaveRemainingInfo toDomain(KrcdtHdNursingInfo entity) {
		//to do
		return null;
	}
	private KrcdtHdNursingInfo toEntity(CareLeaveRemainingInfo domain) {
		//to do
		return null;
	}
}
