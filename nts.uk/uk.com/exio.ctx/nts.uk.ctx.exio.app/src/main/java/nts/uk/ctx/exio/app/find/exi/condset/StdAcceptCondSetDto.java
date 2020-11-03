package nts.uk.ctx.exio.app.find.exi.condset;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSet;

/**
 * 受入条件設定（定型）
 */
@AllArgsConstructor
@Data
public class StdAcceptCondSetDto {

	/**
	 * システム種類
	 */
	private int systemType;

	/**
	 * 外部受入条件コード
	 */
	private String conditionSettingCode;

	/**
	 * 外部受入条件名称
	 */
	private String conditionSettingName;

	/**
	 * 既存データの削除
	 */
	private int deleteExistData;

	/**
	 * 受入モード
	 */
	private Integer acceptMode;

	/**
	 * 外部受入カテゴリID
	 */
	private String categoryId;

	/**
	 * CSVデータの項目名行
	 */
	private Integer csvDataItemLineNumber;

	/**
	 * CSVデータの取込開始行
	 */
	private Integer csvDataStartLine;
	
	/**
	 * 文字コード
	 */
	private Integer characterCode;

	/**
	 * 既存データの削除方法
	 */
	private Integer deleteExistDataMethod;

	public static StdAcceptCondSetDto fromDomain(StdAcceptCondSet domain) {
		return new StdAcceptCondSetDto(domain.getSystemType().value, domain.getConditionSetCode().v(),
				domain.getConditionSetName().v(), domain.getDeleteExistData().value,
				domain.getAcceptMode().isPresent() ? domain.getAcceptMode().get().value : null,
				domain.getCategoryId().isPresent() ? domain.getCategoryId().get() : null,
				domain.getCsvDataItemLineNumber().isPresent() ? domain.getCsvDataItemLineNumber().get().v() : null,
				domain.getCsvDataStartLine().isPresent() ? domain.getCsvDataStartLine().get().v() : null,
				domain.getCharacterCode().isPresent() ? domain.getCharacterCode().get().value : null,
				domain.getDeleteExistDataMethod().isPresent() ? domain.getDeleteExistDataMethod().get().value : null);
	}

}
