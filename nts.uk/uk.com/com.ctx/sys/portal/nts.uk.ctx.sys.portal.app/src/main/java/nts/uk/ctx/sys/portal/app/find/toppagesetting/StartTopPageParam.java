package nts.uk.ctx.sys.portal.app.find.toppagesetting;

import java.util.Optional;

import lombok.Value;

@Value
public class StartTopPageParam {

	// topPageSetting
	private Optional<TopPageSettingNewDto> topPageSetting;
	
	// fromScreen
	private String fromScreen;
	
	// topPageCode
	private String topPageCode;
}
