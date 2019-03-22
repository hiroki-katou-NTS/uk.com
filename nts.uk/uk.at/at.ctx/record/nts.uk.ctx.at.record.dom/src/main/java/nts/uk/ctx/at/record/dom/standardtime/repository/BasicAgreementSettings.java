package nts.uk.ctx.at.record.dom.standardtime.repository;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSetting;

/**
 * 36協定基本設定
 * @author shuichi_ishida
 */
@Getter
public class BasicAgreementSettings {

	/** 36協定基本設定 */
	private BasicAgreementSetting basicAgreementSetting;
	
	/**
	 * コンストラクタ
	 */
	public BasicAgreementSettings() {
		this.basicAgreementSetting = BasicAgreementSetting.createFromJavaType(new String(),
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}
	
	/**
	 * ファクトリー
	 * @param basicAgreementSetting 36協定基本設定
	 * @return 36協定基本設定
	 */
	public static BasicAgreementSettings of(
			BasicAgreementSetting basicAgreementSetting) {
		
		BasicAgreementSettings domain = new BasicAgreementSettings();
		domain.basicAgreementSetting = basicAgreementSetting;
		return domain;
	}
}
