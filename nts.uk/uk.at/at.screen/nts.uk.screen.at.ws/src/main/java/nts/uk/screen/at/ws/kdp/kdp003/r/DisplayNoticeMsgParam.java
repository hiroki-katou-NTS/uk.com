package nts.uk.screen.at.ws.kdp.kdp003.r;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.sys.portal.dom.notice.adapter.DatePeriodDto;

/**
 * 
 * @author tutt
 *
 */
@Getter
public class DisplayNoticeMsgParam {
	private DatePeriodDto periodDto;
	private List<String> wkpIds;
}
