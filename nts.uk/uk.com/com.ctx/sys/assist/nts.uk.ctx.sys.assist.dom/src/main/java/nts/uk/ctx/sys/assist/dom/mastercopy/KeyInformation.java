package nts.uk.ctx.sys.assist.dom.mastercopy;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KeyInformation {
	
	/** The key1. */
	private KEY KEY1;
	
	/** The key2. */
	private Optional<KEY> KEY2;
	
	/** The key3. */
	private Optional<KEY> KEY3;
	
	/** The key4. */
	private Optional<KEY> KEY4;
	
	/** The key5. */
	private Optional<KEY> KEY5;
}
