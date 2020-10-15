package nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.applicationsetting.apptypeset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.PrePostInitAtr;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class AppTypeSettingCommand {
	/**
	 * 申請種類
	 */
	private int appType;
	
	/**
	 * 新規登録時に自動でメールを送信する
	 */
	private Boolean sendMailWhenRegister;
	
	/**
	 * 承認処理時に自動でメールを送信する
	 */
	private Boolean sendMailWhenApproval;
	
	/**
	 * 事前事後区分の初期表示
	 */
	private Integer displayInitialSegment;
	
	/**
	 * 事前事後区分を変更できる
	 */
	private Boolean canClassificationChange;

	public AppTypeSetting toDomain() {
		return new AppTypeSetting(
				EnumAdaptor.valueOf(appType, ApplicationType.class),
				sendMailWhenRegister == null ? false : sendMailWhenRegister,
				sendMailWhenApproval == null ? false : sendMailWhenApproval,
				displayInitialSegment == null ? null : EnumAdaptor.valueOf(displayInitialSegment, PrePostInitAtr.class),
				canClassificationChange
		);
	}
}
