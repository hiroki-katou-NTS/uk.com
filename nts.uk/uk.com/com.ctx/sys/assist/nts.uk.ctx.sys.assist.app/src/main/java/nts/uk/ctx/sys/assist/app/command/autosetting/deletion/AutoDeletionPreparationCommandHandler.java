package nts.uk.ctx.sys.assist.app.command.autosetting.deletion;

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
import nts.gul.text.StringUtil;
import nts.uk.ctx.sys.assist.dom.deletedata.CategoryDeletion;
import nts.uk.ctx.sys.assist.dom.deletedata.DataDeletionPatternSetting;
import nts.uk.ctx.sys.assist.dom.deletedata.DataDeletionPatternSettingRepository;
import nts.uk.ctx.sys.assist.dom.deletedata.ManualSetDeletion;
import nts.uk.ctx.sys.assist.dom.storage.Explanation;
import nts.uk.ctx.sys.assist.dom.storage.FileCompressionPassword;
import nts.uk.ctx.sys.assist.dom.storage.StorageClassification;
import nts.uk.ctx.sys.assist.dom.storage.TargetMonth;
import nts.uk.ctx.sys.assist.dom.storage.TargetYear;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * UKDesign.UniversalK.共通.CMF_補助機能.CMF005_データ削除.サーバ処理.アルゴリズム.自動実行.自動削除準備.自動削除準備
 */
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class AutoDeletionPreparationCommandHandler extends CommandHandlerWithResult<String, ManualSetDeletion> {

	@Inject
	private DataDeletionPatternSettingRepository dataDeletionPatternSettingRepository;

	@Override
	protected ManualSetDeletion handle(CommandHandlerContext<String> context) {
		String patternCd = context.getCommand();
		// ドメインモデル「削除パターン設定」を取得する
		Optional<DataDeletionPatternSetting> optPatternSet = this.dataDeletionPatternSettingRepository
				.findByContractCdAndPatternCd(AppContexts.user().contractCode(), patternCd);
		if (optPatternSet.isPresent()) {
			DataDeletionPatternSetting patternSetting = optPatternSet.get();
			// ドメインモデル「データ削除の手動設定」を追加する
			int delType = StorageClassification.AUTO.value;
			String delId = IdentifierUtil.randomUniqueId();
			String delName = patternSetting.getPatternName().v();
			boolean saveBeforeDelete = true;
			boolean passwordAvailability = patternSetting.getWithoutPassword().equals(NotUseAtr.USE);
			String password = patternSetting.getPatternCompressionPwd().map(FileCompressionPassword::v).orElse(null);
			GeneralDate refDate = GeneralDate.today();
			GeneralDateTime executionDateTime = GeneralDateTime.now();
			String suppleExplanation = patternSetting.getPatternSuppleExplanation().map(Explanation::v).orElse(null);
			boolean presenceOfEmployee = false;
			String practitioner = AppContexts.user().employeeId();
			List<CategoryDeletion> categories = patternSetting.getCategories().stream().map(
					item -> new CategoryDeletion(delId, item.getCategoryId().v(), null, item.getSystemType().value))
					.collect(Collectors.toList());

			GeneralDate today = GeneralDate.today();
			// 日次保存開始年月
			Integer dailyReferYear = this.getTargetYear(patternSetting.getDailyReferYear());
			Integer dailyReferMonth = this.getTargetMonth(patternSetting.getDailyReferMonth());
			boolean disableDay = dailyReferYear == null || dailyReferMonth == null;
			GeneralDate dayStartDate = disableDay ? null
					: GeneralDate.ymd(today.addYears(dailyReferYear).year(), // （システム日付（今年） ー データ削除のパターン設定.日次参照年）
							today.addMonths(dailyReferMonth).month(), // +（システム日付（今月） ー データ削除のパターン設定.日次参照月）
							today.day()).addDays(1); // + (システム日付（今日）+ 1日)
			// 日次保存終了年月
			GeneralDate dayEndDate = disableDay ? null : GeneralDate.today(); // システム日付（年月日）
			// 月次保存開始年月
			Integer monthlyReferYear = this.getTargetYear(patternSetting.getMonthlyReferYear());
			Integer monthlyReferMonth = this.getTargetMonth(patternSetting.getMonthlyReferMonth());
			boolean disableMonth = monthlyReferYear == null || monthlyReferMonth == null;
			String monthStartDate = disableMonth ? null
					: String.format("%d%s", today.addYears(monthlyReferYear).year(), // （システム日付（今年） ー
																						// データ削除のパターン設定.月次参照年）
							StringUtil.padLeft(String.valueOf(today.addMonths(monthlyReferYear).month()), 2, '0')); // +（システム日付（今月） ー データ削除のパターン設定.月次参照月）
			// 月次保存終了年月
			String monthEndDate = disableMonth ? null : String.format("%d%s", 
					today.year(), 
					StringUtil.padLeft(String.valueOf(today.month()), 2, '0')); // システム日付（年月）
			// 年次開始年
			Integer annualReferYear = this.getTargetYear(patternSetting.getAnnualReferYear());
			boolean disableYear = annualReferYear == null;
			Integer startYear = disableYear ? null : today.addYears(annualReferYear).year(); // （システム日付（今年） ー
																								// データ削除のパターン設定.年次参照年）
			Integer endYear = disableYear ? null : today.year(); // システム日付（年）
			return ManualSetDeletion.createFromJavatype(delId, AppContexts.user().companyId(), delName,
					saveBeforeDelete, passwordAvailability, password, presenceOfEmployee, practitioner,
					suppleExplanation, refDate, executionDateTime, dayStartDate, dayEndDate,
					monthStartDate != null ? Integer.parseInt(monthStartDate) : null,
					monthEndDate != null ? Integer.parseInt(monthEndDate) : null, startYear, endYear, delType,
					patternCd, categories);
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
