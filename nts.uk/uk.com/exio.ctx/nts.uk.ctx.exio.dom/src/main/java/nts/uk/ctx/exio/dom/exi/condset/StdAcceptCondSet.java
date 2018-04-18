package nts.uk.ctx.exio.dom.exi.condset;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.exio.dom.exi.csvimport.ExiCharset;
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
	 * 文字コード
	 */
	private Optional<ExiCharset> characterCode;

	/**
	 * 受入モード
	 */
	private Optional<AcceptMode> acceptMode;

	/**
	 * 外部受入条件名称
	 */
	private AcceptanceConditionName conditionSetName;

	/**
	 * チェック完了
	 */
	private Optional<NotUseAtr> checkCompleted;

	/**
	 * 既存データの削除方法
	 */
	private Optional<DeleteExistDataMethod> deleteExtDataMethod;

	public StdAcceptCondSet(String cid, 
			AcceptanceConditionCode conditionSetCd, 
			Optional<String> categoryId,
			Optional<AcceptanceLineNumber> csvDataLineNumber, 
			SystemType systemType, 
			NotUseAtr deleteExistData,
			Optional<AcceptanceLineNumber> csvDataStartLine, 
			Optional<ExiCharset> characterCode, 
			Optional<AcceptMode> acceptMode,
			AcceptanceConditionName conditionSetName, 
			Optional<NotUseAtr> checkCompleted,
			Optional<DeleteExistDataMethod> deleteExtDataMethod) {
		super();
		this.cid = cid;
		this.conditionSetCd = conditionSetCd;
		this.categoryId = categoryId;
		this.csvDataLineNumber = csvDataLineNumber;
		this.systemType = systemType;
		this.deleteExistData = deleteExistData;
		this.csvDataStartLine = csvDataStartLine;
		this.characterCode = characterCode;
		this.acceptMode = acceptMode;
		this.conditionSetName = conditionSetName;
		this.checkCompleted = checkCompleted;
		this.deleteExtDataMethod = deleteExtDataMethod;
	}
	
	public StdAcceptCondSet(String cid, int systemType, String conditionSetCd, String conditionSetName,
			int deleteExistData, Integer acceptMode, Integer checkCompleted, String categoryId, Integer csvDataLineNumber,
			Integer csvDataStartLine, Integer characterCode, Integer deleteExtDataMethod) {
		super();
		this.cid = cid;
		this.conditionSetCd = new AcceptanceConditionCode(conditionSetCd);
		this.systemType = EnumAdaptor.valueOf(systemType, SystemType.class);
		this.deleteExistData = EnumAdaptor.valueOf(deleteExistData, NotUseAtr.class);
		if (acceptMode == null)
			this.acceptMode = Optional.empty();
		else
			this.acceptMode = Optional.of(EnumAdaptor.valueOf(acceptMode, AcceptMode.class));
		this.conditionSetName = new AcceptanceConditionName(conditionSetName);
		if (checkCompleted == null)
			this.checkCompleted = Optional.empty();
		else 
			this.checkCompleted = Optional.of(EnumAdaptor.valueOf(checkCompleted, NotUseAtr.class));
		this.categoryId = Optional.ofNullable(categoryId);
		if (csvDataLineNumber == null)
			this.csvDataLineNumber = Optional.empty();
		else
			this.csvDataLineNumber = Optional.of(new AcceptanceLineNumber(csvDataLineNumber));
		if (csvDataStartLine == null)
			this.csvDataStartLine = Optional.empty();
		else
			this.csvDataStartLine = Optional.of(new AcceptanceLineNumber(csvDataStartLine));
		if (characterCode == null)
			this.characterCode = Optional.empty();
		else
			this.characterCode = Optional.of(ExiCharset.valueOf(characterCode));
		if (deleteExtDataMethod == null)
			this.deleteExtDataMethod = Optional.empty();
		else
			this.deleteExtDataMethod = Optional
					.of(EnumAdaptor.valueOf(deleteExtDataMethod.intValue(), DeleteExistDataMethod.class));
	}

	public void updateWhenSettingItems(String categoryId, Integer csvDataLineNumber, Integer csvDataStartLine, Integer characterCode) {
		this.categoryId = Optional.of(categoryId);
		this.csvDataLineNumber = Optional.of(new AcceptanceLineNumber(csvDataLineNumber));
		this.csvDataStartLine = Optional.of(new AcceptanceLineNumber(csvDataStartLine));
		this.characterCode = Optional.of(ExiCharset.valueOf(characterCode));
	}
	
	public void updateCheckComplete(Integer check) {
		this.checkCompleted = Optional.of(EnumAdaptor.valueOf(check, NotUseAtr.class));
	}
	
}
