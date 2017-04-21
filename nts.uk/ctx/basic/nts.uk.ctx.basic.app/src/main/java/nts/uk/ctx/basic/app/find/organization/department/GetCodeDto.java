package nts.uk.ctx.basic.app.find.organization.department;

import lombok.Data;

@Data
public class GetCodeDto {

	private String code;

	public GetCodeDto(String code) {
		super();
		this.code = code;
	}
	
}
