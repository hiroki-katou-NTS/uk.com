package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ProcessInformation;

/**
 * 処理区分基本情報
 */
@AllArgsConstructor
@Value
public class ProcessInformationDto {

	/**
	 * CID
	 */
	private String cid;

	/**
	 * PROCESS_CATE_NO
	 */
	private int processCateNo;

	/**
	 * DEPRECAT_CATE
	 */
	private int deprecatCate;

	/**
	 * PROCESS_CLS
	 */
	private String processCls;

	public static ProcessInformationDto fromDomain(ProcessInformation domain) {
		return new ProcessInformationDto(domain.getCid(), domain.getProcessCateNo(), domain.getDeprecatCate().value,
				domain.getProcessCls().v());
	}

}
