package nts.uk.ctx.at.schedule.ws.shift.shiftcondition.shiftcondition.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShiftConditionNodeDto {
	private int code;
	private String name;
	private String nodeText;
	private String custom;
	private List<ShiftConditionNodeDto> childs;
	
	public ShiftConditionNodeDto(int code, String name, List<ShiftConditionNodeDto> childs) {
		super();
		this.code = code;
		this.name = name;
		this.nodeText = code + " " + name;
		this.custom = "Random";
		this.childs = childs;
	}
}
