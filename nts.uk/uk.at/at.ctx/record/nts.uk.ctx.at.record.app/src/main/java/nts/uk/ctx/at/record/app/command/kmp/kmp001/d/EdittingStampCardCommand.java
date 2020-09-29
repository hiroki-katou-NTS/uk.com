package nts.uk.ctx.at.record.app.command.kmp.kmp001.d;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EdittingStampCardCommand {
	private String digitsNumber;
	private String stampMethod;
}
