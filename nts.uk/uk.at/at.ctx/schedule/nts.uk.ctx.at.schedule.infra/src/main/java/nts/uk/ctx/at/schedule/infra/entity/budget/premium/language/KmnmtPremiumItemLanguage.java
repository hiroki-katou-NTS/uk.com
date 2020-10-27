package nts.uk.ctx.at.schedule.infra.entity.budget.premium.language;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
//import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.budget.premium.PremiumName;
import nts.uk.ctx.at.schedule.dom.budget.premium.language.PremiumItemLanguage;
import nts.uk.ctx.at.schedule.infra.entity.budget.premium.KmnmtPremiumItem;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author tutk
 *
 */
@NoArgsConstructor
@Setter
@Entity
@Table(name = "KMNMT_PREMIUM_ITEM_LANG")
public class KmnmtPremiumItemLanguage extends ContractUkJpaEntity {
	@EmbeddedId
	public KmnmtPremiumItemLanguagePK kmnmtPremiumItemLanguagePK;

	@Column(name = "PREMIUM_NAME")
	public String name;

	@OneToOne
	@PrimaryKeyJoinColumns(value = { @PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID"),
			@PrimaryKeyJoinColumn(name = "PREMIUM_NO", referencedColumnName = "PREMIUM_NO") })
	public KmnmtPremiumItem kmnmtPremiumItem;

	@Override
	protected Object getKey() {
		return kmnmtPremiumItemLanguagePK;
	}

	public KmnmtPremiumItemLanguage(KmnmtPremiumItemLanguagePK kmnmtPremiumItemLanguagePK, String name) {
		super();
		this.kmnmtPremiumItemLanguagePK = kmnmtPremiumItemLanguagePK;
		this.name = name;
	}

	public PremiumItemLanguage toDomain() {
		return new PremiumItemLanguage(this.kmnmtPremiumItemLanguagePK.companyID,
				this.kmnmtPremiumItemLanguagePK.displayNumber, this.kmnmtPremiumItemLanguagePK.langID,
				this.name == null ? null : new PremiumName(this.name));
	}

	public static KmnmtPremiumItemLanguage toEntity(PremiumItemLanguage domain) {
		String name = null;
		if(domain.getName().isPresent()) {
			name = domain.getName().get().v();
		}
		return new KmnmtPremiumItemLanguage(
				new KmnmtPremiumItemLanguagePK(domain.getCompanyID(), domain.getDisplayNumber(), domain.getLangID()),
				name);
	}

}
