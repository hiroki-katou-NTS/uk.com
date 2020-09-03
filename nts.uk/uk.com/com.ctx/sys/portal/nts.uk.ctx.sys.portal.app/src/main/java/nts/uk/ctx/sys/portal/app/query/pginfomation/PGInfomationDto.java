package nts.uk.ctx.sys.portal.app.query.pginfomation;

import lombok.Data;
import nts.uk.ctx.sys.portal.dom.logsettings.PGInfomation;
import nts.uk.ctx.sys.portal.dom.logsettings.TargetSetting;

@Data
public class PGInfomationDto {
	/**
	 * プログラムID
	 */
	private String programId;

	/**
	 * ログイン履歴の記録
	 */
	private TargetSetting loginHistoryRecord;

	/**
	 * 修正履歴の記録
	 */
	private TargetSetting editHistoryRecord;

	/**
	 * 機能名
	 */
	private String functionName;

	/**
	 * 画面ID
	 */
	private String screenId;

	/**
	 * 起動履歴の記録
	 */
	private TargetSetting bootHistoryRecord;

	public static PGInfomationDto fromDomain(PGInfomation pgInfomation) {
		PGInfomationDto pgInfomationDto = new PGInfomationDto();
		pgInfomationDto.programId = pgInfomation.getProgramId();
		pgInfomationDto.loginHistoryRecord = pgInfomation.getLoginHistoryRecord();
		pgInfomationDto.editHistoryRecord = pgInfomation.getEditHistoryRecord();
		pgInfomationDto.functionName = pgInfomation.getFunctionName();
		pgInfomationDto.screenId = pgInfomation.getScreenId();
		pgInfomationDto.bootHistoryRecord = pgInfomation.getBootHistoryRecord();
		return pgInfomationDto;
	}
}
