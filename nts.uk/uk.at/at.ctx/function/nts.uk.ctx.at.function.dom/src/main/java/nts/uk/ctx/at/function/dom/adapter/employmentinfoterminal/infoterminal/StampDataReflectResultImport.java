package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal;

import java.util.Optional;

import lombok.Value;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;

/**
 * @author ThanhNX
 *
 *         打刻データ反映処理結果Import
 */
@Value
public class StampDataReflectResultImport {

	// 反映対象日
	private final Optional<GeneralDate> reflectDate;

	// 永続化処理
	private final AtomTask atomTask;

	// [C-0] 追加する(反映対象日, 永続化処理)
	public StampDataReflectResultImport(Optional<GeneralDate> reflectDate, AtomTask atomTask) {
		this.reflectDate = reflectDate;
		this.atomTask = atomTask;
	}
}
