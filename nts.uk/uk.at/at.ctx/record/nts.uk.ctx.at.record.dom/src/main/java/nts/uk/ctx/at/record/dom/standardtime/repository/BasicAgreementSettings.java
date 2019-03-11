package nts.uk.ctx.at.record.dom.standardtime.repository;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSetting;
import nts.uk.ctx.at.record.dom.standardtime.UpperAgreementSetting;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AgreementOneMonthTime;

/**
 * 36協定基本設定
 * @author shuichi_ishida
 */
@Getter
public class BasicAgreementSettings {

	/** 36協定基本設定 */
	private BasicAgreementSetting basicAgreementSetting;
	/** 36協定上限規制 */
	private UpperAgreementSetting upperAgreementSetting;
	
	/**
	 * コンストラクタ
	 */
	public BasicAgreementSettings() {
		this.basicAgreementSetting = BasicAgreementSetting.createFromJavaType(new String(),
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		this.upperAgreementSetting = new UpperAgreementSetting(
				new AgreementOneMonthTime(0),
				new AgreementOneMonthTime(0));
	}
	
	/**
	 * ファクトリー
	 * @param basicAgreementSetting 36協定基本設定
	 * @return 36協定基本設定
	 */
	public static BasicAgreementSettings of(
			BasicAgreementSetting basicAgreementSetting,
			UpperAgreementSetting upperAgreementSetting) {
		
		BasicAgreementSettings domain = new BasicAgreementSettings();
		if (basicAgreementSetting != null) domain.basicAgreementSetting = basicAgreementSetting;
		if (upperAgreementSetting != null) domain.upperAgreementSetting = upperAgreementSetting;
		return domain;
	}
}
