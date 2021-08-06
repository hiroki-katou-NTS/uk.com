package nts.uk.screen.at.app.query.kdw.kdw003.g;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskItemDto {
	private Integer frameNo;
	private String taskCode;
	private String taskName;
	private String taskAbName;
	private String startDate;
	private String endDate;
}
