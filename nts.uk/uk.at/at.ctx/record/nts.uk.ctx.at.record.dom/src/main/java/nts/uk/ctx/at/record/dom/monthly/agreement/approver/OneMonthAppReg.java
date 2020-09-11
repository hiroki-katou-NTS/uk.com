package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.agreementbasicsettings.AgreementsOneMonth;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.AgeementTimeCommonSetting;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.SpecialProvisionsOfAgreement;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.TypeAgreementApplication;
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSetting;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementDomainService;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.ErrorTimeInMonth;
// import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.OneMonthTime;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.OneMonthTime;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.shr.com.context.AppContexts;
import org.apache.commons.lang3.tuple.Pair;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;

/**
 * 1ヶ月申請を登録する
 * 1ヶ月の36協定特別条項の適用申請を作成する
 *
 * @author khai.dh
 */
@Stateless
public class OneMonthAppReg {
	@Inject
	private ApproverGetDomainService apprService;

	/**
	 * [1] 作成する
	 * 1ヶ月の36協定特別条項の適用申請を作成する
	 *
	 * @param require
	 * @param cid
	 * @param applicant
	 * @param appContent  申請内容: 月間の申請内容
	 * @param displayInfo 画面表示情報
	 */
	public AppCreationResult create(Require require,
									String cid,
									String applicant,
									MonthlyAppContent appContent,
									ScreenDisplayInfo displayInfo) {

		Optional<ApproverItem> approverItem = apprService.getApprover(require, appContent.getApplicant());
		if (!approverItem.isPresent()) {
			return new AppCreationResult(
					appContent.getApplicant(),
					ResultType.APPROVER_NOT_SET,
					Optional.empty(),
					Optional.empty(),
					Optional.empty()
			);
		}
		val agrTimeSettingService = new AgeementTimeCommonSetting();
		BasicAgreementSetting setting = agrTimeSettingService.getBasicSet(cid,
				appContent.getApplicant(),
				GeneralDate.today(),
				WorkingSystem.REGULAR_WORK); // TODO Tài liệu mô tả thiếu tham số

		AgreementsOneMonth agrOneMonth = setting.getOneMonth();
		Pair<Boolean, AgreementOneMonthTime> result = agrOneMonth.checkErrorTimeExceeded(appContent.getErrTime());

		if (result.getKey()) {
			return new AppCreationResult(
					appContent.getApplicant(),
					ResultType.MONTHLY_LIMIT_EXCEEDED,
					Optional.empty(),
					Optional.of(result.getValue()),
					Optional.empty()
			);
		}

		AgreementOneMonthTime alarmTime = agrOneMonth.calculateAlarmTime(appContent.getErrTime());
		SpecialProvisionsOfAgreement app = createOneMonthApp(
				applicant,
				appContent,
				approverItem.get().getApproverList(),
				approverItem.get().getConfirmerList(),
				displayInfo);

		AtomTask at = AtomTask.of(() -> {
			require.addApp(app);
		});

		return new AppCreationResult(
				appContent.getApplicant(),
				ResultType.NO_ERROR,
				Optional.of(at),
				Optional.empty(),
				Optional.empty()
		);
	}

	/**
	 * 1ヶ月の申請を作成する
	 *
	 * @param applicant     申請者
	 * @param appContent    申請内容
	 * @param approverList  承認者リスト
	 * @param confirmerList 確認者リスト
	 * @param displayInfo   画面表示情報
	 * @return 36協定特別条項の適用申請
	 */
	private SpecialProvisionsOfAgreement createOneMonthApp(
			String applicant,
			MonthlyAppContent appContent,
			List<String> approverList,
			List<String> confirmerList,
			ScreenDisplayInfo displayInfo) {

		val errorAlarm = ErrorTimeInMonth.create(appContent.getErrTime(), appContent.getAlarmTime());

		// $1ヶ月時間 = 1ヶ月時間#1ヶ月時間(申請内容.年月度,$エラーアラーム)
		val oneMonthTime = new OneMonthTime(errorAlarm, appContent.getYm());

		// $申請時間 = 申請時間#申請時間(３６協定申請種類.1ヶ月,$1ヶ月時間,Optional.empty)
		// $ Application time = Application time # Application time (36 agreement application types. 1 month, $ 1 month time, Optional.empty)
		// TODO Không tương thích tham số
		//val attendanceTime = new AttendanceTime (TypeAgreementApplication.ONE_MONTH, oneMonthTime, Optional.empty() );

//		return 36協定特別条項の適用申請#新規申請作成(申請者,申請内容.対象者,$申請時間,申請内容.申請理由
//				,承認者リスト,確認者リスト,画面表示情報)
		//String cid = AppContexts.user().companyId();
//		new SpecialProvisionsOfAgreement(
//				cid,
//				applicant,
//				appContent.getApplicant(),
//				attendanceTime,
//				appContent.getReason(),
//				approverList,
//				confirmerList,
//				displayInfo
//		);

// return 36 Application for application of special provisions of agreement # New application creation (applicant, application content. Target person, $ application time, application content. Application reason
//, Approver list, Confirmer list, Screen display information)
		return null; // TODO
	}

	public static interface Require extends ApproverGetDomainService.Require, AgreementDomainService.RequireM3 {
		public void addApp(SpecialProvisionsOfAgreement app);
	}
}
