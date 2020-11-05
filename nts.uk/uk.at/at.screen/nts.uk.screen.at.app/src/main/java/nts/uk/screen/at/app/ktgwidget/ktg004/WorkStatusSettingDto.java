package nts.uk.screen.at.app.ktgwidget.ktg004;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WorkStatusSettingDto {

	private List<ItemsSettingDto> ItemsSetting;
	
	private String name;
}
