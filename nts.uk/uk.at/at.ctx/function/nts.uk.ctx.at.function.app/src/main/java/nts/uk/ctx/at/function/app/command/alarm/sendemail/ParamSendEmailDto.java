package nts.uk.ctx.at.function.app.command.alarm.sendemail;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.at.function.dom.alarm.sendemail.ValueExtractAlarmDto;
/**
 * 
 * @author thuongtv
 *
 */
@Value
@AllArgsConstructor
public class ParamSendEmailDto {
	
	String companyID;
	String employeeId;
	Integer functionID;
	List<ValueExtractAlarmDto> listDataAlarmExport;
	FileGeneratorContext generatorContext;
	String subjectEmail;
	String bodyEmail;

}
