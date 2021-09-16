package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.adapter.employee.RQEmpDataImport;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.NRHelper;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.StampCard;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.log.TopPageAlarmEmpInfoTerRQ;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.AppStampReceptionData;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.AppVacationReceptionData;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.AppWorkHolidayReceptionData;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.ApplicationCategory;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.ApplicationReceptionData;
import nts.uk.ctx.at.shared.dom.WorkInfoAndTimeZone;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyInfo;
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
			String empInfoTerCode, String contractCode, T recept) {

		Optional<String> leavCategory = Optional.empty();
		if (recept.getApplicationCategory().equals(ApplicationCategory.STAMP.value)) {
			leavCategory = Optional.of(((AppStampReceptionData) recept).getStampType());
		}
		Optional<EmpInfoTerminal> empInfoTerOpt = require.getEmpInfoTerminal(empInfoTerCode, contractCode,
				leavCategory);

		// $就業情報端末 = Optional.empty() || $タイムレコードのﾘｸｴｽﾄ設定 = Optional.empty()
		if (!empInfoTerOpt.isPresent())
			return Optional.empty();

		Optional<String> sid = getEmployeeId(require, contractCode, recept.getIdNumber());
		if (!sid.isPresent())
			return Optional.empty();

		Optional<RQEmpDataImport> empData = sid
				.flatMap(info -> require.getEmpData(Arrays.asList(info)).stream().findFirst());
		Optional<String> cid = getCompanyId(require, empData);
		if (!cid.isPresent()) {
			return Optional.empty();
		}

		// ログイン
		login(require, contractCode, cid, empData);

		// if 申請受信データ.申請区分 = ‘３’(休暇申請)
		Optional<WorkType> workTypeOpt = Optional.empty();
		if (recept.getApplicationCategory().equals(ApplicationCategory.VACATION.value)) {
			workTypeOpt = require.findByPK(cid.get(), ((AppVacationReceptionData) recept).getWorkType());
			if (!workTypeOpt.isPresent())
				return Optional.empty();
		}

		// if 申請受信データ.申請区分 = ‘３’(休暇申請)
		Optional<WorkInfoAndTimeZone> workInfoAndTimeZone = Optional.empty();
		if (recept.getApplicationCategory().equals(ApplicationCategory.WORK_HOLIDAY.value)) {
			Optional<WorkingConditionItem>  workingConItemOpt = require.getBySidAndStandardDate(sid.get(),
					NRHelper.createGeneralDate(((AppWorkHolidayReceptionData) recept).getAppYMD()));
			workInfoAndTimeZone = require.getWorkInfo(cid.get(), sid.get(),
					NRHelper.createGeneralDate(((AppWorkHolidayReceptionData) recept).getAppYMD()), workingConItemOpt);
			if(!workingConItemOpt.isPresent() || !workInfoAndTimeZone.isPresent()) {
				return Optional.empty();
			}
		}

		Application obj = empInfoTerOpt.get().createApplication(cid.get(), recept, workTypeOpt, workInfoAndTimeZone,
				sid.get());
		return createApplication(require, cid.get(), contractCode, recept, obj);
	}

	// 申請を登録する
	private static <T extends ApplicationReceptionData> Optional<AtomTask> createApplication(Require require,
			String cid, String contractCode, T recept, Application application) {
		// if( require.申請Repository.get($申請.事前事後区分, $申請.入力日, $申請.申請日, $申請.申請種類,
		// $申請.申請者).isPresent)
		if (canCreateData(require, application)) {
			return Optional.of(AtomTask.of(() -> {
				// 申請を登録する
				RegisterApplicationFromNR.register(require, cid, contractCode, application);
				
				require.loggedOut();
			}));
		}
		return Optional.empty();
	}

	// 申請の登録かどうか確認する
	private static boolean canCreateData(Require require, Application appNew) {
		List<Application> listAppNew = require.getApplication(appNew.getPrePostAtr(), appNew.getInputDate(),
				appNew.getAppDate().getApplicationDate(), appNew.getAppType(), appNew.getEmployeeID());
		if (listAppNew.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	// 社員IDを取得する
	private static Optional<String> getEmployeeId(Require require, String contractCode, String stampNumber) {
		Optional<StampCard> stampCard = require.getByCardNoAndContractCode(contractCode, stampNumber);
		return stampCard.map(x -> x.getEmployeeId());
	}

	// 会社IDを取得する
	private static Optional<String> getCompanyId(Require require, Optional<RQEmpDataImport> empData) {
		return empData.map(x -> x.getCompanyId());
	}

	// ログイン
	private static boolean login(Require require, String contractCode, Optional<String> cid,
			Optional<RQEmpDataImport> empData) {

		if (!cid.isPresent() || !empData.isPresent()) {
			return false;
		}
		Optional<String> userId = require.getUserIdFromLoginId(empData.get().getPersonId());
		if (!userId.isPresent()) {
			return false;
		}
		String companyCode = require.getCompanyInfoById(cid.get()).getCompanyCode();

		require.loggedInAsEmployee(userId.get(), empData.get().getPersonId(), contractCode, cid.get(), companyCode,
				empData.get().getEmployeeId(), empData.get().getEmployeeCode());
		return true;

	}

	public static interface Require extends RegisterApplicationFromNR.Require {

		// [R-1] 就業情報端末を取得する
		public Optional<EmpInfoTerminal> getEmpInfoTerminal(String empInfoTerCode, String contractCode,
				Optional<String> leavCategory);

		// [R-2] 勤務種類を取得
		public Optional<WorkType> findByPK(String companyId, String workTypeCd);

		// [R-3] 労働条件項目を取得
		public Optional<WorkingConditionItem> getBySidAndStandardDate(String employeeId, GeneralDate baseDate);

		// [R-4] 申請を取得する
		// 事前事後区分, 入力日, 申請日, 申請種類, 申請者
		public List<Application> getApplication(PrePostAtr prePostAtr, GeneralDateTime inputDate, GeneralDate appDate,
				ApplicationType appType, String employeeID);

		// [R-5] 打刻カードを取得する
		public Optional<StampCard> getByCardNoAndContractCode(String contractCode, String stampNumber);

//		// [R-６] エラーNR-通信を作る
//		public void insert(ErrorNRCom errorNR);

		// [R-6] 就業情報端末通信用トップページアラームを作る
		public void insertLogAll(TopPageAlarmEmpInfoTerRQ alEmpInfo);

		// [R-7] 就業担当者の社員ID（List）を取得する
		public List<String> getListEmpID(String companyID, GeneralDate referenceDate);

		// [R-8] 社員IDListから管理情報を取得する
		// GetMngInfoFromEmpIDListAdapter
		List<RQEmpDataImport> getEmpData(List<String> empIDList);

		// [R-9]会社IDを取得する
		// CompanyAdapter
		CompanyInfo getCompanyInfoById(String companyId);

		// [R-10] UserIDを取得する
		// IGetInfoForLogin
		public Optional<String> getUserIdFromLoginId(String perId);

		// [R-11]ログイン
		// LoginUserContextManager
		void loggedInAsEmployee(String userId, String personId, String contractCode, String companyId,
				String companyCode, String employeeId, String employeeCode);

		//[R-12] ログアウト
		void loggedOut();
		
		// [R-13] 実績と予定と労働条件データかで勤務情報と補正済み所定時間帯を取得する
		public Optional<WorkInfoAndTimeZone> getWorkInfo(String cid, String employeeId, GeneralDate baseDate,
				Optional<WorkingConditionItem> workItem);

	}

}
