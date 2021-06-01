package nts.uk.ctx.exio.app.find.exi.codeconvert;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.exio.dom.input.revise.type.codeconvert.CodeConvertDetails;

/**
 * コード変換詳細
 */
@AllArgsConstructor
@Value
public class CodeConvertDetailsDto {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * コード変換コード
	 */
	private String convertCd;

	/**
	 * 行番号
	 */
	private int lineNumber;

	/**
	 * 出力項目
	 */
	private String outputItem;

	/**
	 * 本システムのコード
	 */
	private String systemCd;

	public static CodeConvertDetailsDto fromDomain(CodeConvertDetails domain) {
		return new CodeConvertDetailsDto(domain.getCid(), domain.getConvertCd(), domain.getLineNumber(),
				domain.getOutputItem().v(), domain.getSystemCd().v());
	}

}
