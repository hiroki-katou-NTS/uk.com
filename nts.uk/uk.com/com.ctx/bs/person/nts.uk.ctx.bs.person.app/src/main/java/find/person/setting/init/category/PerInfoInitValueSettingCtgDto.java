package find.person.setting.init.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PerInfoInitValueSettingCtgDto {
	private String perInfoCtgId;
	private String categoryName;
	private boolean isSetting; 
}
