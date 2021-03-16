package nts.uk.screen.at.app.query.knr.knr002.c;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RemoteSettingsDto {

	private List<SelectionItemsDto> listSelectionItemsDto;
	
	private List<ContentToSendDto> listContentToSendDto;
	
}
