package nts.uk.ctx.sys.gateway.dom.stopbysystem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.sys.gateway.dom.stopbycompany.StopMessage;
import nts.uk.ctx.sys.gateway.dom.stopbycompany.SystemStatusType;
import nts.uk.ctx.sys.gateway.dom.stopbycompany.UsageStopModeType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// システム全体の利用停止の設定
public class StopBySystem extends AggregateRoot {

	/** 契約コード */
	private String contractCd;
	/** システム利用状態 */
	private SystemStatusType systemStatus;

	/** 利用停止のメッセージ */
	private StopMessage stopMessage;

	/** 利用停止モード */
	private UsageStopModeType usageStopMode;

	/** 停止予告のメッセージ */
	private StopMessage usageStopMessage;

	public static StopBySystem createFromJavaType(String contractCd, Integer systemStatus, String stopMessage,
			Integer usageStopMode, String usageStopMessage) {
		return new StopBySystem(contractCd, EnumAdaptor.valueOf(systemStatus, SystemStatusType.class),
				new StopMessage(stopMessage), EnumAdaptor.valueOf(usageStopMode, UsageStopModeType.class),
				new StopMessage(usageStopMessage));
	}

}
