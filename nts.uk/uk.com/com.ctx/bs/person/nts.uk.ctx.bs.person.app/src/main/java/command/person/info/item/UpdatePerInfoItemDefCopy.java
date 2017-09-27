package command.person.info.item;

import java.util.List;

import lombok.Value;

@Value
public class UpdatePerInfoItemDefCopy {
	private String perInfoCtgId;
	private List<String> perInfoItemDefIds;
}
