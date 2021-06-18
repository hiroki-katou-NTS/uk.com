package nts.uk.ctx.at.function.dom.processexecution;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The class ExternalOutput.<br>
 * 外部出力
 */
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ExternalOutput extends DomainObject {

	/**
	 * The External output classification.<br>
	 * 外部出力区分
	 **/
	private NotUseAtr extOutputCls;

	/**
	 * The External output condition code list.<br>
	 * 条件一覧
	 **/
	private List<ExternalOutputConditionCode> extOutCondCodeList;

	/**
	 * Instantiates a new External output.
	 *
	 * @param extOutputCls       the external output classification
	 * @param extOutCondCodeList the external output condition code list
	 */
	public ExternalOutput(int extOutputCls, List<String> extOutCondCodeList) {
		this.extOutputCls = EnumAdaptor.valueOf(extOutputCls, NotUseAtr.class);
		this.extOutCondCodeList = extOutCondCodeList.stream()
													.map(ExternalOutputConditionCode::new)
													.collect(Collectors.toList());
	}

}
