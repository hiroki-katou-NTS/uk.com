package nts.uk.ctx.at.function.dom.alarm.mailsettings;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * @author ThanhPV
 * アラームリスト通常用メール設定
 */
@Getter
public class MailSettingNormal extends AggregateRoot{

	/**
	 * 会社ID
	 */
	private String companyID;
	/**
	 * 本人宛メール設定
	 */
	private Optional<MailSettings> mailSettings;
	/**
	 * 管理者宛メール設定
	 */
	private Optional<MailSettings> mailSettingAdmins;
	
	public MailSettingNormal(String companyID, MailSettings mailSettings,
			MailSettings mailSettingAdmins) {
		super();
		this.companyID = companyID;
		this.mailSettings = Optional.ofNullable(mailSettings);
		this.mailSettingAdmins = Optional.ofNullable(mailSettingAdmins);
	}
	
	
}
