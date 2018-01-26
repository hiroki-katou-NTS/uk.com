package nts.uk.ctx.at.function.dom.processexecution;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDateTime;

/**
 * 更新処理前回実行日時
 */
@Getter
@AllArgsConstructor
public class LastExecDateTime extends AggregateRoot {
	/* 会社ID */
	private String companyId;
	
	/* コード */
	private ExecutionCode execItemCd;
	
	/* 前回実行日時 */
	private GeneralDateTime lastExecDateTime;
}
