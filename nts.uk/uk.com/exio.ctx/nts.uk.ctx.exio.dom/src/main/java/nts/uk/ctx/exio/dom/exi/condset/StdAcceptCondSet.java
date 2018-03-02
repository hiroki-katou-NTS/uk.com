package nts.uk.ctx.exio.dom.exi.condset;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.text.StringUtil;

/**
 * 受入条件設定（定型）
 */
@Getter
public class StdAcceptCondSet extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 外部受入条件コード
	 */
	private AcceptanceConditionCode conditionSetCd;

	/**
	 * 外部受入カテゴリID
	 */
	private Optional<String> categoryId;

	/**
	 * CSVデータの項目名行
	 */
	private Optional<AcceptanceLineNumber> csvDataLineNumber;

	/**
	 * システム種類
	 */
	private int systemType;

	/**
	 * 既存データの削除
	 */
	private int deleteExistData;

	/**
	 * CSVデータの取込開始行
	 */
	private Optional<AcceptanceLineNumber> csvDataStartLine;

	/**
	 * 受入モード
	 */
	private AcceptMode acceptMode;

	/**
	 * 外部受入条件名称
	 */
	private AcceptanceConditionName conditionSetName;

	/**
	 * チェック完了
	 */
	private int checkCompleted;

	/**
	 * 既存データの削除方法
	 */
	private Optional<DeleteExistDataMethod> deleteExtDataMethod;

	public StdAcceptCondSet(String cid, int systemType, String conditionSetCd, String conditionSetName,
			int deleteExistData, int acceptMode, int checkCompleted, String categoryId, Integer csvDataLineNumber,
			Integer csvDataStartLine, Integer deleteExtDataMethod) {
		super();
		this.cid = cid;
		this.conditionSetCd = new AcceptanceConditionCode(conditionSetCd);
		this.systemType = systemType;
		this.deleteExistData = deleteExistData;
		this.acceptMode = EnumAdaptor.valueOf(acceptMode, AcceptMode.class);
		this.conditionSetName = new AcceptanceConditionName(conditionSetName);
		this.checkCompleted = checkCompleted;
		if (StringUtil.isNullOrEmpty(categoryId, true)) {
			this.categoryId = Optional.empty();
		} else {
			this.categoryId = Optional.of(categoryId);
		}
		if (csvDataLineNumber == null) {
			this.csvDataLineNumber = Optional.empty();
		} else {
			this.csvDataLineNumber = Optional.of(new AcceptanceLineNumber(csvDataLineNumber));
		}
		if (csvDataStartLine == null) {
			this.csvDataStartLine = Optional.empty();
		} else {
			this.csvDataStartLine = Optional.of(new AcceptanceLineNumber(csvDataStartLine));
		}
		if (deleteExtDataMethod == null) {
			this.deleteExtDataMethod = Optional.empty();
		} else {
			this.deleteExtDataMethod = Optional
					.of(EnumAdaptor.valueOf(deleteExtDataMethod.intValue(), DeleteExistDataMethod.class));
		}
	}

}
