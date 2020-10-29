package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.service;

import java.util.List;
import java.util.Optional;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsence;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.ErrorNRCom;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.NRHelper;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.StampCard;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.AppVacationReceptionData;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.AppWorkHolidayReceptionData;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.ApplicationCategory;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.ApplicationReceptionData;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime_Old;
import nts.uk.ctx.at.request.dom.application.stamp.AppStamp;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * @author ThanhNX
 *
 *         データタイムレコードを申請に変換する
 */
public class ConvertTimeRecordApplicationService {

	private ConvertTimeRecordApplicationService() {
	};

	// [1] 変換する
	public static <T extends ApplicationReceptionData> Optional<AtomTask> converData(Require require,
			Integer empInfoTerCode, String contractCode, T recept) {

		Optional<EmpInfoTerminal> empInfoTerOpt = require.getEmpInfoTerminal(empInfoTerCode, contractCode);

		Optional<TimeRecordReqSetting> requestSetting = require.getTimeRecordReqSetting(empInfoTerCode);

		// $就業情報端末 = Optional.empty() || $タイムレコードのﾘｸｴｽﾄ設定 = Optional.empty()
		if (!empInfoTerOpt.isPresent() || !requestSetting.isPresent())
			return Optional.empty();

		// if 申請受信データ.申請区分 = ‘３’(休暇申請)
		Optional<WorkType> workTypeOpt = Optional.empty();
		if (recept.getApplicationCategory().equals(ApplicationCategory.VACATION.value)) {
			workTypeOpt = require.findByPK(requestSetting.get().getCompanyId().v(),
					((AppVacationReceptionData) recept).getWorkType());
		}

		Optional<StampCard> stampCard = require.getByCardNoAndContractCode(contractCode, recept.getIdNumber());
		if (!stampCard.isPresent())
			return Optional.empty();
//		if 申請受信データ.申請区分 = ‘３’(休暇申請)
		Optional<WorkingConditionItem> workingConItemOpt = Optional.empty();
		if (recept.getApplicationCategory().equals(ApplicationCategory.WORK_HOLIDAY.value)) {
			workingConItemOpt = require.getBySidAndStandardDate(stampCard.get().getEmployeeId(),
					NRHelper.createGeneralDate(((AppWorkHolidayReceptionData) recept).getAppYMD()));
		}
		Object obj = empInfoTerOpt.get().createApplication(requestSetting.get().getCompanyId().v(), recept, workTypeOpt,
				workingConItemOpt, stampCard.get().getEmployeeId());
		return createAppication(require, recept, obj);
		// TODO: ※処理にエラーがある場合、別の申請受信データの処理を続行、
		//return Optional.empty();
	}

	private static <T extends ApplicationReceptionData> Optional<AtomTask> createAppication(Require require, T recept,
			Object object) {
		ApplicationCategory cate = ApplicationCategory.valueStringOf(recept.getApplicationCategory());
		switch (cate) {

		// 打刻申請
		case STAMP:
//			if (canCreateData(require, ((AppStamp) object).getApplication_New())) {
//				return Optional.of(AtomTask.of(() -> {
//					require.insert((AppStamp) object);
//				}));
//			}
			// AppStamp
			return Optional.empty();

		// 残業申請
		case OVERTIME:
			// AppOverTime
//			if (canCreateData(require, ((AppOverTime) object).getApplication())) {
//				return Optional.of(AtomTask.of(() -> {
//					require.insert((AppOverTime) object);
//				}));
//			}
			return Optional.empty();

		// 休暇申請
		case VACATION:
			// AppAbsence
//			if (canCreateData(require, ((AppAbsence) object).getApplication())) {
//				return Optional.of(AtomTask.of(() -> {
//					require.insert((AppAbsence) object);
//				}));
//			}
			return Optional.empty();

		// 勤務変更申請
		case WORK_CHANGE:
			// AppWorkChange
			// TODO: sua thiet ke, chua sua code
//			if (canCreateData(require, ((AppWorkChange) object).getApplication_New())) {
//				return Optional.of(AtomTask.of(() -> {
//
//				}));
//			}
			return Optional.empty();

		// 休日出勤時間申請
		case WORK_HOLIDAY:
			// AppHolidayWork
//			if (canCreateData(require, ((AppHolidayWork) object).getApplication())) {
//				return Optional.of(AtomTask.of(() -> {
//					require.insert((AppHolidayWork) object);
//				}));
//			}
			return Optional.empty();

		// 遅刻早退取消申請
		case LATE:
			// LateOrLeaveEarly
//			if (canCreateData(require, ((LateOrLeaveEarly) object).getApplication())) {
//				return Optional.of(AtomTask.of(() -> {
//					require.insert((LateOrLeaveEarly) object);
//				}));
//			}
			return Optional.empty();

		// 時間年休申請
		case ANNUAL:
			// TODO: chua co domain
			return Optional.empty();

		default:
			return Optional.empty();
		}
	}

	private static boolean canCreateData(Require require, Application_New appNew) {
		List<Application_New> listAppNew = require.getApplication(appNew.getPrePostAtr(), appNew.getInputDate(),
				appNew.getAppDate(), appNew.getAppType(), appNew.getEmployeeID());
		if (listAppNew.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public static interface Require {

		// [R-1] 就業情報端末を取得する
		public Optional<EmpInfoTerminal> getEmpInfoTerminal(Integer empInfoTerCode, String contractCode);

		// [R-2] 勤務種類を取得
		public Optional<WorkType> findByPK(String companyId, String workTypeCd);

		// [R-3] 労働条件項目を取得
		public Optional<WorkingConditionItem> getBySidAndStandardDate(String employeeId, GeneralDate baseDate);

		// [R-4] 申請を取得する
		// 事前事後区分, 入力日, 申請日, 申請種類, 申請者
		public List<Application_New> getApplication(PrePostAtr prePostAtr, GeneralDateTime inputDate,
				GeneralDate appDate, ApplicationType appType, String employeeID);

		// [R-5] 打刻カードを取得する
		public Optional<StampCard> getByCardNoAndContractCode(String contractCode, String stampNumber);

		// [R-６] エラーNR-通信を作る
		public void insert(ErrorNRCom errorNR);

		// [R-7] タイムレコードのﾘｸｴｽﾄ設定を取得する
		public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(Integer empInfoTerCode);

		// [R-8] 打刻申請を作る
		public void insert(AppStamp appStamp);

		// [R-9] 残業申請を作る
		public void insert(AppOverTime_Old appOverTime);

		// [R-10] 休暇申請を作る
		public void insert(AppAbsence appAbsence);

		// [R-11] 勤務変更申請を作る
		public void insert(AppWorkChange appWorkChange);

		// [R-12] 休日出勤時間申請を作る
		public void insert(AppHolidayWork appHolidayWork);

		// [R-13] 遅刻早退取消申請を作る
		public void insert(LateOrLeaveEarly lateOrLeaveEarly);

		// [R-14] 時間年休申請を作る
		public void insert();

	}

}
