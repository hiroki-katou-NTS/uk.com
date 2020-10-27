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
import nts.uk.ctx.at.schedule.infra.entity.budget.premium.KscmtPremiumItem;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author tutk
 *
 */
@NoArgsConstructor
@Setter
@Entity
@Table(name = "KSCMT_PREMIUM_ITEM_LANG")
public class KscmtPremiumItemLanguage extends ContractUkJpaEntity {
	@EmbeddedId
	public KscmtPremiumItemLanguagePK kscmtPremiumItemLanguagePK;

	@Column(name = "PREMIUM_NAME")
	public String name;

	@OneToOne
	@PrimaryKeyJoinColumns(value = { @PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID"),
			@PrimaryKeyJoinColumn(name = "PREMIUM_NO", referencedColumnName = "PREMIUM_NO") })
	public KscmtPremiumItem kscmtPremiumItem;

	@Override
	protected Object getKey() {
		return kscmtPremiumItemLanguagePK;
	}

	public KscmtPremiumItemLanguage(KscmtPremiumItemLanguagePK kscmtPremiumItemLanguagePK, String name) {
		super();
		this.kscmtPremiumItemLanguagePK = kscmtPremiumItemLanguagePK;
		this.name = name;
	}

	public PremiumItemLanguage toDomain() {
		return new PremiumItemLanguage(this.kscmtPremiumItemLanguagePK.companyID,
				this.kscmtPremiumItemLanguagePK.displayNumber, this.kscmtPremiumItemLanguagePK.langID,
				this.name == null ? null : new PremiumName(this.name));
	}

	public static KscmtPremiumItemLanguage toEntity(PremiumItemLanguage domain) {
		String name = null;
		if(domain.getName().isPresent()) {
			name = domain.getName().get().v();
		}
		return new KscmtPremiumItemLanguage(
				new KscmtPremiumItemLanguagePK(domain.getCompanyID(), domain.getDisplayNumber(), domain.getLangID()),
				name);
	}

}
