package nts.uk.ctx.at.record.app.command.kmp.kmp001.a;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author chungnt
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardInformationCommands {
	private String employeeId;
	private List<String> cardNumbers;
}
