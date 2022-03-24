package nts.uk.ctx.link.smile.app.smilelink;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author yen_nth
 *
 */
@Data
@NoArgsConstructor
public class SmileAcceptLinkDto {
	private List<SmileAcceptSettingDto> listAcceptSet;

	public SmileAcceptLinkDto(List<SmileAcceptSettingDto> listAcceptSet) {
		super();
		this.listAcceptSet = listAcceptSet;
	}
}
