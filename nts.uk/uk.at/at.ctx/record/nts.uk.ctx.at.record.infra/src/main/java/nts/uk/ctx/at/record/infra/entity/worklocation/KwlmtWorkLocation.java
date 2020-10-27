package nts.uk.ctx.at.record.infra.entity.worklocation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "KWLMT_WORK_LOCATION")
@AllArgsConstructor
@NoArgsConstructor

/**
 * 
 * @author hieult
 *
 */
public class KwlmtWorkLocation extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KwlmtWorkLocationPK kwlmtWorkLocationPK;

	/** Work Location Name */
	@Column(name = "WORK_LOCATION_NAME")
	public String workLocationName;
	
	/** Horizonal Distance */
	@Column(name = "HORIZONTAL_DISTANCE")
	public String horiDistance;
	
	/** Vertical Distance */
	@Column(name = "VERTICAL_DISTANCE")
	public String vertiDistance;
	
	/** Latitude */
	@Column(name = "LATITUDE")
	public String latitude;
	
	/** Longitude */
	@Column(name = "LONGITUDE")
	public String longitude;

	@Override
	protected Object getKey() {
		return kwlmtWorkLocationPK;
	}

}
