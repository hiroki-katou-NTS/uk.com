package nts.uk.ctx.at.record.infra.entity.worklocation;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.com.net.Ipv4Address;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author tutk
 *
 */
@Entity
@Table(name = "KRCMT_IP4ADDRESS")
@NoArgsConstructor
public class KrcmtIP4Address extends ContractUkJpaEntity implements Serializable {


	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtIP4AddressPK krcmtIP4AddressPK;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "CONTRACT_CD", referencedColumnName = "CONTRACT_CD"),
			@PrimaryKeyJoinColumn(name = "WK_LOCATION_CD", referencedColumnName = "WK_LOCATION_CD") })
	public KrcmtWorkLocation krcmtWorkLocation;

	@Override
	protected Object getKey() {
		return krcmtIP4AddressPK;
	}

	public KrcmtIP4Address(KrcmtIP4AddressPK krcmtIP4AddressPK) {
		super();
		this.krcmtIP4AddressPK = krcmtIP4AddressPK;
	}
	
	
	public static KrcmtIP4Address toEntity(String contractCode, String workLocationCD, Ipv4Address domain) {
		return new KrcmtIP4Address(new KrcmtIP4AddressPK(contractCode, workLocationCD, domain.getNet1(), domain.getNet2(),
				domain.getHost1(), domain.getHost2()));
	}
	
	public Ipv4Address toDomain() {
		return Ipv4Address.parse(this.krcmtIP4AddressPK.net1 + "." + this.krcmtIP4AddressPK.net2 + "."
				+ this.krcmtIP4AddressPK.host1 + "." + this.krcmtIP4AddressPK.host2);
	}
	

}
