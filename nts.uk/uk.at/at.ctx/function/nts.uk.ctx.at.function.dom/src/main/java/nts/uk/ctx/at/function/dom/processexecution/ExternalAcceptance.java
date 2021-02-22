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
//import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;

/**
 * The class External acceptance.<br>
 * 外部受入
 */
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ExternalAcceptance extends DomainObject {

	/**
	 * The External acceptance classification.<br>
	 * 外部受入区分
	 **/
	private NotUseAtr extAcceptCls;

	/**
	 * The External acceptance condition code list.<br>
	 * 条件一覧
	 **/
	private List<ExternalAcceptanceConditionCode> extAcceptCondCodeList;

	/**
	 * Instantiates a new <code>ExternalAcceptance</code>.
	 *
	 * @param extAcceptCls          the external acceptance classification
	 * @param extAcceptCondCodeList the external acceptance condition code list
	 */
	public ExternalAcceptance(int extAcceptCls, List<String> extAcceptCondCodeList) {
		this.extAcceptCls = EnumAdaptor.valueOf(extAcceptCls, NotUseAtr.class);
		this.extAcceptCondCodeList = extAcceptCondCodeList.stream()
														  .map(ExternalAcceptanceConditionCode::new)
														  .collect(Collectors.toList());
	}

}
