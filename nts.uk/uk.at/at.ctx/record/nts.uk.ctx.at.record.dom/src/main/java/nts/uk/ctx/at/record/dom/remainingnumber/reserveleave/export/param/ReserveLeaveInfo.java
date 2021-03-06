package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveNumberInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.RemNumShiftListWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.LeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveOverNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpResereLeaveMng;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.erroralarm.ReserveLeaveError;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.GrantBeforeAfterAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave.ReserveLeaveRemainingNumberInfo;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPriority;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.export.GetUpperLimitSetting;

/**
 * ??????????????????
 * 
 * @author shuichi_ishida
 */
@Getter
@Setter
public class ReserveLeaveInfo implements Cloneable {

	/** ????????? */
	private GeneralDate ymd;
	/** ?????? */
	private ReserveLeaveRemainingNumberInfo remainingNumber;
	/** ????????????????????? */
	private List<ReserveLeaveGrantRemainingData> grantRemainingList;
	/** ????????? */
	private ReserveLeaveUsedDayNumber usedDays;
	/** ???????????? */
	private Optional<ReserveLeaveGrantInfo> grantInfo;

	/**
	 * ?????????????????????
	 */
	public ReserveLeaveInfo() {

		this.ymd = GeneralDate.min();
		this.remainingNumber = new ReserveLeaveRemainingNumberInfo();
		this.grantRemainingList = new ArrayList<>();
		this.usedDays = new ReserveLeaveUsedDayNumber(0.0);
		this.grantInfo = Optional.empty();
	}

	/**
	 * ??????????????????
	 * 
	 * @param ymd
	 *            ?????????
	 * @param remainingNumber
	 *            ??????
	 * @param grantRemainingList
	 *            ?????????????????????
	 * @param usedDays
	 *            ?????????
	 * @param afterGrantAtr
	 *            ??????????????????
	 * @param grantInfo
	 *            ????????????
	 * @return ??????????????????
	 */
	public static ReserveLeaveInfo of(GeneralDate ymd, ReserveLeaveRemainingNumberInfo remainingNumber,
			List<ReserveLeaveGrantRemainingData> grantRemainingList, ReserveLeaveUsedDayNumber usedDays,
			Optional<ReserveLeaveGrantInfo> grantInfo) {

		ReserveLeaveInfo domain = new ReserveLeaveInfo();
		domain.ymd = ymd;
		domain.remainingNumber = remainingNumber;
		domain.grantRemainingList = grantRemainingList;
		domain.usedDays = usedDays;
		domain.grantInfo = grantInfo;
		return domain;
	}

	@Override
	public ReserveLeaveInfo clone() {
		ReserveLeaveInfo cloned = new ReserveLeaveInfo();
		try {
			cloned.ymd = this.ymd;
			cloned.remainingNumber = this.remainingNumber.clone();
			for (val grantRemainingNumber : this.grantRemainingList) {
				val detail = grantRemainingNumber.getDetails();
				Double overLimitDays = null;
				if (detail.getUsedNumber().getLeaveOverLimitNumber().isPresent()) {
					overLimitDays = detail.getUsedNumber().getLeaveOverLimitNumber().get().numberOverDays.v();
				}
				ReserveLeaveGrantRemainingData newRemainData = ReserveLeaveGrantRemainingData.createFromJavaType(
						grantRemainingNumber.getLeaveID(), grantRemainingNumber.getEmployeeId(),
						grantRemainingNumber.getGrantDate(), grantRemainingNumber.getDeadline(),
						grantRemainingNumber.getExpirationStatus().value, grantRemainingNumber.getRegisterType().value,
						detail.getGrantNumber().getDays().v(), detail.getUsedNumber().getDays().v(), overLimitDays,
						detail.getRemainingNumber().getDays().v());
				cloned.grantRemainingList.add(newRemainData);
			}
			cloned.usedDays = new ReserveLeaveUsedDayNumber(this.usedDays.v());
			if (this.grantInfo.isPresent()) {
				cloned.grantInfo = Optional.of(this.grantInfo.get().clone());
			}
		} catch (Exception e) {
			throw new RuntimeException("ReserveLeaveInfo clone error.");
		}
		return cloned;
	}

