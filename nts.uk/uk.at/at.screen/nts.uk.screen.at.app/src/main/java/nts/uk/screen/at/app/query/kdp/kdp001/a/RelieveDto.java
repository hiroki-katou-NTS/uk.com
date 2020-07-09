package nts.uk.screen.at.app.query.kdp.kdp001.a;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Relieve;
@AllArgsConstructor
public class RelieveDto {

	/**
	 * 打刻手段
	 */
	@Getter
	private final String stampMeans;

	public static RelieveDto fromDomain(Relieve relieve) {
		return new RelieveDto(relieve.getStampMeans() != null ? relieve.getStampMeans().name : null);
	}

}
