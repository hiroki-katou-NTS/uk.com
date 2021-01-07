package nts.uk.query.app.exi.condset.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSet;

/**
 * The class Standard acceptance condition setting dto.<br>
 * Dto 受入条件設定（定型）
 *
 * @author nws-minhnb
 */
@Data
@AllArgsConstructor
public class StdAcceptCondSetDto implements StdAcceptCondSet.MementoSetter {

	/**
	 * システム種類
	 */
	private int systemType;

	/**
	 * 外部受入条件コード
	 */
	private String conditionSetCode;

	/**
	 * 外部受入条件名称
	 */
	private String conditionSetName;

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

	/**
	 * No args constructor.
	 */
	private StdAcceptCondSetDto() {
	}

	/**
	 * Sets company id.
	 *
	 * @param companyId the company id
	 */
	@Override
	public void setCompanyId(String companyId) {
	}

	/**
	 * Sets check completed.
	 *
	 * @param checkCompleted the check completed
	 */
	@Override
	public void setCheckCompleted(Integer checkCompleted) {
	}

	/**
	 * Creates from domain.
	 *
	 * @param domain the domain 受入条件設定（定型）
	 * @return the dto 受入条件設定（定型）
	 */
	public static StdAcceptCondSetDto createFromDomain(StdAcceptCondSet domain) {
		if (domain == null) {
			return null;
		}
		StdAcceptCondSetDto dto = new StdAcceptCondSetDto();
		domain.setMemento(dto);
		return dto;
	}

}
