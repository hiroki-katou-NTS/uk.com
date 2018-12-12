/**
 * 
 */
package nts.uk.ctx.at.function.app.find.dailyworkschedule;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nws-ducnt
 *
 */
@Data
@NoArgsConstructor
public class DataReturnDto {
	/**
	 * dataInforReturnDtos
	 */
	private List<DataInforReturnDto> dataInforReturnDtos;

	/**
	 * msgErr
	 */
	private List<String> msgErr;

	/**
	 * @param dataInforReturnDtos
	 * @param msgErr
	 */
	public DataReturnDto(List<DataInforReturnDto> dataInforReturnDtos, List<String> msgErr) {
		super();
		this.dataInforReturnDtos = dataInforReturnDtos;
		this.msgErr = msgErr;
	}
	
	
}
