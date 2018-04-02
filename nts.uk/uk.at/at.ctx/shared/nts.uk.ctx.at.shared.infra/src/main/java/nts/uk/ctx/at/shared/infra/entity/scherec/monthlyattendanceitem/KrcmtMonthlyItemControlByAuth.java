package nts.uk.ctx.at.shared.infra.entity.scherec.monthlyattendanceitem;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.MonthlyItemControlByAuthority;
import nts.uk.shr.infra.data.entity.UkJpaEntity;


@Getter
@Entity
@NoArgsConstructor
@Table(name = "KSHST_MON_ITEM_AUTH")
public class KrcmtMonthlyItemControlByAuth extends UkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtMonthlyItemControlByAuthPK krcntMonthlyItemControlByAuthPK;
	
	@OneToMany(mappedBy="monthlyattditemauth", cascade = CascadeType.ALL)
	@JoinTable(name = "KSHST_MON_SER_TYPE_CTR")
	public List<KrcstDisplayAndInputMonthly> monthlyServiceTypeControls;

	@Override
	protected Object getKey() {
		return krcntMonthlyItemControlByAuthPK;
	}

	public KrcmtMonthlyItemControlByAuth(KrcmtMonthlyItemControlByAuthPK krcntMonthlyItemControlByAuthPK, List<KrcstDisplayAndInputMonthly> monthlyServiceTypeControls) {
		super();
		this.krcntMonthlyItemControlByAuthPK = krcntMonthlyItemControlByAuthPK;
		this.monthlyServiceTypeControls = monthlyServiceTypeControls;
	}
	
	public static KrcmtMonthlyItemControlByAuth toEntity(String companyID,String authorityMonthlyID,MonthlyItemControlByAuthority domain) {
		return new KrcmtMonthlyItemControlByAuth(
				new KrcmtMonthlyItemControlByAuthPK(domain.getCompanyId(),domain.getAuthorityMonthlyId()),
				domain.getListDisplayAndInputMonthly().stream().map(c->KrcstDisplayAndInputMonthly.toEntity(companyID, authorityMonthlyID, c)).collect(Collectors.toList())
				);
		
		
	}
	
	public MonthlyItemControlByAuthority toDomain() {
		return new MonthlyItemControlByAuthority(
				this.krcntMonthlyItemControlByAuthPK.companyID,
				this.krcntMonthlyItemControlByAuthPK.authorityMonthlyID,
				this.monthlyServiceTypeControls.stream().map(c->c.toDomain()).collect(Collectors.toList())
				);
	}
}
