package nts.uk.ctx.cloud.operate.app.command;

import java.util.List;
import java.util.Map;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import lombok.Value;

@Value
public class CSVImportCommand {
	Map<String, List<InputPart>> formDataMap;
}
