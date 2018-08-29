package nts.uk.ctx.at.function.app.find.statement.outputitemsetting;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.at.shared.dom.workingcondition.NotUseAtr;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;
@Getter
@Setter
public class StamOutputEnumDto {
	
	/** The setting Segment. */
	//するしない区分
	private List<EnumConstant> settingSegment;
	
	
	/**
	 * Inits the.
	 *
	 * @param i18n the i 18 n
	 * @return the user info enum dto
	 */
	public static StamOutputEnumDto init(I18NResourcesForUK i18n) {
		StamOutputEnumDto dto = new StamOutputEnumDto();
		dto.setSettingSegment(EnumAdaptor.convertToValueNameList(NotUseAtr.class, i18n));
		return dto;
	}

}
