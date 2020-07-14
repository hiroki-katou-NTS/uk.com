package nts.uk.screen.at.app.query.kmp.kmp001.c;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDateTime;

/**
 * 
 * @author chungnt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
public class CardUnregisteredDto {

	private String stampNumber = "";
	private String infoLocation = "";
	private String stampAtr = "";
	private GeneralDateTime stampDatetime;
	
}
