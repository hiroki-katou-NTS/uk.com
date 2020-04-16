package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.Optional;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;

/**
 * @author ThanhNX
 *
 *         打刻データ反映処理結果
 */
@Value
public class StampDataReflectResult implements DomainValue {

	// 反映対象日
	private final Optional<GeneralDate> reflectDate;

	//	永続化処理
	private final AtomTask atomTask;

	//	[C-0] 追加する(反映対象日, 永続化処理)
	public StampDataReflectResult(Optional<GeneralDate> reflectDate, AtomTask atomTask) {
		super();
		this.reflectDate = reflectDate;
		this.atomTask = atomTask;
	}

}
