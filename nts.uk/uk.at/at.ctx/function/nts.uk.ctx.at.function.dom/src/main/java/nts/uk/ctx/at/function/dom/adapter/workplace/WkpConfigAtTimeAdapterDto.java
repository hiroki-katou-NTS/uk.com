package nts.uk.ctx.at.function.dom.adapter.workplace;

//import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WkpConfigAtTimeAdapterDto {
	/** The workplace id. */
	private String workplaceId;

	/** The hierarchy cd. */
	private String hierarchyCd;

	public WkpConfigAtTimeAdapterDto(String workplaceId, String hierarchyCd) {
		super();
		this.workplaceId = workplaceId;
		this.hierarchyCd = hierarchyCd;
	}
	
}
