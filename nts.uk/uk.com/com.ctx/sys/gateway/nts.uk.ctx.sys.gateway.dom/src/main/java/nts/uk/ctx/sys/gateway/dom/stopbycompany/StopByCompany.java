package nts.uk.ctx.sys.gateway.dom.stopbycompany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//会社単位の利用停止の設定
public class StopByCompany extends AggregateRoot {

	/** 契約コード */
	private String contractCd;

	/** 会社コード */
	private String companyCd;

	/** 利用停止モード */
	private UsageStopModeType usageStopMode;

	/** 利用停止のメッセージ */
	private String usageStopMessage;

	/** システム利用状態 */
	private SystemStatusType systemStatus;

	/** 停止予告のメッセージ */
	private String stopMessage;

	public static StopByCompany createFromJavaType(String contractCd, String companyCd, Integer usageStopMode,
			String usageStopMessage, int systemStatus, String stopMessage) {
		return new StopByCompany(contractCd, companyCd, EnumAdaptor.valueOf(usageStopMode, UsageStopModeType.class),
				usageStopMessage, EnumAdaptor.valueOf(systemStatus, SystemStatusType.class), stopMessage);
	}
}
