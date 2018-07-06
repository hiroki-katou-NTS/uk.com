package nts.uk.screen.at.app.dailyperformance.correction.datadialog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeName {
  
    private String code;
    
	private String name;
	
	private String id;
	
	private int errorFind;
	
	public CodeName(String code, String name, String id) {
		super();
		this.code = code;
		this.name = name;
		this.id = id;
	}
	
	public CodeName createError(int error){
		this.errorFind = error;
		return this;
	}
}
