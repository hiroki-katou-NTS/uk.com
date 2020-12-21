package nts.uk.cnv.screen.app.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cnv001BLoadParamDto {
	String category;

	public String getBranch() {
		return "";
	}
	public GeneralDate getDate() {
		return GeneralDate.today();
	}
}
