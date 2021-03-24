package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.substitutionholiday.updateremainnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.enums.EnumAdaptor;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.UnbalanceCompensation;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.HolidayAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataDaysAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT - <<Work>> 振休残数更新
 *
 */

public class RemainSubstitutionHolidayUpdating {

	// 振休残数更新
	public static AtomTask updateRemainSubstitutionHoliday(RequireM5 require, List<AccumulationAbsenceDetail> lstAbsRecMng,AggrPeriodEachActualClosure period, String empId) {
		List<AtomTask> atomTasks = new ArrayList<>();
		
		String companyId = AppContexts.user().companyId();
		
		deletePayoutManaData(require, period.getPeriod(), empId).ifPresent(at -> atomTasks.add(at));
		
		atomTasks.addAll(updatePayoutMngData(require, companyId, lstAbsRecMng));
		
		deleteSubManaData(require, period.getPeriod(), empId).ifPresent(at -> atomTasks.add(at));
		
		atomTasks.addAll(updateSubstitutionHolidayMngData(require, companyId, lstAbsRecMng));
		
		return AtomTask.bundle(atomTasks);
	}
	
	//ドメインモデル 「振出管理データ」 を削除する
	public static Optional<AtomTask> deletePayoutManaData(RequireM4 require, DatePeriod period, String empId){
		List<PayoutManagementData> managementDatas = require.payoutManagementData(empId, false, period);
		
		if (CollectionUtil.isEmpty(managementDatas))
			return Optional.empty();
		
		List<String> payoutId = managementDatas.stream().map(r -> r.getPayoutId()).collect(Collectors.toList());
		
		return Optional.of(AtomTask.of(() -> require.deletePayoutManagementData(payoutId)));
	}
	
	//ドメインモデル 「振休管理データ」 を削除する
	public static Optional<AtomTask> deleteSubManaData(RequireM3 require, DatePeriod period, String empId){
		List<SubstitutionOfHDManagementData> ofHDManagementDatas = require.substitutionOfHDManagementData(empId, false, period);
		
		if (CollectionUtil.isEmpty(ofHDManagementDatas))
			return Optional.empty();
		
		List<String> subOfHDID = ofHDManagementDatas.stream().map(r -> r.getSubOfHDID()).collect(Collectors.toList());
		
		return Optional.of(AtomTask.of(() -> require.deleteSubstitutionOfHDManagementData(subOfHDID)));
	}

	// 振出管理データの更新
	private static List<AtomTask> updatePayoutMngData(RequireM2 require, 
			String companyId, List<AccumulationAbsenceDetail> lstAbsRecMng) {
		List<AtomTask> atomTasks = new ArrayList<>();
		
		if (CollectionUtil.isEmpty(lstAbsRecMng))
			return atomTasks;
		
		lstAbsRecMng = lstAbsRecMng.stream()
				.filter(a -> a.getOccurrentClass() == OccurrenceDigClass.OCCURRENCE)
				.collect(Collectors.toList());
		
		if (CollectionUtil.isEmpty(lstAbsRecMng))
			return atomTasks;
		
		for (AccumulationAbsenceDetail data : lstAbsRecMng) {
			UnbalanceCompensation unUseOfRec = (UnbalanceCompensation)data;
			Optional<PayoutManagementData> optPayout = require.payoutManagementData(unUseOfRec.getManageId());
			if (optPayout.isPresent()) {
				// update
				PayoutManagementData payoutDataOld = optPayout.get();
				PayoutManagementData payoutData = new PayoutManagementData(payoutDataOld.getPayoutId(),
						payoutDataOld.getCID(), payoutDataOld.getSID(), payoutDataOld.getPayoutDate(),
						unUseOfRec.getDeadline(),
						EnumAdaptor.valueOf(unUseOfRec.getLegalInExClassi().value, HolidayAtr.class),
						new ManagementDataDaysAtr(unUseOfRec.getNumberOccurren().getDay().v()),
						new ManagementDataRemainUnit(unUseOfRec.getUnbalanceNumber().getDay().v()), 
						unUseOfRec.getDigestionCate(),
						unUseOfRec.getExtinctionDate());
				
				atomTasks.add(AtomTask.of(() -> require.updatePayoutManagementData(payoutData)));
			} else {
				// insert
				PayoutManagementData payoutData = new PayoutManagementData(unUseOfRec.getManageId(), companyId,
						data.getEmployeeId(), data.getDateOccur(), unUseOfRec.getDeadline(),
						EnumAdaptor.valueOf(unUseOfRec.getLegalInExClassi().value, HolidayAtr.class),
						new ManagementDataDaysAtr(unUseOfRec.getNumberOccurren().getDay().v()),
						new ManagementDataRemainUnit(unUseOfRec.getUnbalanceNumber().getDay().v()), 
						unUseOfRec.getDigestionCate(),
						unUseOfRec.getExtinctionDate());
				atomTasks.add(AtomTask.of(() -> require.createPayoutManagementData(payoutData)));
			}
		}
		
		return atomTasks;
	}

