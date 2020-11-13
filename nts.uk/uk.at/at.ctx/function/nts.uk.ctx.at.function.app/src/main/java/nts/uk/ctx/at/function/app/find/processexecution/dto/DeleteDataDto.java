package nts.uk.ctx.at.function.app.find.processexecution.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.processexecution.AuxiliaryPatternCode;
import nts.uk.ctx.at.function.dom.processexecution.DeleteData;

/**
 * The class Delete data dto.<br>
 * Dto データの削除
 *
 * @author nws-minhnb
 */
@Data
@AllArgsConstructor
public class DeleteDataDto {

	/**
	 * データの削除区分
	 **/
	private int dataDeletionClassification;

	/**
	 * パターンコード
	 * 補助パターンコード
	 **/
	private String patternCode;

	/**
	 * No args constructor.
	 */
	private DeleteDataDto() {
	}

	/**
	 * Create from domain.
	 *
	 * @param domain the domain
	 * @return the Delete data dto
	 */
	public static DeleteDataDto createFromDomain(DeleteData domain) {
		if (domain == null) {
			return null;
		}
		DeleteDataDto dto = new DeleteDataDto();
		dto.dataDeletionClassification = domain.getDataDelCls().value;
		dto.patternCode = domain.getPatternCode().map(AuxiliaryPatternCode::v).orElse(null);
		return dto;
	}

}
