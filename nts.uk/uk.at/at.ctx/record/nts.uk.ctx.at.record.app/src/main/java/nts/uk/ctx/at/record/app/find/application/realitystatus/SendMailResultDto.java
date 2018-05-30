package nts.uk.ctx.at.record.app.find.application.realitystatus;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * @author dat.lh
 *
 */
@Value
@AllArgsConstructor
public class SendMailResultDto {
	private boolean OK;
	private List<String> listError;
}