	public List<ReserveLeaveGrantRemainingData> getGrantRemainingNumberList() {
		return this.grantRemainingList.stream().map(c -> (ReserveLeaveGrantRemainingData) c)
				.collect(Collectors.toList());
	}

	/**
	 * ?????????????????????????????????
	 */
	public void updateRemainingNumber(GrantBeforeAfterAtr grantPeriodAtr) {
		this.remainingNumber.updateRemainingNumber(this.grantRemainingList, grantPeriodAtr);
	}

	
	/**
	 * ????????????
	 * @param require
	 * @param cacheCarrier
	 * @param companyId
	 * @param employeeId
	 * @param periodWorkList
	 * @param aggrPeriodWork
	 * @param tmpReserveLeaveMngs
	 * @param aggrResult
	 * @param annualPaidLeaveSet
	 * @param limit
	 * @return
	 */
	public AggrResultOfReserveLeave remainNumberProcess(RequireM1 require, CacheCarrier cacheCarrier, String companyId,
			String employeeId, RsvLeaAggrPeriodWork aggrPeriodWork, GrantBeforeAfterAtr grantBeforeAfterAtr,
			List<TmpResereLeaveMng> tmpReserveLeaveMngs, AggrResultOfReserveLeave aggrResult,
			AnnualPaidLeaveSetting annualPaidLeaveSet, UpperLimitSetting limit) {
		
		// ??????????????????????????????
		aggrResult = lapsedGrantDigest(require, cacheCarrier, companyId, employeeId,
				aggrPeriodWork, tmpReserveLeaveMngs, aggrResult, annualPaidLeaveSet, limit);
		
		//????????????
		aggrResult = lapsedProcess(aggrPeriodWork, aggrResult, grantBeforeAfterAtr);
		
		return aggrResult;
	}
	
	
	/**
	 * ??????????????????????????????
	 * 
	 * @param companyId
	 *            ??????ID
	 * @param employeeId
	 *            ??????ID
	 * @param aggrPeriodWork
	 *            ????????????????????????WORK
	 * @param tmpReserveLeaveMngs
	 *            ??????????????????????????????????????????
	 * @param isGetNextMonthData
	 *            ????????????????????????????????????
	 * @param aggrResult
	 *            ???????????????????????????
	 * @param getUpperLimitSetting
	 *            ??????????????????????????????
	 * @param annualPaidLeaveSet
	 *            ????????????
	 * @return ???????????????????????????
	 */
	private AggrResultOfReserveLeave lapsedGrantDigest(RequireM1 require, CacheCarrier cacheCarrier, String companyId,
			String employeeId, RsvLeaAggrPeriodWork aggrPeriodWork, List<TmpResereLeaveMng> tmpReserveLeaveMngs,
			AggrResultOfReserveLeave aggrResult, AnnualPaidLeaveSetting annualPaidLeaveSet,
			UpperLimitSetting limit) {

		// ?????????????????? ??? ?????????
		this.ymd = aggrPeriodWork.getPeriod().start();

		// ????????????
		aggrResult = this.grantProcess(require, cacheCarrier, companyId, employeeId, aggrPeriodWork, aggrResult,
				 limit);

		if (!aggrPeriodWork.getEndWork().isNextPeriodEndAtr()) {

			// ????????????
			aggrResult = this.digestProcess(require, companyId, employeeId, aggrPeriodWork, tmpReserveLeaveMngs,
					aggrResult, annualPaidLeaveSet);
		}

		// ????????????????????????????????????????????????????????????????????????????????????
		if (aggrPeriodWork.getEndWork().isPeriodEndAtr()) {

			// ????????????????????????????????????????????????????????????????????????????????? ??? ????????????????????????????????????
			aggrResult.setAsOfPeriodEnd(this.clone());
		}

		if (aggrPeriodWork.getEndWork().isNextPeriodEndAtr()) {
			// ???????????????????????????????????????????????????????????????????????????????????????????????? ??? ????????????????????????????????????
			aggrResult.setAsOfStartNextDayOfPeriodEnd(this.clone());
		}

		if (!aggrPeriodWork.getEndWork().isNextPeriodEndAtr()) {
			// ?????????????????? ??? ?????????
			this.ymd = aggrPeriodWork.getPeriod().end();
		}

		// ??????????????????????????????????????????
		return aggrResult;
	}

