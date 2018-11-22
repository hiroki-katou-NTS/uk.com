package nts.uk.ctx.sys.gateway.app.find.stopsetting.stopbycompany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.sys.gateway.dom.stopbycompany.StopByCompany;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StopByCompanyDto {

	/** 契約コード */
	private String contractCd;

	/** 会社コード */
	private String companyCd;

	/** 利用停止モード */
	private Integer usageStopMode;

	/** 利用停止のメッセージ */
	private String usageStopMessage;

	/** システム利用状態 */
	private Integer systemStatus;

	/** 停止予告のメッセージ */
	private String stopMessage;

	public static StopByCompanyDto fromDomain(StopByCompany domain) {
		return new StopByCompanyDto(domain.getContractCd(), domain.getCompanyCd(), domain.getUsageStopMode().value,
				domain.getUsageStopMessage().v(), domain.getSystemStatus().value, domain.getStopMessage().v());

	}
}
