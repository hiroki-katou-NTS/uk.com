package nts.uk.ctx.at.function.dom.processexecution;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import java.util.Optional;

/**
 * The class Save data.<br>
 * Domain データの保存
 *
 * @author nws-minhnb
 */
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SaveData extends DomainObject {

	/**
	 * The Save data classification.<br>
	 * データの保存区分
	 **/
	private NotUseAtr saveDataCls;

	/**
	 * The Pattern code.<br>
	 * パターンコード
	 **/
	private Optional<AuxiliaryPatternCode> patternCode;

	/**
	 * Instantiates a new <code>SaveData</code>.
	 *
	 * @param saveDataCls the save data classification
	 * @param patternCode the pattern code
	 */
	public SaveData(int saveDataCls, String patternCode) {
		this.saveDataCls = EnumAdaptor.valueOf(saveDataCls, NotUseAtr.class);
		this.patternCode = Optional.ofNullable(patternCode).map(AuxiliaryPatternCode::new);
	}

}
