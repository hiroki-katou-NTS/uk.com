package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.NRHelper;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.StampCard;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.log.RogerFlagRQ;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.log.TopPageAlEmpInfoTerDetailRQ;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.log.TopPageAlarmEmpInfoTerRQ;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.log.TopPageAlarmManagerTrRQ;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.AppVacationReceptionData;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.AppWorkHolidayReceptionData;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.ApplicationCategory;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.ApplicationReceptionData;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
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

		Optional<EmpInfoTerminal> empInfoTerOpt = require.getEmpInfoTerminal(empInfoTerCode, contractCode);

		Optional<TimeRecordReqSetting> requestSetting = require.getTimeRecordReqSetting(empInfoTerCode, contractCode);

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

		try {
			Application obj = empInfoTerOpt.get().createApplication(requestSetting.get().getCompanyId().v(), recept,
					workTypeOpt, workingConItemOpt, stampCard.get().getEmployeeId());
			return createApplication(require, requestSetting.get().getCompanyId().v(), recept, obj);
		} catch (BusinessException bEx) {
			// ※処理にエラーがある場合、別の申請受信データの処理を続行、
			AtomTask atomTaskEx = AtomTask.of(() -> {

				Optional<TopPageAlarmEmpInfoTerRQ> alEmpTer = createLogEmpTer(require, stampCard.get().getEmployeeId(),
						requestSetting.get().getCompanyId().v(), empInfoTerCode, recept.getIdNumber(),
						bEx.getMessage());
				if (alEmpTer.isPresent())
					require.insertLogAll(alEmpTer.get());
			});
			return Optional.of(atomTaskEx);
		}
	}

	// [pvt-1] 就業情報端末通信用トップページアラームを作る
	private static Optional<TopPageAlarmEmpInfoTerRQ> createLogEmpTer(Require require, String sid, String companyId,
			String terCode, String cardNumber, String message) {
		List<String> lstSidApproval = require.getListEmpID(companyId, GeneralDate.today());

		if (lstSidApproval.isEmpty())
			return Optional.empty();

		List<TopPageAlarmManagerTrRQ> lstManagerTr = lstSidApproval.stream()
				.map(x -> new TopPageAlarmManagerTrRQ(x, RogerFlagRQ.ALREADY_READ)).collect(Collectors.toList());
		TopPageAlEmpInfoTerDetailRQ detail = new TopPageAlEmpInfoTerDetailRQ(0, message, new EmployeeId(sid),
				cardNumber);

		return Optional.of(new TopPageAlarmEmpInfoTerRQ(companyId, lstManagerTr, terCode, Arrays.asList(detail)));

	}

	private static <T extends ApplicationReceptionData> Optional<AtomTask> createApplication(Require require,
			String cid, T recept, Application object) {
		// if( require.申請Repository.get($申請.事前事後区分, $申請.入力日, $申請.申請日, $申請.申請種類,
		// $申請.申請者).isPresent)
		if (canCreateData(require, object)) {
			return Optional.of(AtomTask.of(() -> {
				// 申請を登録する
				RegisterApplicationFromNR.register(require, cid, object);
			}));
		}
		return Optional.empty();
	}

	private static boolean canCreateData(Require require, Application appNew) {
		List<Application> listAppNew = require.getApplication(appNew.getPrePostAtr(), appNew.getInputDate(),
				appNew.getAppDate().getApplicationDate(), appNew.getAppType(), appNew.getEmployeeID());
		if (listAppNew.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public static interface Require extends RegisterApplicationFromNR.Require {

		// [R-1] 就業情報端末を取得する
		public Optional<EmpInfoTerminal> getEmpInfoTerminal(String empInfoTerCode, String contractCode);

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

		// [R-7] タイムレコードのﾘｸｴｽﾄ設定を取得する
		public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(String empInfoTerCode, String contractCode);

		// [R-9] 就業担当者の社員ID（List）を取得する
		public List<String> getListEmpID(String companyID, GeneralDate referenceDate);

	}

}
