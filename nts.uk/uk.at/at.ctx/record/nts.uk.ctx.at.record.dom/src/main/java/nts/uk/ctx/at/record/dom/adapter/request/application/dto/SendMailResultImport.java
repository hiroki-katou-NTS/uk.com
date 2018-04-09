package nts.uk.ctx.at.record.dom.adapter.request.application.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * @author dat.lh
 *
 */
@Value
@AllArgsConstructor
public class SendMailResultImport {
	private boolean OK;
	private List<String> listError;
}
