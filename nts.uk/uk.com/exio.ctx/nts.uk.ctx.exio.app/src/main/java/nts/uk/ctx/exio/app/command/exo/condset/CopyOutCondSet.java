package nts.uk.ctx.exio.app.command.exo.condset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CopyOutCondSet {

	boolean result;
	String destinationName;
	String destinationCode;
	boolean overWrite;

}
