package nts.uk.smile.app.smilelinked.ocd.screenquery;

import lombok.Data;
import nts.uk.smile.dom.smilelinked.cooperationoutput.LinkedPaymentConversion;
import nts.uk.smile.dom.smilelinked.cooperationoutput.SmileLinkageOutputSetting;

@Data
public class InitialStartupOutputDto {
	// Smile連携出力設定
	private SmileLinkageOutputSetting smileLinkageOutputSetting;
	
	// 出力条件設定（定型）
	private LinkedPaymentConversion listStandardOutputConditionSetting;
	
	// List＜雇用コード、雇用名称＞
}
