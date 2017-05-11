package nts.uk.ctx.at.record.app.find.divergencetime;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.divergencetime.DivergenceItemName;

@Getter
public class DivergenceItemNameDto {

	/*勤怠項目ID*/
	private int id;
	/*勤怠項目名称*/
	private String name;
	
	public static DivergenceItemNameDto fromDomain(DivergenceItemName domain) {
		return new DivergenceItemNameDto(
				domain.getId(),
				domain.getName());
	}

	public DivergenceItemNameDto(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
}
