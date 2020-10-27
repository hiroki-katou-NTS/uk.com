package nts.uk.ctx.at.request.infra.entity.application.appabsence.apptimedigest;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "KRQDT_APP_TIME_DIGEST")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KrqdtAppTimeDigest extends ContractUkJpaEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected KrqdtAppTimeDigestPK krqdtAppTimeDigestPK;
	 /**
     * 排他バージョン
     */
    @Version
	@Column(name="EXCLUS_VER")
	public Long version;
	/**
	 * 60H超過時間
	 */
	@Column(name = "SIXTY_HOUR_OVERTIME")
	private Integer sixtyHOvertime;
	
	/**
	 * 時間代休時間
	 */
	@Column(name ="HOUR_OF_SUB_HOLIDAY")
	private Integer hoursOfSubHoliday;
	/**
	 * 時間年休時間
	 */
	@Column(name ="HOUR_OF_HOLIDAY")
	private Integer hoursOfHoliday;
	

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return null;
	}

}
