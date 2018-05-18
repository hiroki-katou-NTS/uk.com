package nts.uk.ctx.sys.env.app.find.sysusagesetfinder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

@Value
@NoArgsConstructor
@Getter
@Setter
public class ParamFinder {
	private String companyId = null;
	private String companyCode = null;
}
