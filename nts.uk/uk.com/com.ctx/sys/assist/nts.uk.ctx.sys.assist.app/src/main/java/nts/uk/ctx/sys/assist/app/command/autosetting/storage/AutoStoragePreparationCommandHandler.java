package nts.uk.ctx.sys.assist.app.command.autosetting.storage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.sys.assist.dom.storage.DataStorageMng;
import nts.uk.ctx.sys.assist.dom.storage.DataStorageMngRepository;
import nts.uk.ctx.sys.assist.dom.storage.DataStoragePatternSetting;
import nts.uk.ctx.sys.assist.dom.storage.DataStoragePatternSettingRepository;
import nts.uk.ctx.sys.assist.dom.storage.FileCompressionPassword;
import nts.uk.ctx.sys.assist.dom.storage.ManualSetOfDataSave;
import nts.uk.ctx.sys.assist.dom.storage.OperatingCondition;
import nts.uk.ctx.sys.assist.dom.storage.StorageClassification;
import nts.uk.ctx.sys.assist.dom.storage.TargetCategory;
import nts.uk.ctx.sys.assist.dom.storage.TargetMonth;
import nts.uk.ctx.sys.assist.dom.storage.TargetYear;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * UKDesign.UniversalK.共通.CMF_補助機能.CMF003_データ保存.サーバー処理.アルゴリズム.自動保存.自動保存準備.自動保存準備
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AutoStoragePreparationCommandHandler extends CommandHandlerWithResult<String, ManualSetOfDataSave> {

	@Inject
	private DataStoragePatternSettingRepository dataStoragePatternSettingRepository;
	
	@Inject
	private DataStorageMngRepository dataStorageMngRepository;

	@Override
	protected ManualSetOfDataSave handle(CommandHandlerContext<String> context) {
		String patternCode = context.getCommand();
		String companyId = AppContexts.user().companyId();
		String dataStorageProcessingId = IdentifierUtil.randomUniqueId();
		DataStorageMng dataStorageMng = new DataStorageMng(dataStorageProcessingId, NotUseAtr.NOT_USE, 0, 0, 0,
				OperatingCondition.INPREPARATION);
		dataStorageMngRepository.add(dataStorageMng);
		// ドメインモデル「データ保存のパターン設定」を取得する
		Optional<DataStoragePatternSetting> optPatternSetting = this.dataStoragePatternSettingRepository
				.findByContractCdAndPatternCd(AppContexts.user().contractCode(), patternCode);
		if (optPatternSetting.isPresent()) {
			DataStoragePatternSetting patternSetting = optPatternSetting.get();
			// ドメインモデル「データ保存の手動設定」を追加する
			int saveType = StorageClassification.AUTO.value;
			String saveName = patternSetting.getPatternName().v();
			int passwordAvailability = patternSetting.getWithoutPassword().value;
			String password = patternSetting.getPatternCompressionPwd().map(FileCompressionPassword::v).orElse(null);
			GeneralDate refDate = GeneralDate.today();
			GeneralDateTime executionDateTime = GeneralDateTime.now();
			String suppleExplanation = patternSetting.getPatternSuppleExplanation().orElse(null);
			int presenceOfEmployee = NotUseAtr.USE.value;
			String practitioner = AppContexts.user().employeeId();
			List<TargetCategory> targetCategories = patternSetting.getCategories().stream().map(
					item -> new TargetCategory(dataStorageProcessingId, item.getCategoryId().v(), item.getSystemType()))
					.collect(Collectors.toList());

			GeneralDate today = GeneralDate.today();
			// 日次保存開始年月
			Integer dailyReferYear = this.getTargetYear(patternSetting.getDailyReferYear());
			Integer dailyReferMonth = this.getTargetMonth(patternSetting.getDailyReferMonth());
			boolean disableDay = dailyReferYear == null || dailyReferMonth == null;
			GeneralDate daySaveStartDate = disableDay ? null
					: GeneralDate.ymd(today.addYears(dailyReferYear).year(), // （システム日付（今年）ー データ保存のパターン設定.日次参照年）
							today.addMonths(dailyReferMonth).month(), // （システム日付（今月） ー データ保存のパターン設定.日次参照月）
							today.day()).addDays(1); // (システム日付（今日）+ 1日)
			// 日次保存終了年月
			GeneralDate daySaveEndDate = disableDay ? null : GeneralDate.today(); // システム日付（年月日）
			// 月次保存開始年月
			Integer monthlyReferYear = this.getTargetYear(patternSetting.getMonthlyReferYear());
			Integer monthlyReferMonth = this.getTargetMonth(patternSetting.getMonthlyReferMonth());
			boolean disableMonth = monthlyReferYear == null || monthlyReferMonth == null;
			String monthSaveStartDate = disableMonth ? null
					: String.format("%d-%d", today.addYears(monthlyReferYear).year(), // システム日付（今年） ー データ保存のパターン設定.月次参照年
							today.addMonths(monthlyReferMonth).month()); // （システム日付（今月） ー データ保存のパターン設定.月次参照月）
			// 月次保存終了年月
			String monthSaveEndDate = disableMonth ? null
					: String.format("%d-%d", today.year(), today.month()); // システム日付（年月）
			// 年次開始年
			Integer annualReferYear = this.getTargetYear(patternSetting.getAnnualReferYear());
			boolean disableYear = annualReferYear == null;
			Integer startYear = disableYear ? null : 
				today.addYears(annualReferYear).year(); // （システム日付（今年）ーデータ保存のパターン設定.年次参照年）
			Integer endYear = disableYear ? null : today.year(); // システム日付（年）

			// ドメインモデル「データ保存の対象社員」に保存する
			return new ManualSetOfDataSave(companyId, dataStorageProcessingId, passwordAvailability, saveName, refDate,
					password, executionDateTime, daySaveEndDate, daySaveStartDate, monthSaveEndDate, monthSaveStartDate,
					suppleExplanation, endYear, startYear, presenceOfEmployee, practitioner, saveType, null,
					targetCategories);
		}
		return null;
	}

	private Integer getTargetYear(Optional<TargetYear> targetYear) {
		return targetYear.map(data -> 1 - data.value).orElse(null);
	}

	private Integer getTargetMonth(Optional<TargetMonth> targetMonth) {
		return targetMonth.map(data -> 1 - data.value).orElse(null);
	}

}
