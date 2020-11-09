package nts.uk.ctx.sys.gateway.infra.entity.accessrestrictions;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.gateway.dom.accessrestrictions.AllowedIPAddress;
import nts.uk.ctx.sys.gateway.dom.accessrestrictions.IPAddressRegistrationFormat;
import nts.uk.shr.com.net.Ipv4Address;
import nts.uk.shr.com.net.Ipv4Part;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * @author thanhpv
 */
@Entity
@Table(name="SGWMT_ACCESS_IP")
@NoArgsConstructor
public class SgwmtAccessIp extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public SgwmtAccessIpPK pk;

	@Column(name = "IP_INPUT_TYPE")
	public Integer ipInputType;
	
	@Column(name = "END_IP1")
	public Integer endIP1;
	
	@Column(name = "END_IP2")
	public Integer endIP2;
	
	@Column(name = "END_IP3")
	public Integer endIP3;
	
	@Column(name = "END_IP4")
	public Integer endIP4;
	
	@Column(name = "IP_CMT")
	public String ipCmt;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
	
	@ManyToOne
	@JoinColumn(name = "CONTRACT_CD", insertable = false,  updatable = false)
	public SgwmtAccess sgwmtAccess;
	
	public SgwmtAccessIp(AllowedIPAddress domain, String contractCd) {
		this.pk = new SgwmtAccessIpPK(contractCd, 
				domain.getStartAddress().getNet1().v(),
				domain.getStartAddress().getNet2().v(), 
				domain.getStartAddress().getHost1().v(),
				domain.getStartAddress().getHost2().v());
		this.ipInputType = domain.getIpInputType().value;
		if (domain.getEndAddress().isPresent()) {
			this.endIP1 = domain.getEndAddress().get().getNet1().v();
			this.endIP2 = domain.getEndAddress().get().getNet2().v();
			this.endIP3 = domain.getEndAddress().get().getHost1().v();
			this.endIP4 = domain.getEndAddress().get().getHost2().v();
		}
		if (domain.getComment().isPresent()) {
			this.ipCmt = domain.getComment().get().v();
		}
	}

	public AllowedIPAddress toDomain() {
		return new AllowedIPAddress(
				IPAddressRegistrationFormat.valueOf(this.ipInputType),
				new Ipv4Address(
						new Ipv4Part(this.pk.startIP1), 
						new Ipv4Part(this.pk.startIP2), 
						new Ipv4Part(this.pk.startIP3), 
						new Ipv4Part(this.pk.startIP4)),
				this.ipInputType == 0 ? Optional.empty()
						: Optional.of(new Ipv4Address(
								new Ipv4Part(this.endIP1), 
								new Ipv4Part(this.endIP2), 
								new Ipv4Part(this.endIP3), 
								new Ipv4Part(this.endIP4))),
				this.ipCmt);
	}

}
