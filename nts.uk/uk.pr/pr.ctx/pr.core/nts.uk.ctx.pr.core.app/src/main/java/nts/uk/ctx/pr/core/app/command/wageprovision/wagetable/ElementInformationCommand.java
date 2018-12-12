package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementInformation;

/**
 * 要素情報
 */

@Data
@NoArgsConstructor
public class ElementInformationCommand {

	/**
	 * 一次元要素
	 */
	private ElementAttributeCommand oneDimensionElement;

	/**
	 * 二次元要素
	 */
	private ElementAttributeCommand twoDimensionElement;

	/**
	 * 三次元要素
	 */
	private ElementAttributeCommand threeDimensionElement;

	public ElementInformation fromCommandToDomain() {
		return new ElementInformation(oneDimensionElement.fromCommandToDomain(),
				twoDimensionElement.fromCommandToDomain(), threeDimensionElement.fromCommandToDomain());
	}

}
