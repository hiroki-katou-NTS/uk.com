package nts.uk.screen.com.app.smm.smm001.screencommand;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.smile.dom.smilelinked.cooperationoutput.EmploymentAndLinkedMonthSetting;
import nts.uk.smile.dom.smilelinked.cooperationoutput.LinkedPaymentConversion;
import nts.uk.smile.dom.smilelinked.cooperationoutput.LinkedPaymentConversionRepository;
import nts.uk.smile.dom.smilelinked.cooperationoutput.PaymentCategory;
import nts.uk.smile.dom.smilelinked.cooperationoutput.SmileLinkageOutputSetting;
import nts.uk.smile.dom.smilelinked.cooperationoutput.SmileLinkageOutputSettingRepository;

/**
 * SMILE連携外部出力を登録する
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class RegisterSmileLinkageExternalIOutputScreenCommandHandler
		extends CommandHandler<RegisterSmileLinkageExternalIOutputScreenCommand> {

	@Inject
	private SmileLinkageOutputSettingRepository smileLinkageOutputSettingRepository;

	@Inject
	private LinkedPaymentConversionRepository linkedPaymentConversionRepository;

	@Override
	protected void handle(CommandHandlerContext<RegisterSmileLinkageExternalIOutputScreenCommand> context) {
		RegisterSmileLinkageExternalIOutputScreenCommand command = context.getCommand();
		/**
		 * get 契約コード、会社ID Object＜Smile連携出力設定＞
		 */
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		SmileLinkageOutputSetting smileLinkageOutputSetting = smileLinkageOutputSettingRepository.get(contractCode,
				companyId);
		SmileLinkageOutputSetting newSmileLinkageOutputSetting = command.convertScreenCommandToEntity();

		if (smileLinkageOutputSetting == null) {
			smileLinkageOutputSettingRepository.insert(newSmileLinkageOutputSetting);
		} else {
			smileLinkageOutputSettingRepository.update(newSmileLinkageOutputSetting);
		}

		/**
		 * 支払コードを指定して連動支払変換を取得する 契約コード、会社ID、支払コード
		 */
		PaymentCategory paymentCategory = EnumAdaptor.valueOf(command.getPaymentCode(), PaymentCategory.class);
		List<EmploymentAndLinkedMonthSetting> employmentAndLinkedMonthSettings = linkedPaymentConversionRepository
				.getByPaymentCode(contractCode, companyId, paymentCategory);

		LinkedPaymentConversion newLinkedPaymentConversion = command.convertScreenCommandToLinkedPaymentConversion();
		if (!employmentAndLinkedMonthSettings.isEmpty()) {
			linkedPaymentConversionRepository.update(newLinkedPaymentConversion);
		} else {
			linkedPaymentConversionRepository.insert(newLinkedPaymentConversion);
		}
	}
}