	// ???????????????????????????
	public boolean isFirstTimeGrant(RsvLeaAggrPeriodWork aggregatePeriodWork) {

		// ????????????????????????????????????
		if (!aggregatePeriodWork.getGrantWork().isGrantAtr()) {
			return false;
		}

		// ???????????????
		return aggregatePeriodWork.getGrantWork().getGrantNumber() == 1;
	}

	/**
	 * ????????????
	 * 
	 * @param aggrPeriodWork
	 *            ????????????????????????????????????WORK
	 * @param aggrResult
	 *            ???????????????????????????
	 * @return ???????????????????????????
	 */
	private AggrResultOfReserveLeave lapsedProcess(RsvLeaAggrPeriodWork aggrPeriodWork,
			AggrResultOfReserveLeave aggrResult, GrantBeforeAfterAtr grantPeriodAtr) {

		// ????????????????????????
		if (!aggrPeriodWork.getLapsedAtr().isLapsedAtr())
			return aggrResult;

		// ????????????????????????????????????
		val itrGrantRemainingNumber = this.grantRemainingList.listIterator();
		while (itrGrantRemainingNumber.hasNext()) {
			val grantRemainingNumber = itrGrantRemainingNumber.next();

			// ?????????=????????????????????????WORK????????????????????????????????????????????????????????????
			if (!grantRemainingNumber.getDeadline().equals(aggrPeriodWork.getPeriod().end())) {
				continue;
			}

			// ???????????????????????????????????????true??????????????????????????????
			if (grantRemainingNumber.isDummyData() == true)
				continue;

			// ?????????????????????????????????????????????????????????
			grantRemainingNumber.setExpirationStatus(LeaveExpirationStatus.EXPIRED);

			// ?????????????????????
			val targetUndigestNumber = this.remainingNumber.getReserveLeaveUndigestedNumber();
			val remainingNumber = grantRemainingNumber.getDetails().getRemainingNumber();
			targetUndigestNumber.addDays(remainingNumber.getDays().v());
		}


		// ?????????????????????????????????
		this.updateRemainingNumber(grantPeriodAtr);

		// ????????????????????????????????????????????????????????????????????????????????????????????????
		if (!aggrResult.getLapsed().isPresent())
			aggrResult.setLapsed(Optional.of(new ArrayList<>()));
		aggrResult.getLapsed().get().add(this.clone());

		// ??????????????????????????????????????????
		return aggrResult;
	}

