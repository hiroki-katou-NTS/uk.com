package nts.uk.ctx.exio.dom.exi.condset;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.enumcommon.NotUseAtr;

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
	private SystemType systemType;

	/**
	 * 既存データの削除
	 */
	private NotUseAtr deleteExistData;

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
	private NotUseAtr checkCompleted;

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
		this.systemType = EnumAdaptor.valueOf(systemType, SystemType.class);
		this.deleteExistData = EnumAdaptor.valueOf(deleteExistData, NotUseAtr.class);
		this.acceptMode = EnumAdaptor.valueOf(acceptMode, AcceptMode.class);
		this.conditionSetName = new AcceptanceConditionName(conditionSetName);
		this.checkCompleted = EnumAdaptor.valueOf(checkCompleted, NotUseAtr.class);
		this.categoryId = Optional.ofNullable(categoryId);
		this.csvDataLineNumber = Optional.ofNullable(new AcceptanceLineNumber(csvDataLineNumber));
		this.csvDataStartLine = Optional.ofNullable(new AcceptanceLineNumber(csvDataStartLine));
		this.deleteExtDataMethod = Optional
				.ofNullable(EnumAdaptor.valueOf(deleteExtDataMethod.intValue(), DeleteExistDataMethod.class));
	}

}
