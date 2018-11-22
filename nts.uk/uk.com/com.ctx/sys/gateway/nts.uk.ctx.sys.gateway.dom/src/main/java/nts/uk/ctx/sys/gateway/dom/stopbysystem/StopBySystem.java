package nts.uk.ctx.sys.gateway.dom.stopbysystem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.sys.gateway.dom.stopbycompany.SystemStatusType;
import nts.uk.ctx.sys.gateway.dom.stopbycompany.UsageStopModeType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//システム全体の利用停止の設定
public class StopBySystem extends AggregateRoot {

	/** 契約コード */
	private String contractCd;

	/** 利用停止モード */
	private UsageStopModeType usageStopMode;

	/** 利用停止のメッセージ */
	private String usageStopMessage;

	/** システム利用状態 */
	private SystemStatusType systemStatus;

	/** 停止予告のメッセージ */
	private String stopMessage;

	public static StopBySystem createFromJavaType(String contractCd, Integer usageStopMode, String usageStopMessage,
			Integer systemStatus, String stopMessage) {
		return new StopBySystem(contractCd, EnumAdaptor.valueOf(usageStopMode, UsageStopModeType.class),
				usageStopMessage, EnumAdaptor.valueOf(systemStatus, SystemStatusType.class), stopMessage);
	}

}