	/**
	 * ????????????
	 * 
	 * @param companyId
	 *            ??????ID
	 * @param employeeId
	 *            ??????ID
	 * @param aggrPeriodWork
	 *            ????????????????????????WORK
	 * @param aggrResult
	 *            ???????????????????????????
	 * @param getUpperLimitSetting
	 *            ??????????????????????????????
	 * @return ???????????????????????????
	 */
	private AggrResultOfReserveLeave grantProcess(RequireM1 require, CacheCarrier cacheCarrier, String companyId,
			String employeeId, RsvLeaAggrPeriodWork aggrPeriodWork, AggrResultOfReserveLeave aggrResult,
			UpperLimitSetting limit) {

		// ????????????????????????????????????
		if (!aggrPeriodWork.getGrantWork().isGrantAtr())
			return aggrResult;


		if (!aggrPeriodWork.getGrantWork().getReserveLeaveGrant().isPresent()) 
			return aggrResult;
		

		// ??????????????????????????????????????????????????????
		val newRemainData = aggrPeriodWork.getGrantWork().getReserveLeaveGrant().get().toReserveLeaveGrantRemainingData(employeeId);


		// ?????????????????????????????????????????????????????????????????????????????????????????????
		this.grantRemainingList.add(newRemainData);

		// ?????????????????????????????????????????????
		this.grantInfo = aggrPeriodWork.getGrantWork().getReserveLeaveGrant().get().toReserveLeaveGrantInfo(grantInfo);

		// ?????????????????????????????????
		GrantBeforeAfterAtr grantPeriodAtr = aggrPeriodWork.getGrantWork().judgeGrantPeriodAtr();

		// ?????????????????????????????????
		this.updateRemainingNumber(grantPeriodAtr);

		// ???????????????????????????????????????????????????
		this.lapsedExcessReserveLeave(aggrPeriodWork, limit);

		// ??????????????????????????????????????????????????????????????????????????????
		if (!aggrResult.getAsOfGrant().isPresent())
			aggrResult.setAsOfGrant(Optional.of(new ArrayList<>()));
		aggrResult.getAsOfGrant().get().add(this.clone());

		// ??????????????????????????????????????????
		return aggrResult;
	}

	/**
	 * ???????????????????????????????????????????????????
	 * 
	 * @param aggrPeriodWork
	 *            ????????????????????????????????????WORK
	 */
	private void lapsedExcessReserveLeave(RsvLeaAggrPeriodWork aggrPeriodWork, UpperLimitSetting limit) {

		// ?????????????????????????????????????????????
		Integer maxDays = limit.getMaxDaysCumulation().v();
		val noMinus = this.remainingNumber.getReserveLeaveNoMinus();
		double totalRemain = noMinus.getRemainingNumberInfo().getRemainingNumber().getTotalRemainingDays().v();
		if (maxDays.doubleValue() < totalRemain) {

			// ?????????????????????????????? ???????????? ?????????????????? ?????????
			List<ReserveLeaveGrantRemainingData> remainingDatas = this.grantRemainingList.stream()
					.filter(c -> c.getExpirationStatus() == LeaveExpirationStatus.AVAILABLE
							&& aggrPeriodWork.getPeriod().contains(c.getGrantDate()))
					.collect(Collectors.toList());
			// ?????????
			Collections.sort(remainingDatas, new Comparator<ReserveLeaveGrantRemainingData>() {
				@Override
				public int compare(ReserveLeaveGrantRemainingData o1, ReserveLeaveGrantRemainingData o2) {
					int compGrant = o1.getGrantDate().compareTo(o2.getGrantDate());
					if (compGrant != 0)
						return compGrant;
					return o1.getDeadline().compareTo(o2.getDeadline());
				}
			});

			// ???????????????????????????????????? ??? ??????????????????
			double excessDays = totalRemain - maxDays.doubleValue();

			for (val remainingData : remainingDatas) {

				// ???????????????????????????????????????????????????
				val details = remainingData.getDetails();
				if (excessDays <= details.getRemainingNumber().getDays().v()) {
					details.getUsedNumber().leaveOverLimitNumber = Optional.of(new LeaveOverNumber(excessDays, 0));
					details.getRemainingNumber().add(new LeaveRemainingNumber(-excessDays, 0));
					excessDays = 0.0;
				} else {
					details.getUsedNumber().leaveOverLimitNumber = Optional
							.of(new LeaveOverNumber(details.getRemainingNumber().getDays().v(), 0));
					details.getRemainingNumber()
							.add(new LeaveRemainingNumber(-details.getRemainingNumber().getDays().v(), 0));
					excessDays -= details.getRemainingNumber().getDays().v();
				}

				// ???????????????????????????????????????????????????
				if (excessDays <= 0.0)
					break;
			}

			// ?????????????????????????????????
			GrantBeforeAfterAtr grantPeriodAtr = aggrPeriodWork.getGrantWork().judgeGrantPeriodAtr();

			// ?????????????????????????????????
			this.updateRemainingNumber(grantPeriodAtr);
		}
	}

