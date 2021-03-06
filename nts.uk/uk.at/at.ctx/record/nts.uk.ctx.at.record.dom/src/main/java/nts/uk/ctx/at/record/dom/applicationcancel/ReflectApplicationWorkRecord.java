package nts.uk.ctx.at.record.dom.applicationcancel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.adapter.request.application.state.RCReflectStatusResult;
import nts.uk.ctx.at.record.dom.adapter.request.application.state.RCReflectedState;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationShare;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ReflectedStateShare;
import nts.uk.ctx.at.shared.dom.scherec.application.common.StampRequestModeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.AppRecordImageShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.cancellation.CreateApplicationReflectionHist;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.RCCreateDailyAfterApplicationeReflect;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.CorrectDailyAttendanceService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ChangeDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.CorrectionAttendanceRule;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerCompanySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyprocess.calc.CalculateOption;

/**
 * @author thanh_nx
 *
 *         [RQ665]????????????????????????????????????
 */
public class ReflectApplicationWorkRecord {

	public static Pair<RCReflectStatusResult, Optional<AtomTask>> process(Require require,  String cid, ApplicationShare application,
			GeneralDate date, RCReflectStatusResult reflectStatus, GeneralDateTime reflectTime, String execId) {
		// [input.??????.?????????????????????]???????????????
		GeneralDate dateTarget = date;
		Optional<Stamp> stamp = Optional.empty();
		if (application.getOpStampRequestMode().isPresent()
				&& application.getOpStampRequestMode().get() == StampRequestModeShare.STAMP_ONLINE_RECORD) {
			// ?????????????????????????????????????????????????????????
			Pair<Optional<GeneralDate>, Optional<Stamp>> dateOpt = GetTargetDateRecordApplication.getTargetDate(require,
					cid, (AppRecordImageShare) application);
			if (dateOpt.getLeft().isPresent()) {
				dateTarget = dateOpt.getLeft().get();
				stamp = dateOpt.getRight();
			}
		}

		// ??????????????????????????????(work??????????????????
		Optional<IntegrationOfDaily> domainDaily = require.findDaily(application.getEmployeeID(), dateTarget);
		if (!domainDaily.isPresent())
			return Pair.of(reflectStatus, Optional.empty());

		// input.????????????(work??????[????????????????????????(work)]??????????????????????????????
		IntegrationOfDaily domainBeforeReflect = createDailyDomain(require, domainDaily.get());

		// ????????????(???????????????Work?????????????????????????????????(work?????????????????????
		DailyRecordOfApplication dailyRecordApp = new DailyRecordOfApplication(new ArrayList<>(),
				ScheduleRecordClassifi.RECORD, createDailyDomain(require, domainDaily.get()));

		// ??????.????????????????????????????????????
		ChangeDailyAttendance changeAtt;
		if (application.getOpStampRequestMode().isPresent()
				&& application.getOpStampRequestMode().get() == StampRequestModeShare.STAMP_ONLINE_RECORD) {
			changeAtt = new ChangeDailyAttendance(true, true, false, false, ScheduleRecordClassifi.RECORD, true);
			/// ???????????????NR??????????????????????????? -- itemId
			TimeStampApplicationNRMode.process(require, cid, dateTarget,
					(AppRecordImageShare) application, dailyRecordApp, stamp, changeAtt);
		} else {
			/// ????????????????????????????????? in process
			val affterReflect = RCCreateDailyAfterApplicationeReflect.process(require, cid, application, dailyRecordApp, dateTarget);

			changeAtt = ChangeDailyAttendance.createChangeDailyAtt(affterReflect.getLstItemId(), ScheduleRecordClassifi.RECORD);
		}

		// ??????????????????????????? --- create default ???? sau xu ly phan anh check lai
		IntegrationOfDaily domainCorrect = CorrectionAttendanceRule.process(require, cid,
				dailyRecordApp, changeAtt);

		// ????????????????????????????????????????????????
		WorkInfoOfDailyAttendance workInfoAfter = CorrectDailyAttendanceService.correctFurikyu(require, cid,
				domainBeforeReflect.getWorkInformation(), domainCorrect.getWorkInformation());
		domainCorrect.setWorkInformation(workInfoAfter);
		if (domainCorrect instanceof DailyRecordOfApplication) {
			dailyRecordApp.setAttendanceBeforeReflect(
					((DailyRecordOfApplication) domainCorrect).getAttendanceBeforeReflect());
		}
		dailyRecordApp.setDomain(domainCorrect);
		
		// ???????????????????????????????????? -- co xu ly tinh toan khac ko hay cua lich
		List<IntegrationOfDaily> lstAfterCalc = require.calculateForRecord(CalculateOption.asDefault(),
				Arrays.asList(domainCorrect), Optional.empty());
		if (!lstAfterCalc.isEmpty()) {
			dailyRecordApp.setDomain(lstAfterCalc.get(0));
		}

		ReflectedStateShare before = EnumAdaptor.valueOf(reflectStatus.getReflectStatus().value, ReflectedStateShare.class);
		AtomTask task = AtomTask.of(() -> {
			//??????????????????????????????????????????????????????
			require.removeConfirmApproval(Arrays.asList(dailyRecordApp.getDomain().getDomain()));
			
			// ?????????????????????
			require.addAllDomain(dailyRecordApp.getDomain(), true);

			// ?????????????????????????????????
			CreateApplicationReflectionHist.create(require, application.getAppID(), ScheduleRecordClassifi.RECORD,
					dailyRecordApp, domainBeforeReflect, reflectTime, execId, before);

		});
		// [input.???????????????????????????]????????????????????????????????????
		reflectStatus.setReflectStatus(RCReflectedState.REFLECTED);

		return Pair.of(reflectStatus, Optional.of(task));
	}
	
	private static IntegrationOfDaily createDailyDomain(Require require, IntegrationOfDaily domainDaily) {
		DailyRecordToAttendanceItemConverter converter = require.createDailyConverter();
		converter.setData(domainDaily).employeeId(domainDaily.getEmployeeId()).workingDate(domainDaily.getYmd());
		return converter.toDomain();
	}

	public static interface Require extends GetTargetDateRecordApplication.Require,
	        CorrectionAttendanceRule.Require, CreateApplicationReflectionHist.Require,
			TimeStampApplicationNRMode.Require, RCCreateDailyAfterApplicationeReflect.Require, CorrectDailyAttendanceService.Require {

		// DailyRecordShareFinder
		public Optional<IntegrationOfDaily> findDaily(String employeeId, GeneralDate date);

		// ConvertDailyRecordToAd
		public DailyRecordToAttendanceItemConverter createDailyConverter();

		// CalculateDailyRecordServiceCenter
		public List<IntegrationOfDaily> calculateForRecord(CalculateOption calcOption,
				List<IntegrationOfDaily> integrationOfDaily, Optional<ManagePerCompanySet> companySet);

		// DailyRecordAdUpService
		public void addAllDomain(IntegrationOfDaily domain, boolean removeError);
		
		//DailyRecordAdUpService
		public void removeConfirmApproval(List<IntegrationOfDaily> domainDaily);
	}
}
