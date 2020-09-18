package nts.uk.ctx.sys.portal.app.query.pginfomation;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.uk.ctx.sys.portal.dom.enums.MenuClassification;
import nts.uk.ctx.sys.portal.dom.logsettings.LogSetting;
import nts.uk.ctx.sys.portal.dom.logsettings.LogSettingRepository;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenu;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenuRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class PGInfomationQueryFinder {
	
	@Inject
	private StandardMenuRepository standardMenuRepository;

	@Inject
	private LogSettingRepository logSettingRepository;

	/**
	 * ログ設定画面表示
	 * @param systemType
	 * @return
	 */
	public List<PGInfomationDto> findBySystem(int systemType) {
		String companyId = AppContexts.user().companyId();

		// Step システムからログ設定を取得
		List<LogSetting> logSettings = this.logSettingRepository.findBySystem(companyId, systemType);
		if (logSettings.isEmpty()) {
			companyId = "000000000000-0000";
			logSettings = this.logSettingRepository.findBySystem(companyId, systemType);
		}

		// Step コード一覧から標準メニューを取得
		if (logSettings.isEmpty()) {
			return Collections.emptyList();
		}
		List<MenuClassification> menuClassifications = logSettings.stream()
				.map(s -> s.getMenuClassification())
				.collect(Collectors.toList());
		List<String> programIds = logSettings.stream().map(s -> s.getProgramId()).collect(Collectors.toList());
		String screenId = "A";
		List<StandardMenu> standardMenus = this.standardMenuRepository.findByProgram(
				companyId, 
				systemType,
				menuClassifications, 
				programIds, 
				screenId);
		if (standardMenus.isEmpty()) {
			return Collections.emptyList();
		}

		// Step 「PG一覧」を作成
		return this.findPGList(standardMenus, logSettings);
	}

	/**
	 * 「PG一覧」を作成
	 * @param standardMenus
	 * @param logSettings
	 * @return
	 */
	private List<PGInfomationDto> findPGList(List<StandardMenu> standardMenus, List<LogSetting> logSettings) {
		boolean programIdInCharge = logSettings.stream()
				.anyMatch(l -> StringUtils.isNotEmpty(l.getProgramId()));
		return standardMenus.stream()
				.map((item) -> {
					// Step ・機能名　＝　標準メニュー．表示名称
					String functionName = item.getDisplayName().v();
					
					// Step ・ログイン履歴の記録．活性区分　＝　標準メニュー．ログイン履歴表示区分．表示区分
					Integer logLoginDisplay = item.getLogLoginDisplay();
					// Step ・ログイン履歴の記録．使用区分
					// if (ログイン履歴表示区分．表示区分　＝　False　OR　List＜ログ設定＞の担当プログラムID　がない)　－＞　＝　False　（＃111212を対応　メニューコードじゃなくてプログラムID）
					// else ー＞　＝　ログ設定．ログイン履歴の記録．使用区分
					Integer recordLoginDisplay = (logLoginDisplay == 0 || !programIdInCharge) ? 0 : logLoginDisplay;
					TargetSettingDto loginHistoryRecord = TargetSettingDto.builder()
							.activeCategory(logLoginDisplay)
							.usageCategory(recordLoginDisplay)
							.build();

					// Step ・起動履歴の記録．活性区分　＝　標準メニュー．起動履歴表示区分．表示区分
					Integer logStartDisplay = item.getLogStartDisplay();
					// Step ・起動履歴の記録．使用区分　
					// if (起動履歴の記録．活性区分　＝　False　OR　List＜ログ設定＞の担当プログラムID　がない)　－＞　＝　False　（＃111212を対応　メニューコードじゃなくてプログラムID）
					// else ー＞　＝　ログ設定．起動履歴の記録．使用区分
					Integer recordStartDisplay = (logStartDisplay == 0 || !programIdInCharge) ? 0 : logStartDisplay;
					TargetSettingDto startHistoryRecord = TargetSettingDto.builder()
							.activeCategory(logStartDisplay)
							.usageCategory(recordStartDisplay)
							.build();

					// Step ・修正履歴の記録．活性区分　＝　標準メニュー．修正履歴表示区分．表示区分
					Integer logUpdateDisplay = item.getLogUpdateDisplay();
					// Step 修正履歴の記録．使用区分
					// if (修正履歴の記録．活性区分　＝　False　OR　List＜ログ設定＞の担当プログラムID　がない)　－＞　＝　False　（＃111212を対応　メニューコードじゃなくてプログラムID）
					// else ー＞　＝　ログ設定．修正履歴の記録．使用区分
					Integer recordUpdateDisplay = (logUpdateDisplay == 0 || !programIdInCharge) ? 0 : logUpdateDisplay;
					TargetSettingDto updateHistoryRecord = TargetSettingDto.builder()
							.activeCategory(logUpdateDisplay)
							.usageCategory(recordUpdateDisplay)
							.build();

					// Step ・メニュー分類　＝　標準メニュー．メニュー分類　（111322）
					Integer menuClassification = item.getClassification().value;
						
					return PGInfomationDto.builder()
							.functionName(functionName)
							.loginHistoryRecord(loginHistoryRecord)
							.bootHistoryRecord(startHistoryRecord)
							.editHistoryRecord(updateHistoryRecord)
							.menuClassification(menuClassification)
							.programId(item.getProgramId()) // programId
							.build();
				})
				.collect(Collectors.toList());
	}
}
