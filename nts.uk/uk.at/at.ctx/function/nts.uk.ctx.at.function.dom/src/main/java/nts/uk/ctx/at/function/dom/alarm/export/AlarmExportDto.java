package nts.uk.ctx.at.function.dom.alarm.export;

import java.io.InputStream;

import lombok.Value;

/**
 * 
 * @author thuongtv
 *
 */

@Value
public class AlarmExportDto {
	private InputStream inputStream;
	private String fileName;
}
