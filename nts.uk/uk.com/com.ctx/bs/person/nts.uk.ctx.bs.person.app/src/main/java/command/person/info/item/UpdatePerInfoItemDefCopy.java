package command.person.info.item;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePerInfoItemDefCopy {
	private String perInfoCtgId;
	private List<String> perInfoItemDefIds;
}
