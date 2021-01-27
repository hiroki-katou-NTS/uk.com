package nts.uk.ctx.at.function.app.find.processexecution.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.processexecution.ExternalAcceptance;
import nts.uk.ctx.at.function.dom.processexecution.ExternalAcceptanceConditionCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

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
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExternalAcceptanceDto {

	/**
	 * The External acceptance classification.<br>
	 * 外部受入区分
	 **/
	private int extAcceptCls;

	/**
	 * The External acceptance condition code list.<br>
	 * 条件一覧
	 **/
	private List<String> extAcceptCondCodeList;

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
		dto.extAcceptCls = domain.getExtAcceptCls().value;
		dto.extAcceptCondCodeList = domain.getExtAcceptCondCodeList()
											  .stream()
											  .map(ExternalAcceptanceConditionCode::v)
											  .collect(Collectors.toList());
		return dto;
	}

	public ExternalAcceptance toDomain() {
		return ExternalAcceptance.builder()
				.extAcceptCls(EnumAdaptor.valueOf(this.extAcceptCls, NotUseAtr.class))
				.extAcceptCondCodeList(this.extAcceptCondCodeList.stream()
						.map(ExternalAcceptanceConditionCode::new)
						.collect(Collectors.toList()))
				.build();
	}
	
}