	/**
	 * ????????????
	 * 
	 * @param companyId
	 *            ??????ID
	 * @param employeeId
	 *            ??????ID
	 * @param aggrPeriodWork
	 *            ????????????????????????WORK
	 * @param tmpReserveLeaveMngs
	 *            ??????????????????????????????????????????
	 * @param aggrResult
	 *            ???????????????????????????
	 * @param annualPaidLeaveSet
	 *            ????????????
	 * @return ???????????????????????????
	 */
	private AggrResultOfReserveLeave digestProcess(
			// RequireM1 require,
			LeaveRemainingNumber.RequireM3 require, String companyId, String employeeId,
			RsvLeaAggrPeriodWork aggrPeriodWork, List<TmpResereLeaveMng> tmpReserveLeaveMngs,
			AggrResultOfReserveLeave aggrResult, AnnualPaidLeaveSetting annualPaidLeaveSet) {

		// ????????????????????????????????????????????????????????????????????????
		if (aggrPeriodWork.getEndWork().isNextPeriodEndAtr()) {
			return aggrResult;
		}

		// ???????????????????????????????????????????????????????????????
		List<TmpResereLeaveMng> targetList = new ArrayList<>();
		for (val tmpReserveLeaveMng : tmpReserveLeaveMngs) {
			if (!aggrPeriodWork.getPeriod().contains(tmpReserveLeaveMng.getYmd()))
				continue;
			targetList.add(tmpReserveLeaveMng);
		}

		targetList.sort((a, b) -> a.getYmd().compareTo(b.getYmd()));

		// ?????????????????????????????????
		GrantBeforeAfterAtr grantPeriodAtr = aggrPeriodWork.getGrantWork().judgeGrantPeriodAtr();

		for (val tmpReserveLeaveMng : targetList) {

			// ???????????????????????????
			{
				// ?????????????????????WORK
				ReserveLeaveUsedNumber usedNumber = new ReserveLeaveUsedNumber();

				// ?????????????????????????????????
				List<LeaveGrantRemainingData> targetRemainingDatas = new ArrayList<>();
				for (val remainingData : this.grantRemainingList) {
					if (tmpReserveLeaveMng.getYmd().before(remainingData.getGrantDate()))
						continue;
					if (tmpReserveLeaveMng.getYmd().after(remainingData.getDeadline()))
						continue;
					targetRemainingDatas.add(remainingData);
				}

				// ???????????????????????????
				if (annualPaidLeaveSet.getAcquisitionSetting().annualPriority == AnnualPriority.FIFO) {

					// ????????????????????????????????? ???????????? ??????(DESC)???
					targetRemainingDatas.sort((a, b) -> -a.getGrantDate().compareTo(b.getGrantDate()));
				} else {

					// ??????????????????????????? ???????????? ??????(ASC)???
					targetRemainingDatas.sort((a, b) -> a.getGrantDate().compareTo(b.getGrantDate()));
				}

				// ?????????????????????
				LeaveUsedNumber leaveUsedNumber = new LeaveUsedNumber();
				leaveUsedNumber.setDays(new LeaveUsedDayNumber(tmpReserveLeaveMng.getUseDays().v()));

				// ????????????????????????
				LeaveUsedDayNumber days = new LeaveUsedDayNumber(leaveUsedNumber.getDays().v());

				ReserveLeaveUsedNumber addNumber = ReserveLeaveUsedNumber.of(days, Optional.empty(), Optional.empty(),
						Optional.empty());
				usedNumber.add(addNumber);

				// ?????????????????????????????????WORK????????????????????????
				RemNumShiftListWork remNumShiftListWork = new RemNumShiftListWork();

				// ??????????????????????????????????????????
				Optional<LeaveGrantRemainingData> dummyData = LeaveGrantRemainingData.digest(
						require,
						targetRemainingDatas,
						remNumShiftListWork,
						leaveUsedNumber,
						companyId,
						employeeId,
						aggrPeriodWork.getPeriod().start());
				
				if(dummyData.isPresent()){
					ReserveLeaveGrantRemainingData addData = ReserveLeaveGrantRemainingData.of(dummyData.get());
					this.grantRemainingList.add(addData);
				}

				// ????????????????????????????????????????????????
				{
					// ??????????????????????????????????????????????????????????????????????????????
					this.getRemainingNumber().getReserveLeaveWithMinus().getUsedNumber()
							.addUsedDays(usedNumber.getDays().v(), grantPeriodAtr);

					// ?????????????????????????????????
					this.updateRemainingNumber(grantPeriodAtr);
				}
			}
		}

		// ??????????????????????????????????????????
		{
			// ????????????????????????????????????
			val withMinus = this.remainingNumber.getReserveLeaveWithMinus();
			if (withMinus.getRemainingNumberInfo().getRemainingNumber().getTotalRemainingDays().v() < 0.0) {

				if (grantPeriodAtr.equals(GrantBeforeAfterAtr.AFTER_GRANT)) {

					// ?????????????????????????????????????????????????????????
					aggrResult.addError(ReserveLeaveError.SHORTAGE_RSVLEA_AFTER_GRANT);
				} else {

					// ?????????????????????????????????????????????????????????
					aggrResult.addError(ReserveLeaveError.SHORTAGE_RSVLEA_BEFORE_GRANT);
				}
			}
		}

		// ??????????????????????????????????????????
		return aggrResult;
	}

