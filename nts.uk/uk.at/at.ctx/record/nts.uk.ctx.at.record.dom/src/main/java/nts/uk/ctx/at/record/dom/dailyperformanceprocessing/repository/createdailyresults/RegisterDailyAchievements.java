package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.daily.DailyRecordAdUpService;
import nts.uk.ctx.at.record.dom.functionalgorithm.errorforcreatedaily.ErrorHandlingCreateDailyResults;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampDakokuRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;
import nts.uk.shr.com.context.AppContexts;

/**
 * 日別実績を登録する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.日別実績処理.作成処理.日別作成Mgrクラス.アルゴリズム.社員の日別実績を作成する.日別実績を登録する.日別実績を登録する
 * @author tutk
 *
 */
@Stateless
public class RegisterDailyAchievements {

	@Inject
	private ErrorHandlingCreateDailyResults errorHandlingCreateDailyResults;

	@Inject
	private RegisterDailyWork registerDailyWork;

	@Inject
	private StampDakokuRepository stampDakokuRepository;

	@Inject
	private DailyRecordAdUpService dailyRecordAdUpService;

	@Inject
	private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;

	public void register(List<IntegrationOfDaily> listIntegrationOfDaily, List<ErrorMessageInfo> listError,
			List<Stamp> listStamp, Optional<String> empCalAndSumExecLogId) {
		String companyId = AppContexts.user().companyId();

		for (ErrorMessageInfo errorMessageInfo : listError) {
			String empCalAndSumExeLogId = empCalAndSumExecLogId.isPresent() ? empCalAndSumExecLogId.get() : null;
			// 日別実績の作成エラー処理
			errorHandlingCreateDailyResults.executeCreateError(companyId, errorMessageInfo.getEmployeeID(),
					errorMessageInfo.getProcessDate(), empCalAndSumExeLogId, errorMessageInfo.getExecutionContent(),
					errorMessageInfo.getResourceID(), errorMessageInfo.getMessageError());
		}

		// Do list Stamp là stamp của tất cả các ngày trong period, nên tách riêng xử lý
		// update của stamp
		// ドメインモデル「打刻」を更新する (Update 「打刻」)
		listStamp.forEach(stampItem -> {
			this.stampDakokuRepository.update(stampItem);
		});
		// エラーで本人確認と上司承認を解除する
		dailyRecordAdUpService.removeConfirmApproval(listIntegrationOfDaily);

		for (IntegrationOfDaily integrationOfDaily : listIntegrationOfDaily) {
			// 登録する (Đăng ký)
			registerDailyWork.register(integrationOfDaily, new ArrayList<>());
			// 暫定データの登録
			this.interimRemainDataMngRegisterDateChange.registerDateChange(companyId,
					integrationOfDaily.getEmployeeId(), Arrays.asList(integrationOfDaily.getYmd()));
		}

	}

}
