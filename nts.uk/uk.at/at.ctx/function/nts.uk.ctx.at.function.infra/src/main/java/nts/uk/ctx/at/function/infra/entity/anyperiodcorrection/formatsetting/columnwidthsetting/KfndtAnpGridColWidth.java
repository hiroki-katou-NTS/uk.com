package nts.uk.ctx.at.function.infra.entity.anyperiodcorrection.formatsetting.columnwidthsetting;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractCompanyUkJpaEntity;

import javax.persistence.*;

@Entity
@Table(name = "KFNDT_ANP_GRID_COL_WIDTH")
@NoArgsConstructor
@AllArgsConstructor
public class KfndtAnpGridColWidth extends ContractCompanyUkJpaEntity {
    @EmbeddedId
    public KfndtAnpGridColWidthPk pk;

    @Column(name = "COLUMN_WIDTH")
    public int columnWidth;

    @Override
    protected Object getKey() {
        return pk;
    }
}
