package nts.uk.ctx.at.shared.ws.person;

import java.util.List;

import lombok.Data;

@Data
public class FindbyIdDto {
	List<String> personIds;
}
