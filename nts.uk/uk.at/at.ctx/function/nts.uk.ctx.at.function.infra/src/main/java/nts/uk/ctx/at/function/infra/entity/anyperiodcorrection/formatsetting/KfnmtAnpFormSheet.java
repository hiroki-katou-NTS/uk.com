package nts.uk.ctx.at.function.infra.entity.anyperiodcorrection.formatsetting;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "KFNMT_ANP_FORM_SHEET")
@NoArgsConstructor
public class KfnmtAnpFormSheet extends ContractUkJpaEntity {
    @EmbeddedId
    public KfnmtAnpFormSheetPk pk;

    @Column(name = "SHEET_NAME")
    public String sheetName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
            @JoinColumn(name = "CODE", referencedColumnName = "CODE", insertable = false, updatable = false)
    })
    public KfnmtAnpForm anyPeriodForm;

    @OneToMany(mappedBy = "anyPeriodSheet", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<KfnmtAnpFormItem> items;

    public KfnmtAnpFormSheet(KfnmtAnpFormSheetPk pk, String sheetName, List<KfnmtAnpFormItem> items) {
        this.pk = pk;
        this.sheetName = sheetName;
        this.items = items;
    }

    @Override
    protected Object getKey() {
        return pk;
    }
}
