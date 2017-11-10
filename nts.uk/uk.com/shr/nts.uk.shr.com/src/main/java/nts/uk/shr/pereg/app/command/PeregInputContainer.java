package nts.uk.shr.pereg.app.command;

import java.util.List;

import lombok.Value;

@Value
public class PeregInputContainer {

	private final List<ItemsByCategory> inputs;
}
