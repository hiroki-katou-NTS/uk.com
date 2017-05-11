package nts.uk.ctx.at.record.dom.divergencetime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DivergenceItemName {
	
	/*勤怠項目ID*/
	private int id;
	/*勤怠項目名称*/
	private String name;
	
	public DivergenceItemName(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public static DivergenceItemName createSimpleFromJavaType(
			int id,
			String name){
		return new DivergenceItemName(id, name);
	}
}
