package nts.uk.ctx.exio.dom.input.revise;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.revise.type.codeconvert.CodeConvertCode;
import nts.uk.ctx.exio.dom.input.revise.type.codeconvert.ExternalImportCodeConvert;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;

/**
 * 項目の編集
 */
@Getter
@AllArgsConstructor
public class ReviseItem implements DomainAggregate {

	/** 会社ID */
	private String companyId;

	/** 受入設定コード */
	private ExternalImportCode settingCode;
	
	/** 受入項目NO */
	private int itemNo;

	/** 値の編集 */
	private ReviseValue revisingValue;

	/** コード変換コード */
	private Optional<CodeConvertCode> codeConvertCode;

	/**
	 * 編集する
	 * @param require
	 * @param context
	 * @param importItemNumber
	 * @param targetValue
	 * @return
	 */
	public DataItem revise(Require require, String targetValue) {

		// 値の編集
		Object result = this.revisingValue.revise(targetValue);

		// コード変換
		Object value = codeConvertCode
				.flatMap(ccc -> require.getCodeConvert(companyId, ccc))
				.map(c -> c.convert(result.toString()).v())
				.map(v -> (Object) v)
				.orElse(result);
		
		return new DataItem(importItemNumber, value);
	}

	public interface Require{
		Optional<ExternalImportCodeConvert> getCodeConvert(String companyId, CodeConvertCode code);
	}
}
