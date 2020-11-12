package nts.uk.ctx.at.function.app.find.processexecution.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.processexecution.AuxiliaryPatternCode;
import nts.uk.ctx.at.function.dom.processexecution.SaveData;

/**
 * The class Save data dto.<br>
 * Dto データの保存
 *
 * @author nws-minhnb
 */
@Data
@AllArgsConstructor
public class SaveDataDto {

	/**
	 * データの保存区分
	 **/
	private int saveDataClassification;

	/**
	 * パターンコード
	 **/
	private String patternCode;

	/**
	 * No args constructor.
	 */
	private SaveDataDto() {
	}

	/**
	 * Create from domain.
	 *
	 * @param domain the domain
	 * @return the Save data dto
	 */
	public static SaveDataDto createFromDomain(SaveData domain) {
		if (domain == null) {
			return null;
		}
		SaveDataDto dto = new SaveDataDto();
		dto.saveDataClassification = domain.getSaveDataCls().value;
		dto.patternCode = domain.getPatternCode().map(AuxiliaryPatternCode::v).orElse(null);
		return dto;
	}

}
