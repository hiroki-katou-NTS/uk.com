package nts.uk.ctx.pr.transfer.infra.entity.rsdttaxpayee;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.transfer.dom.rsdttaxpayee.ResidentTaxPayee;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 住民税納付先
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QXXMT_RSDT_TAX_PAYEE")
public class QxxmtRsdtTaxPayee extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QxxmtRsdtTaxPayeePk rsdtTaxPayeePk;

    /**
     * 名称
     */
    @Basic(optional = false)
    @Column(name = "NAME")
    public String name;

    /**
     * カナ名
     */
    @Basic(optional = true)
    @Column(name = "KANA_NAME")
    public String kanaName;

    /**
     * とりまとめ局.名称
     */
    @Basic(optional = true)
    @Column(name = "COMPILE_STATION_NAME")
    public String compileStationName;

    /**
     * とりまとめ局.郵便番号
     */
    @Basic(optional = true)
    @Column(name = "COMPILE_STATION_ZIP_CD")
    public String compileStationZipCd;

    /**
     * メモ
     */
    @Basic(optional = true)
    @Column(name = "NOTE")
    public String note;

    /**
     * 加入者名
     */
    @Basic(optional = true)
    @Column(name = "SUBCRIBER_NAME")
    public String subcriberName;

    /**
     * 口座番号
     */
    @Basic(optional = true)
    @Column(name = "ACCOUNT_NUM")
    public String accountNum;

    /**
     * 報告先コード
     */
    @Basic(optional = true)
    @Column(name = "REPORT_CD")
    public String reportCd;

    /**
     * 指定番号
     */
    @Basic(optional = true)
    @Column(name = "DESIGNATION_NUM")
    public String designationNum;

    /**
     * 都道府県
     */
    @Basic(optional = false)
    @Column(name = "PREFECTURES")
    public int prefectures;

    @Override
    protected Object getKey() {
        return rsdtTaxPayeePk;
    }

    public ResidentTaxPayee toDomain() {
        return new ResidentTaxPayee(this.rsdtTaxPayeePk.cid, this.rsdtTaxPayeePk.code, this.name, this.kanaName,
                this.compileStationName, this.compileStationZipCd, this.note, this.subcriberName, this.accountNum,
                this.reportCd, this.designationNum, this.prefectures);
    }

    public static QxxmtRsdtTaxPayee toEntity(ResidentTaxPayee domain) {
        return new QxxmtRsdtTaxPayee(new QxxmtRsdtTaxPayeePk(domain.getCid(), domain.getCode().v()),
                domain.getName().v(), domain.getKanaName().map(i -> i.v()).orElse(null),
                domain.getCompileStation().getName().map(i -> i.v()).orElse(null),
                domain.getCompileStation().getZipCode().map(i -> i.v()).orElse(null),
                domain.getNote().map(i -> i.v()).orElse(null),
                domain.getSubscriberName().map(i -> i.v()).orElse(null),
                domain.getAccountNumber().map(i -> i.v()).orElse(null),
                domain.getReportCd().map(i -> i.v()).orElse(null),
                domain.getDesignationNum().map(i -> i.v()).orElse(null),
                domain.getPrefectures());
    }

}
