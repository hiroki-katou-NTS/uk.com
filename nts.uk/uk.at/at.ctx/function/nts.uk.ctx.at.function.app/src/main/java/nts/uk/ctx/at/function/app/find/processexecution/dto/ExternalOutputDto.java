package nts.uk.ctx.at.function.app.find.processexecution.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.processexecution.ExternalOutput;
import nts.uk.ctx.at.function.dom.processexecution.ExternalOutputConditionCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The class External output dto.<br>
 * Dto
 *
 * @author nws-minhnb
 */
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExternalOutputDto {

	/**
	 * The External output classification.<br>
	 * 外部出力区分
	 **/
	private int extOutputCls;

	/**
	 * The External output condition code list.<br>
	 * 条件一覧
	 **/
	private List<String> extOutCondCodeList;

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
		dto.extOutputCls = domain.getExtOutputCls().value;
		dto.extOutCondCodeList = domain.getExtOutCondCodeList()
											   .stream()
											   .map(ExternalOutputConditionCode::v)
											   .collect(Collectors.toList());
		return dto;
	}

	public ExternalOutput toDomain() {
		return ExternalOutput.builder()
				.extOutCondCodeList(this.extOutCondCodeList.stream()
						.map(ExternalOutputConditionCode::new)
						.collect(Collectors.toList()))
				.extOutputCls(EnumAdaptor.valueOf(this.extOutputCls, NotUseAtr.class))
				.build();
	}
	
}
