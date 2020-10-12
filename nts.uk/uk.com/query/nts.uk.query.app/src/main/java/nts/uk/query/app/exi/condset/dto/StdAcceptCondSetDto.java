package nts.uk.query.app.exi.condset.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSet;

/**
 * The class Standard acceptance condition setting dto<br>
 * 受入条件設定（定型）
 * 
 * @author nws-minhnb
 */
@AllArgsConstructor
@Data
public class StdAcceptCondSetDto {

	/** システム種類 */
	private int systemType;

	/** 外部受入条件コード */
	private String conditionSettingCode;

	/** 外部受入条件名称 */
	private String conditionSettingName;

	/** 既存データの削除 */
	private int deleteExistData;

	/** 受入モード */
	private Integer acceptMode;

	/** 外部受入カテゴリID */
	private String categoryId;

	/** CSVデータの項目名行 */
	private Integer csvDataItemLineNumber;

	/** CSVデータの取込開始行 */
	private Integer csvDataStartLine;

	/** 文字コード */
	private Integer characterCode;

	/** 既存データの削除方法 */
	private Integer deleteExistDataMethod;

	/**
	 * From domain.
	 * 
	 * @param domain the Standard acceptance condition setting domain.
	 * @return the <code>StdAcceptCondSetDto</code>
	 */
	public static StdAcceptCondSetDto fromDomain(StdAcceptCondSet domain) {
		return new StdAcceptCondSetDto(
					domain.getSystemType().value,
					domain.getConditionSetCd().v(),
					domain.getConditionSetName().v(),
					domain.getDeleteExistData().value,
					domain.getAcceptMode().map(acceptMode -> acceptMode.value).orElse(null),
					domain.getCategoryId().orElse(null),
					domain.getCsvDataLineNumber().map(acceptanceLineNumber -> acceptanceLineNumber.v()).orElse(null),
					domain.getCsvDataStartLine().map(acceptanceLineNumber -> acceptanceLineNumber.v()).orElse(null),
					domain.getCharacterCode().map(exiCharset -> exiCharset.value).orElse(null),
					domain.getDeleteExtDataMethod().map(delExistDataMethod -> delExistDataMethod.value).orElse(null));
	}

}
