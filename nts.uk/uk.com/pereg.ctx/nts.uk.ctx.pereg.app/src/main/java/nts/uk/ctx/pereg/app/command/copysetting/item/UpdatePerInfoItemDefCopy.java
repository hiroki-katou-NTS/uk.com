package nts.uk.ctx.pereg.app.command.copysetting.item;

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
	private List<PerInfoDefDto> perInfoItemDefLst;
}