	// 振休管理データの更新
	private static List<AtomTask> updateSubstitutionHolidayMngData(RequireM1 require, 
			String companyId, List<AccumulationAbsenceDetail> lstAbsRecMng) {
		//大塚カスタマイズ　振休残数がマイナスの場合でも、常にクリアする。
		boolean isOotsuka = true;
		
		List<AtomTask> atomTasks = new ArrayList<>();
		
		if (CollectionUtil.isEmpty(lstAbsRecMng))
			return atomTasks;
		
		lstAbsRecMng = lstAbsRecMng.stream()
				.filter(a -> a.getOccurrentClass() == OccurrenceDigClass.DIGESTION)
				.collect(Collectors.toList());
		
		if (CollectionUtil.isEmpty(lstAbsRecMng))
			return atomTasks;
		
		for (AccumulationAbsenceDetail data : lstAbsRecMng) {
			AccumulationAbsenceDetail unOffsetOfAb = data;
			Optional<SubstitutionOfHDManagementData> optSubData = require
						.substitutionOfHDManagementData(unOffsetOfAb.getManageId());
			if (optSubData.isPresent()) {
				// update
				SubstitutionOfHDManagementData substitutionData = optSubData.get();
				substitutionData.setRequiredDays(new ManagementDataDaysAtr(unOffsetOfAb.getNumberOccurren().getDay().v()));
				substitutionData.setRemainsDay(isOotsuka ? 0 : unOffsetOfAb.getUnbalanceNumber().getDay().v());
				atomTasks.add(AtomTask.of(() -> require.updateSubstitutionOfHDManagementData(substitutionData)));
			} else {
				// insert
				SubstitutionOfHDManagementData substitutionData = new SubstitutionOfHDManagementData(
						unOffsetOfAb.getManageId(), companyId, data.getEmployeeId(), data.getDateOccur(),
						new ManagementDataDaysAtr(unOffsetOfAb.getNumberOccurren().getDay().v()),
						new ManagementDataRemainUnit(isOotsuka ? 0 : unOffsetOfAb.getUnbalanceNumber().getDay().v()));
				atomTasks.add(AtomTask.of(() -> require.createSubstitutionOfHDManagementData(substitutionData)));
			}
		}
		
		return atomTasks;
	}

	public static interface RequireM5 extends RequireM1, RequireM2, RequireM3, RequireM4 {}
	
	public static interface RequireM4 {
		
		List<PayoutManagementData> payoutManagementData(String sid, Boolean unknownDate, DatePeriod dayoffDate);
		
		void deletePayoutManagementData(List<String> payoutId);
	}
	
	public static interface RequireM3 {
		
		List<SubstitutionOfHDManagementData> substitutionOfHDManagementData(String sid, Boolean unknownDate, DatePeriod dayoffDate);
		
		void deleteSubstitutionOfHDManagementData(List<String> subOfHDID);
	}
	
	public static interface RequireM2 {
		
		Optional<PayoutManagementData> payoutManagementData(String Id);
		
		void updatePayoutManagementData(PayoutManagementData domain);
		
		void createPayoutManagementData(PayoutManagementData domain);
	}
	
	public static interface RequireM1 {
		
		Optional<SubstitutionOfHDManagementData> substitutionOfHDManagementData(String Id);
		
		void updateSubstitutionOfHDManagementData(SubstitutionOfHDManagementData domain);
		
		void createSubstitutionOfHDManagementData(SubstitutionOfHDManagementData domain);
	}
}
