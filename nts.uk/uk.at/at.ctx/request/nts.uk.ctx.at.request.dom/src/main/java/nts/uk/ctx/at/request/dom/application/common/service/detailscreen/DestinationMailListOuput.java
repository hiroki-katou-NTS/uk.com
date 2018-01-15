package nts.uk.ctx.at.request.dom.application.common.service.detailscreen;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DestinationMailListOuput {
	List<String> destinationMail;
	List<Integer> phaseFrameNumber;
}
