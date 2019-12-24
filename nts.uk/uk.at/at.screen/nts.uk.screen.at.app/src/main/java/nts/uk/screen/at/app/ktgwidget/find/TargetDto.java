package nts.uk.screen.at.app.ktgwidget.find;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TargetDto {
	private int closureId;
	
	private String yearMonth;

	public TargetDto(int closureId, String yearMonth) {
		super();
		this.closureId = closureId;
		this.yearMonth = yearMonth;
	}
	
}
