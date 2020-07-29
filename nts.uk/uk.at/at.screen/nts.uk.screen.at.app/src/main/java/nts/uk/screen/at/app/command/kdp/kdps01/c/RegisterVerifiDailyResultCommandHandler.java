package nts.uk.screen.at.app.command.kdp.kdps01.c;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.IdentityProcessUseSet;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.algorithm.CheckIdentityVerification;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.enums.SelfConfirmError;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.IdentificationRepository;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.IdentityProcessUseSetRepository;
import nts.uk.screen.at.app.dailymodify.command.DailyModifyRCommandFacade;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb
 *         UKDesign.UniversalK.就業.KDP_打刻.KDPS01_打刻入力(スマホ).C:打刻結果確認実績.メニュー別OCD.打刻結果(スマホ)より日別実績の本人確認を登録する
 * 
 *         日の本人確認を登録する
 */
@Stateless
public class RegisterVerifiDailyResultCommandHandler extends CommandHandler<RegisterVerifiDailyResultCommand> {

	@Inject
	private DailyModifyRCommandFacade dailyRCommandFacade;

	@Inject
	private IdentityProcessUseSetRepository identityProcessUseSetRepository;

	@Inject
	private CheckIdentityVerification checkIdentityVerification;

	@Inject
	private IdentificationRepository identificationRepository;

	@Inject
	private EmployeeDailyPerErrorRepository employeeDailyError;

	@Override
	protected void handle(CommandHandlerContext<RegisterVerifiDailyResultCommand> context) {
		// ドメインモデル「本人確認処理の利用設定」を取得する
		// Acquire the domain model "use setting of identity confirmation processing"
		String companyId = AppContexts.user().companyId();
		Optional<IdentityProcessUseSet> identityProcessOpt = identityProcessUseSetRepository.findByKey(companyId);

		// アルゴリズム「本人確認の運用方法をチェックする」を実行する
		// Execute the algorithm "Check the operation method of identity verification"
		if (identityProcessOpt.isPresent()) {

			SelfConfirmError comfirmError = checkIdentityVerification.check(identityProcessOpt.get()).map(x -> x)
					.orElse(null);

			if (comfirmError != null) {
				// エラーがある場合登録できない(Can not register if there is an error)
				if (comfirmError.equals(SelfConfirmError.CAN_NOT_REGISTER_WHEN_ERROR)) {
					//対応するドメインモデル「社員の日別実績エラー一覧」を取得する
					// Acquire corresponding domain model "employee's daily performance error list"

					// this.employeeDailyError.getByEmpIDAndPeriod(employeeID, processingDate)
				}
				// エラーに関係なく登録できる(Can register irrespective of error)
				if (comfirmError.equals(SelfConfirmError.CAN_CONFIRM_WHEN_ERROR)) {

				}

			}
		}

	}

}
