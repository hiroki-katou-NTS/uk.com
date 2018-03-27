package nts.uk.ctx.at.shared.infra.entity.scherec.dailyattendanceitem;

import java.io.Serializable;
import java.util.ArrayList;
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
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItemAuthority;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "KSHST_DAI_ITEM_AUTH")
public class KshstDailyAttdItemAuth extends UkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KshstDailyAttdItemAuthPK kshstDailyAttdItemAuthPK;
	
	@OneToMany(mappedBy="dailyattditemauth", cascade = CascadeType.ALL)
	@JoinTable(name = "KSHST_DAI_SER_TYPE_CTR")
	public List<KshstDailyServiceTypeControl> dailyServiceTypeControls;

	@Override
	protected Object getKey() {
		return this.kshstDailyAttdItemAuthPK;
	}

	public KshstDailyAttdItemAuth(KshstDailyAttdItemAuthPK kshstDailyAttdItemAuthPK, List<KshstDailyServiceTypeControl> dailyServiceTypeControls) {
		super();
		this.kshstDailyAttdItemAuthPK = kshstDailyAttdItemAuthPK;
		this.dailyServiceTypeControls = dailyServiceTypeControls;
	}
	
	public static KshstDailyAttdItemAuth toEntity(String companyID,String authorityDailyID,DailyAttendanceItemAuthority domain) {
		return new KshstDailyAttdItemAuth(
				new KshstDailyAttdItemAuthPK(domain.getCompanyID(),domain.getAuthorityDailyId()),
				domain.getListDisplayAndInputControl().stream().map(c->KshstDailyServiceTypeControl.toEntity(companyID,authorityDailyID,c)).collect(Collectors.toList())
				);
	}

	public DailyAttendanceItemAuthority toDomain() {
		return new DailyAttendanceItemAuthority(
				this.kshstDailyAttdItemAuthPK.companyID,
				this.kshstDailyAttdItemAuthPK.authorityDailyID,
				this.dailyServiceTypeControls.stream().map(c->c.toDomain()).collect(Collectors.toList())
				);
	}
	
}
