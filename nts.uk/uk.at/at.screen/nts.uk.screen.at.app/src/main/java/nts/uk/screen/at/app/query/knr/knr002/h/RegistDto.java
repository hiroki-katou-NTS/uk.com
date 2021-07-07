package nts.uk.screen.at.app.query.knr.knr002.h;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author xuannt
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegistDto {
	
	String terminalCode;
	
	List<String> selectedEmpIds;
}
