package nts.uk.ctx.at.record.infra.entity.reservation.bentomenu;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "KRCMT_BENTO_MENU")
@AllArgsConstructor
@NoArgsConstructor
public class KrcmtBentoMenu extends ContractUkJpaEntity {
	
	@EmbeddedId
	public KrcmtBentoMenuPK pk;
	
	@Column(name = "RESERVATION_FRAME1_NAME")
	public String reservationFrameName1;
	
	@Column(name = "RESERVATION_FRAME1_START_TIME")
	public Integer reservationStartTime1;
	
	@Column(name = "RESERVATION_FRAME1_END_TIME")
	public int reservationEndTime1;
	
	@Column(name = "RESERVATION_FRAME2_NAME")
	public String reservationFrameName2;
	
	@Column(name = "RESERVATION_FRAME2_START_TIME")
	public Integer reservationStartTime2;
	
	@Column(name = "RESERVATION_FRAME2_END_TIME")
	public Integer reservationEndTime2;

	@OneToMany(targetEntity = KrcmtBento.class, mappedBy = "bentoMenu", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "KRCMT_BENTO")
	public List<KrcmtBento> bentos;
	
	@Override
	protected Object getKey() {
		return pk;
	}
	
	public BentoMenu toDomain() {
		return new BentoMenu(
				pk.histID, 
				bentos.stream().map(x -> x.toDomain()).collect(Collectors.toList()));
	}

	public static KrcmtBentoMenu fromDomain(BentoMenu bentoMenu) {
		KrcmtBentoMenu krcmtBentoMenu = new KrcmtBentoMenu(
				new KrcmtBentoMenuPK(
						AppContexts.user().companyId(),
						bentoMenu.getHistoryID()
				),
				null,
				null,
				0,
				null,
                null,
				null,
                Arrays.asList());
        List<KrcmtBento> bentos = bentoMenu.getMenu().stream().map(x -> KrcmtBento.fromDomain(x,bentoMenu.getHistoryID(), krcmtBentoMenu)).collect(Collectors.toList());
        krcmtBentoMenu.bentos = bentos;
		return krcmtBentoMenu;

	}
	
}
