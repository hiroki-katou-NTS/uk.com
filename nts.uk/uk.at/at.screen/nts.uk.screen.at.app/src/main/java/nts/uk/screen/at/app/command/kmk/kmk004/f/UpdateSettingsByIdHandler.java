package nts.uk.screen.at.app.command.kmk.kmk004.f;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author chungnt
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UpdateSettingsByIdHandler {

	public String id;
	
	public UpdateSettingsHandler handlerCommon;
	
}
