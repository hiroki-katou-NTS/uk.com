package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 要素項目（マスタ）
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper=false)
public class MasterElementItem extends DomainObject {

	/**
	 * マスタコード
	 */
	private String masterCode;

}
