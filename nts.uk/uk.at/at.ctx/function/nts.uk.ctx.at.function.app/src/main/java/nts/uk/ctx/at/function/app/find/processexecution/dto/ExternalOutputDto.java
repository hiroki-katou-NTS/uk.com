package nts.uk.ctx.at.function.app.find.processexecution.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.processexecution.ExternalOutput;
import nts.uk.ctx.at.function.dom.processexecution.ExternalOutputConditionCode;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The class External output dto.<br>
 * Dto
 *
 * @author nws-minhnb
 */
@Data
@AllArgsConstructor
public class ExternalOutputDto {

	/**
	 * 外部出力区分
	 **/
	private int externalOutputClassification;

	/**
	 * 条件一覧
	 **/
	private List<String> extOutputConditionCodeList;

	/**
	 * No args constructor.
	 */
	private ExternalOutputDto() {
	}

	/**
	 * Create from domain.
	 *
	 * @param domain the domain
	 * @return the External output dto
	 */
	public static ExternalOutputDto createFromDomain(ExternalOutput domain) {
		if (domain == null) {
			return null;
		}
		ExternalOutputDto dto = new ExternalOutputDto();
		dto.externalOutputClassification = domain.getExtOutputCls().value;
		dto.extOutputConditionCodeList = domain.getExtOutCondCodeList().stream().map(ExternalOutputConditionCode::v)
				.collect(Collectors.toList());
		return dto;
	}

}
