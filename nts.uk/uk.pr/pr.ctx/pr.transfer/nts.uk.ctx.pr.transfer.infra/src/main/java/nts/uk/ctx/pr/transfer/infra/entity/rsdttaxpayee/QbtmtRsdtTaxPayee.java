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
@Table(name = "QBTMT_RSDT_TAX_PAYEE")
public class QbtmtRsdtTaxPayee extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QbtmtRsdtTaxPayeePk rsdtTaxPayeePk;

    /**
     * 名称
     */
    @Basic(optional = false)
    @Column(name = "RESIDENT_TAX_NAME")
    public String name;

    /**
     * カナ名
     */
    @Basic(optional = true)
    @Column(name = "RESIDENT_TAX_KNNAME")
    public String kanaName;

    /**
     * 都道府県
     */
    @Basic(optional = false)
    @Column(name = "PREFECTURES_NO")
    public int prefectures;

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
    @Column(name = "REPORTS_CD")
    public String reportCd;

    /**
     * 指定番号
     */
    @Basic(optional = true)
    @Column(name = "DESIGNATION_NUM")
    public String designationNum;

    /**
     * 報告先指定番号
     */
    @Basic(optional = true)
    @Column(name = "REPORTS_NUM")
    public String reportNum;

    /**
     * メモ
     */
    @Basic(optional = true)
    @Column(name = "NOTE")
    public String note;

    @Override
    protected Object getKey() {
        return rsdtTaxPayeePk;
    }

    public ResidentTaxPayee toDomain() {
        return new ResidentTaxPayee(this.rsdtTaxPayeePk.cid, this.rsdtTaxPayeePk.code, this.name, this.kanaName,
                this.prefectures, this.compileStationName, this.compileStationZipCd, this.subcriberName, this.accountNum,
                this.reportCd, this.designationNum, this.reportNum, this.note);
    }

    public static QbtmtRsdtTaxPayee toEntity(ResidentTaxPayee domain) {
        return new QbtmtRsdtTaxPayee(new QbtmtRsdtTaxPayeePk(domain.getCid(), domain.getCode().v()),
                domain.getName().v(), domain.getKanaName().map(i -> i.v()).orElse(null),
                domain.getPrefectures(),
                domain.getCompileStation().getName().map(i -> i.v()).orElse(null),
                domain.getCompileStation().getZipCode().map(i -> i.v()).orElse(null),
                domain.getSubscriberName().map(i -> i.v()).orElse(null),
                domain.getAccountNumber().map(i -> i.v()).orElse(null),
                domain.getReportCd().map(i -> i.v()).orElse(null),
                domain.getDesignationNum().map(i -> i.v()).orElse(null),
                domain.getReportNum().map(i -> i.v()).orElse(null),
                domain.getNote().map(i -> i.v()).orElse(null));
    }

}
