package nts.uk.ctx.at.record.app.command.kmp.kmp001.g;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author chungnt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterStampedCardNumberInput {

	public List<String> sid;
	
	public List<CardGeneration> cardGeneration;
	
}
