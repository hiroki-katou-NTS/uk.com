package nts.uk.ctx.at.function.dom.processexecution;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import java.util.Optional;
//import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;

/**
 * The class Delete data.<br>
 * Domain データの削除
 *
 * @author nws-minhnb
 */
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DeleteData extends DomainObject {

	/**
	 * The Data deletion classification.<br>
	 * データの削除区分
	 **/
	private NotUseAtr dataDelCls;

	/**
	 * The Pattern code.<br>
	 * パターンコード
	 **/
	private Optional<AuxiliaryPatternCode> patternCode;

	/**
	 * Instantiates a new <code>DeleteData</code>.
	 *
	 * @param dataDelCls  the data deletion classification
	 * @param patternCode the pattern code
	 */
	public DeleteData(int dataDelCls, String patternCode) {
		this.dataDelCls = EnumAdaptor.valueOf(dataDelCls, NotUseAtr.class);
		this.patternCode = Optional.ofNullable(patternCode).map(AuxiliaryPatternCode::new);
	}

}
