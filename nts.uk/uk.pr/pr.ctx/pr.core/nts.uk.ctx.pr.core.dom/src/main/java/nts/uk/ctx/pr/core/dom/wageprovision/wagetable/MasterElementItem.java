package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 要素項目（マスタ）
 */
@AllArgsConstructor
@Getter
public class MasterElementItem extends DomainObject {

	/**
	 * マスタコード
	 */
	private MasterCode masterCode;

	public MasterElementItem(String masterCode) {
		this.masterCode = new MasterCode(masterCode);
	}

}
