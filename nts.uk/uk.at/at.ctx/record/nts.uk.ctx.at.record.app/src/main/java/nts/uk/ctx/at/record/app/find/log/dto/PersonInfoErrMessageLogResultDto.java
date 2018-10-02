package nts.uk.ctx.at.record.app.find.log.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonInfoErrMessageLogResultDto {
	
	List<PersonInfoErrMessageLogDto> listResult;
	
	List<String> listEmployee;	

}
