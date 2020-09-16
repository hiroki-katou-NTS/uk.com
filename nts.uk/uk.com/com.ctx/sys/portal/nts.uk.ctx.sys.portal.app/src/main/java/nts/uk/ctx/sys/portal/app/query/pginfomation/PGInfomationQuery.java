package nts.uk.ctx.sys.portal.app.query.pginfomation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.enums.MenuClassification;
import nts.uk.ctx.sys.portal.dom.logsettings.LogSetting;
import nts.uk.ctx.sys.portal.dom.logsettings.LogSettingRepository;
import nts.uk.ctx.sys.portal.dom.logsettings.PGInfomation;
import nts.uk.ctx.sys.portal.dom.logsettings.TargetSetting;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenu;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenuRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class PGInfomationQuery {
	@Inject
	private StandardMenuRepository standardMenuRepository;

	@Inject
	private LogSettingRepository logSettingRepository;

	public List<PGInfomationDto> findBySystem(int systemType) {
		String companyId = AppContexts.user().companyId();
		String programId = AppContexts.programId();

		/**
		 * システムからログ設定を取得
		 */
		List<LogSetting> logSettings = this.logSettingRepository.findBySystem(companyId, systemType);
		
		List<MenuClassification> menuClassifications = logSettings.stream().map(s -> s.getMenuClassification())
				.collect(Collectors.toList());

		/**
		 * コード一覧から標準メニューを取得
		 */
		List<StandardMenu> standardMenus = this.standardMenuRepository.findByProgram(companyId, systemType,
				menuClassifications, programId);

		/**
		 * 「PG一覧」を作成
		 */
		return this.findPGList(standardMenus, logSettings).stream().map(s -> PGInfomationDto.fromDomain(s))
				.collect(Collectors.toList());
	}

	private List<PGInfomation> findPGList(List<StandardMenu> standardMenus, List<LogSetting> logSettings) {
		List<PGInfomation> pgInfomations = new ArrayList<PGInfomation>();
		boolean programIdInCharge = logSettings.stream().allMatch(l -> l.getProgramId() == null);
		for (StandardMenu s : standardMenus) {
			PGInfomation pgInfomation = new PGInfomation();

			// function name
			pgInfomation.setFunctionName(s.getDisplayName().toString());

			TargetSetting loginHistoryRecord = new TargetSetting();
			loginHistoryRecord.setActiveCategory(s.getLogLoginDisplay());
			if (s.getLogLoginDisplay() == 0 || programIdInCharge) {
				loginHistoryRecord.setUsageCategory(0);
			} else {
				loginHistoryRecord.setUsageCategory(s.getLogLoginDisplay());
			}

			// record login history
			pgInfomation.setLoginHistoryRecord(loginHistoryRecord);

			TargetSetting bootHistoryRecord = new TargetSetting();
			bootHistoryRecord.setActiveCategory(s.getLogStartDisplay());
			if (s.getLogStartDisplay() == 0 || programIdInCharge) {
				bootHistoryRecord.setUsageCategory(0);
			} else {
				bootHistoryRecord.setUsageCategory(s.getLogStartDisplay());
			}

			// record boot history
			pgInfomation.setBootHistoryRecord(bootHistoryRecord);

			TargetSetting editHistoryRecord = new TargetSetting();
			editHistoryRecord.setActiveCategory(s.getLogUpdateDisplay());
			if (s.getLogUpdateDisplay() == 0 || programIdInCharge) {
				editHistoryRecord.setUsageCategory(0);
			} else {
				editHistoryRecord.setUsageCategory(s.getLogUpdateDisplay());
			}

			// record edit history
			pgInfomation.setEditHistoryRecord(editHistoryRecord);

			// menuClassification
			pgInfomation.setMenuClassification(s.getClassification().value);

			// programId
			pgInfomation.setProgramId(s.getProgramId());

			pgInfomations.add(pgInfomation);
		}
		return pgInfomations;
	}
}
