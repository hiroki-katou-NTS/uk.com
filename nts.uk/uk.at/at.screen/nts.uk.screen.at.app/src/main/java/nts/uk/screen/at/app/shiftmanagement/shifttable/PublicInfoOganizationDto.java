package nts.uk.screen.at.app.shiftmanagement.shifttable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublicInfoOganizationDto {
	private int unit;
	private String workplaceId;
	private String workplaceGroupId;
	private String displayName;
	private String publicDate;
	private String editDate;

}
