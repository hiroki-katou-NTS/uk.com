package nts.uk.ctx.sys.assist.app.find.autosetting.deletion;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.sys.assist.dom.deletedata.ResultDeletion;

@Data
@AllArgsConstructor
public class DeleteSetDto {
	/**
	 * 削除セットコード
	 */
	private String patternCode;
	
	/**
	 * 削除名称
	 */
	private String delName;
	
	public static DeleteSetDto fromDomain(ResultDeletion domain) {
		return new DeleteSetDto(domain.getDelCode().v(), domain.getDelName().v());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DeleteSetDto) {
			return ((DeleteSetDto) obj).getPatternCode().equals(patternCode);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return patternCode.hashCode();
	}
}
