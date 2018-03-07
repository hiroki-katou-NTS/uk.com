package nts.uk.ctx.exio.dom.exi.codeconvert;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * コード変換詳細
 */
@Getter
public class CdConvertDetails extends DomainObject {

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
	private CodeConvertValue outputItem;

	/**
	 * 本システムのコード
	 */
	private CodeConvertValue systemCd;

	/**
	 * @param cid
	 * @param convertCd
	 * @param lineNumber
	 * @param outputItem
	 * @param systemCd
	 */
	public CdConvertDetails(String cid, String convertCd, int lineNumber, String outputItem, String systemCd) {
		super();
		this.cid = cid;
		this.convertCd = convertCd;
		this.lineNumber = lineNumber;
		this.outputItem = new CodeConvertValue(outputItem);
		this.systemCd = new CodeConvertValue(systemCd);
	}

}
