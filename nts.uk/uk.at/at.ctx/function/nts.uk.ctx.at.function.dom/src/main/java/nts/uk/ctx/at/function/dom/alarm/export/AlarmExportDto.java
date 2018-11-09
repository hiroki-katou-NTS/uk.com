package nts.uk.ctx.at.function.dom.alarm.export;

import java.nio.file.Path;

import lombok.Value;

/**
 * 
 * @author thuongtv
 *
 */

@Value
public class AlarmExportDto {
	private Path path;
	private String fileName;
}
