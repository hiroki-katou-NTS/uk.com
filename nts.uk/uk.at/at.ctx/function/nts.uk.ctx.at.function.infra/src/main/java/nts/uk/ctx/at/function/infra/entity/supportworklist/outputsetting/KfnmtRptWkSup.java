package nts.uk.ctx.at.function.infra.entity.supportworklist.outputsetting;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.*;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="KFNMT_RPT_WK_SUP")
@NoArgsConstructor
@AllArgsConstructor
public class KfnmtRptWkSup extends ContractUkJpaEntity {
    @EmbeddedId
    public KfnmtRptWkSupPk pk;

    @Column(name = "NAME")
    public String name;

    @Column(name = "SYA_EXTRACT_CND")
    public int employeeExtractCondition;

    @Column(name = "DETAIL_ATR")
    public int detailAtr;

    @Column(name = "SUM_DAY_ATR")
    public int sumDayAtr;

    @Column(name = "SUM_SUP_WPL_ATR")
    public int sumSupWplAtr;

    @Column(name = "SUM_SUP_DETAIL_ATR")
    public int sumSupDetailAtr;

    @Column(name = "SUM_WPL_ATR")
    public int sumWplAtr;

    @Column(name = "TOTAL_SUM_SUP_WPL_ATR")
    public int totalSumSupWplAtr;

    @Column(name = "TOTAL_SUM_ATR")
    public int totalSumAtr;

    @Column(name = "BREAK_PAGE_ATR")
    public int breakPageAtr;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reportSetting", orphanRemoval = true, fetch = FetchType.LAZY)
    public List<KfnmtRptWkSupItem> items;

    @Override
    protected Object getKey() {
        return pk;
    }

    public SupportWorkListOutputSetting toDomain(List<KfnmtRptWkSupItem> items) {
        return new SupportWorkListOutputSetting(
                new SupportWorkOutputCode(pk.code),
                new SupportWorkOutputName(name),
                new DetailLayoutSetting(
                        EnumAdaptor.valueOf(employeeExtractCondition, EmployeeExtractCondition.class),
                        new DetailDisplaySetting(
                                EnumAdaptor.valueOf(detailAtr, NotUseAtr.class),
                                items.stream().sorted(Comparator.comparing(i -> i.displayOrder)).map(i -> new OutputItem(i.pk.attendanceItemId, i.displayOrder)).collect(Collectors.toList())
                        ),
                        new GrandTotalDisplaySetting(
                                EnumAdaptor.valueOf(totalSumAtr, NotUseAtr.class),
                                EnumAdaptor.valueOf(totalSumSupWplAtr, NotUseAtr.class)
                        ),
                        new WorkplaceTotalDisplaySetting(
                                EnumAdaptor.valueOf(sumDayAtr, NotUseAtr.class),
                                EnumAdaptor.valueOf(sumSupDetailAtr, NotUseAtr.class),
                                EnumAdaptor.valueOf(sumSupWplAtr, NotUseAtr.class),
                                EnumAdaptor.valueOf(sumWplAtr, NotUseAtr.class)
                        ),
                        EnumAdaptor.valueOf(breakPageAtr, NotUseAtr.class)
                )
        );
    }
}
