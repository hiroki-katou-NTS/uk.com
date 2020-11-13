package nts.uk.ctx.at.function.app.find.processexecution.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.processexecution.ExternalAcceptance;
import nts.uk.ctx.at.function.dom.processexecution.ExternalAcceptanceConditionCode;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The class External acceptance dto.<br>
 * Dto 外部受入
 *
 * @author nws-minhnb
 */
@Data
@AllArgsConstructor
public class ExternalAcceptanceDto {

	/**
	 * 外部受入区分
	 **/
	private int externalAcceptanceClassification;

	/**
	 * 条件一覧
	 **/
	private List<String> extAccepConditionCodeList;


	/**
	 * No args constructor.
	 */
	private ExternalAcceptanceDto() {
	}

	/**
	 * Create from domain.
	 *
	 * @param domain the domain
	 * @return the External acceptance dto
	 */
	public static ExternalAcceptanceDto createFromDomain(ExternalAcceptance domain) {
		if (domain == null) {
			return null;
		}
		ExternalAcceptanceDto dto = new ExternalAcceptanceDto();
		dto.externalAcceptanceClassification = domain.getExtAcceptCls().value;
		dto.extAccepConditionCodeList = domain.getExtAcceptCondCodeList()
											  .map(list -> list.stream()
															   .map(ExternalAcceptanceConditionCode::v)
															   .collect(Collectors.toList()))
											  .orElse(null);
		return dto;
	}

}
