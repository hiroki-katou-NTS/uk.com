package nts.uk.ctx.at.record.dom.jobmanagement.tasksupplementaryinforitemsetting;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @name UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.作業管理.作業補足情報項目設定.作業補足情報の選択肢詳細
 * 
 * @author tutt
 * 
 */
@Getter
@AllArgsConstructor
public class TaskSupInfoChoicesDetail {

	/** 履歴ID */
	private final String historyId;

	/** 項目ID */
	private final int itemId;

	/** コード */
	private final ChoiceCode code;

	/** 名称 */
	private ChoiceName name;

	/** 外部コード */
	private Optional<ExternalCode> externalCode;
}
