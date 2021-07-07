package nts.uk.ctx.exio.dom.input.revise.type.codeconvert;

import java.util.List;

import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * 受入コード変換
 */
@Getter
public class ExternalImportCodeConvert implements DomainAggregate {
	
	/** 会社ID */
	private String companyId;
	
	/** コード変換コード*/
	private CodeConvertCode convertCode;
	
	/** コード変換名称 */
	private CodeConvertName convertName;
	
	/** 変換対象外を受け入れる */
	private boolean importWithoutSetting;
	
	/** コード変換 */
	private List<CodeConvertDetail> convertDetails;
	
	public ExternalImportCodeConvert(String companyId, String convertCode, String convertName, boolean importWithoutSetting,
			List<CodeConvertDetail> listConvertDetails) {
		super();
		this.companyId = companyId;
		this.convertCode = new CodeConvertCode(convertCode);
		this.convertName = new CodeConvertName(convertName);
		this.importWithoutSetting = importWithoutSetting;
		this.convertDetails = listConvertDetails;
	}
	
	/**
	 * 変換する
	 * @param target
	 * @return
	 */
	public CodeConvertResult convert(String target) {
		for (val detail : convertDetails) {
			if (detail.getTargetCode().toString().equals(target)) {
				return CodeConvertResult.succeeded(detail.getSystemCode());
			}
			
		}
		if(importWithoutSetting) {
			// 変換対象外を受け入れる設定のためINPUTを変換値として返す
			return CodeConvertResult.succeeded(new CodeConvertValue(target));
		}
		// 変換対象がない場合はempty
		return CodeConvertResult.failed();
	}
}
