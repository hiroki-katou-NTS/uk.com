package nts.uk.ctx.at.record.infra.entity.reservation.bentomenu;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.reservation.bento.WorkLocationCode;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoAmount;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoName;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoReservationUnitName;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import java.util.Optional;

@Entity
@Table(name = "KRCMT_BENTO")
@AllArgsConstructor
@NoArgsConstructor
public class KrcmtBento extends ContractUkJpaEntity {

    @EmbeddedId
    public KrcmtBentoPK pk;

    @Column(name = "BENTO_NAME")
    public String bentoName;

    @Column(name = "UNIT_NAME")
    public String unitName;

    @Column(name = "PRICE1")
    public int price1;

    @Column(name = "PRICE2")
    public int price2;

    @Column(name = "RESERVATION1_ATR")
    public boolean reservationAtr1;

    @Column(name = "RESERVATION2_ATR")
    public boolean reservationAtr2;

    @Column(name = "WORK_LOCATION_CD")
    public String workLocationCode;

    @ManyToOne
    @PrimaryKeyJoinColumns({
            @PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID"),
            @PrimaryKeyJoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID")
    })
    public KrcmtBentoMenu bentoMenu;

    @Override
    protected Object getKey() {
        return pk;
    }

    public Bento toDomain() {
        return new Bento(
                pk.frameNo,
                new BentoName(bentoName),
                new BentoAmount(price1),
                new BentoAmount(price2),
                new BentoReservationUnitName(unitName),
                reservationAtr1,
                reservationAtr2,
                Optional.of(new WorkLocationCode(workLocationCode)));
    }

    public static KrcmtBento fromDomain(Bento bento, String hisId, KrcmtBentoMenu krcmtBentoMenu) {
        return new KrcmtBento(
                new KrcmtBentoPK(
                        AppContexts.user().companyId(),
                        hisId,
                        bento.getFrameNo()
                ),
                bento.getName().v(),
                bento.getUnit().v(),
                bento.getAmount1().v(),
                bento.getAmount2().v(),
                bento.isReservationTime1Atr(),
                bento.isReservationTime2Atr(),
                bento.getWorkLocationCode().isPresent() ?
                        bento.getWorkLocationCode().get().v() : null,
                krcmtBentoMenu
        );
    }

}
