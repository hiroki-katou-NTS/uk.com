package nts.uk.ctx.at.aggregation.infra.entity.scheduledailytable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.at.aggregation.dom.scheduledailytable.*;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KAGMT_RPT_SCHEREC")
public class KagmtRptScherec extends ContractUkJpaEntity implements Serializable {

    @EmbeddedId
    public KagmtRptScherecPk pk;

    @Column(name = "NAME")
    public String name;

    @Basic(optional = true)
    @Column(name = "NOTE")
    public String note;

    @Column(name = "SIGN_STAMP_USE_ATR")
    public int signStampUseAtr;

    @Column(name = "WKP_MOVE_DISP_ATR")
    public int wkpMoveDispAtr;

    @Column(name = "SUPPORTER_OUTPUT_ATR_SCHE")
    public int supporterOutputAtrSche;

    @Column(name = "SUPPORTER_OUTPUT_ATR_REC")
    public int supporterOutputAtrRec;

    @OneToMany(mappedBy = "kagmtRptScherec", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @OrderBy("pk.dispOrder ASC")
    public List<KagmtRptScherecSignStamp> kagmtRptScherecSignStamps;

    @OneToMany(mappedBy = "kagmtRptScherec", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @OrderBy("pk.dispOrder ASC")
    public List<KagmtRptScherecTallyByperson> personCounters;

    @OneToMany(mappedBy = "kagmtRptScherec", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @OrderBy("pk.dispOrder ASC")
    public List<KagmtRptScherecTallyBywkp> workplaceCounters;

    @Override
    protected Object getKey() {
        return this.pk;
    }

    public KagmtRptScherec(ScheduleDailyTablePrintSetting domain) {
        this.pk = new KagmtRptScherecPk(AppContexts.user().companyId(), domain.getCode().v());
        this.name = domain.getName().v();
        this.note = domain.getItemSetting().getComment().map(PrimitiveValueBase::v).orElse(null);
        this.signStampUseAtr = domain.getItemSetting().getInkanRow().getNotUseAtr().value;
        this.kagmtRptScherecSignStamps = new ArrayList<>();
        for (int i = 0; i < domain.getItemSetting().getInkanRow().getTitleList().size(); i++) {
            KagmtRptScherecSignStamp tmp = new KagmtRptScherecSignStamp();
            tmp.pk = new KagmtRptScherecSignStampPk(this.pk.companyId, this.pk.code, i + 1);
            tmp.title = domain.getItemSetting().getInkanRow().getTitleList().get(i).v();
            this.kagmtRptScherecSignStamps.add(tmp);
        }
        this.personCounters = new ArrayList<>();
        for (int i = 0; i < domain.getItemSetting().getPersonalCounter().size(); i++) {
            KagmtRptScherecTallyByperson tmp = new KagmtRptScherecTallyByperson();
            tmp.pk = new KagmtRptScherecSignStampPk(this.pk.companyId, this.pk.code, i);
            tmp.totalTimesNo = domain.getItemSetting().getPersonalCounter().get(i);
            this.personCounters.add(tmp);
        }
        this.workplaceCounters = new ArrayList<>();
        for (int i = 0; i < domain.getItemSetting().getWorkplaceCounter().size(); i++) {
            KagmtRptScherecTallyBywkp tmp = new KagmtRptScherecTallyBywkp();
            tmp.pk = new KagmtRptScherecSignStampPk(this.pk.companyId, this.pk.code, i);
            tmp.totalTimesNo = domain.getItemSetting().getWorkplaceCounter().get(i);
            this.workplaceCounters.add(tmp);
        }
        this.wkpMoveDispAtr = domain.getItemSetting().getTransferDisplay().value;
        this.supporterOutputAtrSche = domain.getItemSetting().getSupporterSchedulePrintMethod().value;
        this.supporterOutputAtrRec = domain.getItemSetting().getSupporterDailyDataPrintMethod().value;
    }

    public ScheduleDailyTablePrintSetting toDomain() {
        return new ScheduleDailyTablePrintSetting(
                new ScheduleDailyTableCode(this.pk.code),
                new ScheduleDailyTableName(this.name),
                new ScheduleDailyTableItemSetting(
                        new ScheduleDailyTableInkanRow(
                                EnumAdaptor.valueOf(signStampUseAtr, NotUseAtr.class),
                                kagmtRptScherecSignStamps.stream().map(e -> new ScheduleDailyTableInkanTitle(e.title)).collect(Collectors.toList())
                        ),
                        this.note == null ? Optional.empty() : Optional.of(new ScheduleDailyTableComment(this.note)),
                        personCounters.stream().map(e -> e.totalTimesNo).collect(Collectors.toList()),
                        workplaceCounters.stream().map(e -> e.totalTimesNo).collect(Collectors.toList()),
                        EnumAdaptor.valueOf(wkpMoveDispAtr, NotUseAtr.class),
                        EnumAdaptor.valueOf(supporterOutputAtrSche, SupporterPrintMethod.class),
                        EnumAdaptor.valueOf(supporterOutputAtrRec, SupporterPrintMethod.class)
                )
        );
    }
}
