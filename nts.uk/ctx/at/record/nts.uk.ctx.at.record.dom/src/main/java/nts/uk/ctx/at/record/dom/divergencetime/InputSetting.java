package nts.uk.ctx.at.record.dom.divergencetime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InputSetting {

	private UseSetting selectUseSet;
	
	private CancelCheckError cancelErrSelReason;
	
	public static InputSetting convert(
			int selectUseSet,
			int cancelErrSelReason)
	{
		return new InputSetting(
				EnumAdaptor.valueOf(selectUseSet, UseSetting.class),
				EnumAdaptor.valueOf(cancelErrSelReason,CancelCheckError.class));
	} 
}
