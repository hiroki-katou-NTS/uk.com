package nts.uk.ctx.at.function.app.find.processexecution.dto;

import java.util.Optional;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.processexecution.AuxiliaryPatternCode;
import nts.uk.ctx.at.function.dom.processexecution.SaveData;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The class Save data dto.<br>
 * Dto データの保存
 *
 * @author nws-minhnb
 */
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SaveDataDto {

	/**
	 * The Save data classification.<br>
	 * データの保存区分
	 **/
	private int saveDataCls;

	/**
	 * パターンコード
	 **/
	private String patternCode;

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
		dto.saveDataCls = domain.getSaveDataCls().value;
		dto.patternCode = domain.getPatternCode().map(AuxiliaryPatternCode::v).orElse(null);
		return dto;
	}

	public SaveData toDomain() {
		return SaveData.builder()
				.patternCode(Optional.ofNullable(this.patternCode).map(AuxiliaryPatternCode::new))
				.saveDataCls(EnumAdaptor.valueOf(this.saveDataCls, NotUseAtr.class))
				.build();
	}
	
}