	/**
	 * ???????????????????????????????????????1???????????????????????????
	 * 
	 * @return ???????????????????????????
	 */
	public Optional<ReserveLeaveGrantRemainingData> createLeaveGrantRemainingShortageData() {

		// ?????????????????????????????????????????????????????????????????????(List)????????????
		List<ReserveLeaveGrantRemainingData> dummyRemainingList = this.getGrantRemainingList().stream()
				.filter(c -> c.isDummyData()).collect(Collectors.toList());

		if (dummyRemainingList.size() == 0) {
			return Optional.empty();
		}

		// ????????????????????????????????????????????????????????????????????????????????????????????????
		LeaveRemainingNumber leaveRemainingNumberTotal = new LeaveRemainingNumber();
		LeaveUsedNumber leaveUsedNumberTotal = new LeaveUsedNumber();
		dummyRemainingList.forEach(c -> {
			leaveRemainingNumberTotal.add(c.getDetails().getRemainingNumber());
			leaveUsedNumberTotal.add(c.getDetails().getUsedNumber());
		});

		// ????????????????????????????????????????????????????????????????????????????????????

		// ?????????1???????????????
		ReserveLeaveGrantRemainingData reserveLeaveGrantRemainingData
		= ReserveLeaveGrantRemainingData.of(dummyRemainingList.stream().findFirst().get());

		AnnualLeaveNumberInfo leaveNumberInfo = new AnnualLeaveNumberInfo();
		// ??????????????? ??? ??????????????????????????????
		leaveNumberInfo.setRemainingNumber(leaveRemainingNumberTotal);
		// ?????????????????? ??? ?????????????????????????????????
		leaveNumberInfo.setUsedNumber(leaveUsedNumberTotal);

		reserveLeaveGrantRemainingData.setDetails(leaveNumberInfo);
		

		return Optional.of(reserveLeaveGrantRemainingData);
	}

	/**
	 * ????????????????????????????????????????????????????????????????????????
	 */
	public void deleteDummy() {
		// ?????????????????????????????????????????????????????????List????????????
		List<ReserveLeaveGrantRemainingData> noDummyList = this.getGrantRemainingList().stream()
				.filter(c -> !c.isDummyData()).collect(Collectors.toList());
		this.setGrantRemainingList(noDummyList);
	}

	public static interface RequireM1 extends GetUpperLimitSetting.RequireM1, LeaveRemainingNumber.RequireM3 {

	}
}