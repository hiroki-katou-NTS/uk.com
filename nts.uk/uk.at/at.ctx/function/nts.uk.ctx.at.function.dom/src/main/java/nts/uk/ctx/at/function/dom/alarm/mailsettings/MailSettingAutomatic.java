package nts.uk.ctx.at.function.dom.alarm.mailsettings;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
/**
 * @author ThanhPV
 * アラームリスト自動実行用メール設定
 */
@Getter
public class MailSettingAutomatic extends AggregateRoot{

	/**
	 * 会社ID
	 */
	private String companyID;
	/**
	 * 本人宛メール設定
	 */
	private Optional<MailSettings> mailSettings;
	/**
	 * 本人宛メール設定
	 */
	private Optional<String> senderAddress;
	/**
	 * 管理者宛メール設定
	 */
	private Optional<MailSettings> mailSettingAdmins;
	
	public MailSettingAutomatic(String companyID, MailSettings mailSettings, String senderAddress,
			MailSettings mailSettingAdmins) {
		super();
		this.companyID = companyID;
		this.mailSettings = Optional.ofNullable(mailSettings);
		this.senderAddress = Optional.ofNullable(senderAddress);
		this.mailSettingAdmins = Optional.ofNullable(mailSettingAdmins);
	}
}
