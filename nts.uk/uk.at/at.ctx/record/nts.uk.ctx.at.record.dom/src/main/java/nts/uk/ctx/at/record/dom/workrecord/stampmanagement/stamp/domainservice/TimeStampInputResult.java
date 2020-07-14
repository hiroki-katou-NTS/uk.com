package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.task.tran.AtomTask;

@Data
@AllArgsConstructor

/**
 * 
 * @author chungnt
 * 
 *         打刻入力結果
 */

public class TimeStampInputResult {

	// 打刻データ反映処理結果
	public final StampDataReflectResult stampDataReflectResult;

	// 永続化処理
	public final Optional<AtomTask> at;

}
