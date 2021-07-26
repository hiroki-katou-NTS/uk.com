package nts.uk.ctx.exio.dom.input.setting.assembly.revise.codeconvert;

import java.util.List;

import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.DomainObject;

/**
 * 受入コード変換
 */
@Getter
public class ExternalImportCodeConvert extends DomainObject {
	
	/** 変換対象外を受け入れる */
	private boolean importWithoutSetting;
	
	/** コード変換 */
	private List<CodeConvertDetail> convertDetails;
	
	public ExternalImportCodeConvert(boolean importWithoutSetting, List<CodeConvertDetail> listConvertDetails) {
		super();
		this.importWithoutSetting = importWithoutSetting;
		this.convertDetails = listConvertDetails;
	}
	
	/**
	 * 変換する
	 * @param target
	 * @return
	 */
	public CodeConvertValue convert(String target) {
		for (val detail : convertDetails) {
			if (detail.getBefore().v().equals(target)) {
				return detail.getAfter();
			}
		}
		
		if(importWithoutSetting) {
			// 変換対象外を受け入れる設定のためINPUTを変換値として返す
			return new CodeConvertValue(target);
		}
		
		throw new RuntimeException("TODO: エラー時の処理");
	}
}
