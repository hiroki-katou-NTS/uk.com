package nts.uk.ctx.sys.portal.app.query.pginfomation;

import lombok.Data;
import nts.uk.ctx.sys.portal.dom.logsettings.PGInfomation;
import nts.uk.ctx.sys.portal.dom.logsettings.TargetSetting;

@Data
public class PGInfomationDto {
	/**
	 * 機能名
	 */
	private String functionName;

	/**
	 * ログイン履歴の記録
	 */
	private TargetSetting loginHistoryRecord;

	/**
	 * 修正履歴の記録
	 */
	private TargetSetting editHistoryRecord;

	/**
	 * 起動履歴の記録
	 */
	private TargetSetting bootHistoryRecord;

	public static PGInfomationDto fromDomain(PGInfomation pgInfomation) {
		PGInfomationDto pgInfomationDto = new PGInfomationDto();
		pgInfomationDto.loginHistoryRecord = pgInfomation.getLoginHistoryRecord();
		pgInfomationDto.editHistoryRecord = pgInfomation.getEditHistoryRecord();
		pgInfomationDto.functionName = pgInfomation.getFunctionName();
		pgInfomationDto.bootHistoryRecord = pgInfomation.getBootHistoryRecord();
		return pgInfomationDto;
	}
}
