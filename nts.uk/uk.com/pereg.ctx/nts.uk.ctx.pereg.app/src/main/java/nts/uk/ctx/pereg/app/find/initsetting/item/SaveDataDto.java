package nts.uk.ctx.pereg.app.find.initsetting.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pereg.dom.person.setting.init.item.SaveDataType;

/**
 * @author sonnlb
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveDataDto {

	private SaveDataType saveDataType;

	private Object value;

}
