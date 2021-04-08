package nts.uk.ctx.at.function.infra.entity.arbitraryperiodsummarytable;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.arbitraryperiodsummarytable.OutputSettingOfArbitrary;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "KFNMT_RPT_WK_ANP_OUTATD")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class KfnmtRptWkAnpOutAtd extends UkJpaEntity implements Serializable {

    @EmbeddedId
    public KfnmtRptWkAnpOutatdPk pk;
    /**
     * 契約コード
     */
    @Column(name = "CONTRACT_CD")
    public String contractCd;

    /**
     * 会社コード
     */
    @Column(name = "CID")
    public String companyId;

    /**
     * 	勤怠項目ID -> 印刷する勤怠項目.勤怠項目ID
     */
    @Column(name = "ATD_ITEM_ID")
    public int atdItemId;


    @Override
    protected Object getKey() {
        return pk;
    }

    public static List<KfnmtRptWkAnpOutAtd> fromDomain(OutputSettingOfArbitrary outputSetting) {
        return outputSetting.getOutputItemList().stream().map(e -> new KfnmtRptWkAnpOutAtd(
                new KfnmtRptWkAnpOutatdPk(outputSetting.getSettingId(), e.getRanking()),
                AppContexts.user().contractCode(),
                AppContexts.user().companyId(),
                e.getAttendanceId()
        )).collect(Collectors.toCollection(ArrayList::new));
    }
}
