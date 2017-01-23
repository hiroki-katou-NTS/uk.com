package dto;

import lombok.Data;

@Data

public class InsuranceOfficeDto {
	private String code;
	private String name;
	
	public InsuranceOfficeDto(String code,String name){
		this.code=code;
		this.name=name;
	}
	
}
