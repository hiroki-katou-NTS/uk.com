package nts.uk.ctx.at.function.app.find.processexecution.dto;

import java.util.Optional;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.processexecution.AuxiliaryPatternCode;
import nts.uk.ctx.at.function.dom.processexecution.DeleteData;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The class Delete data dto.<br>
 * Dto データの削除
 *
 * @author nws-minhnb
 */
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteDataDto {

	/**
	 * The Data deletion classification.<br>
	 * データの削除区分
	 **/
	private int dataDelCls;

	/**
	 * The Pattern code.<br>
	 * パターンコード
	 **/
	private String patternCode;

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
		dto.dataDelCls = domain.getDataDelCls().value;
		dto.patternCode = domain.getPatternCode().map(AuxiliaryPatternCode::v).orElse(null);
		return dto;
	}

	public DeleteData toDomain() {
		return DeleteData.builder()
				.dataDelCls(EnumAdaptor.valueOf(this.dataDelCls, NotUseAtr.class))
				.patternCode(Optional.ofNullable(this.patternCode).map(AuxiliaryPatternCode::new))
				.build();
	}
	
}
