package nts.uk.ctx.pr.report.infra.entity.printconfig.empinsreportsetting;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 *  雇用保険届テキスト出力設定: 主キー情報
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QrsmtEmpInsRptTxtSettingPk implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 会社ID
     */
    @Basic(optional = false)
    @Column(name = "CID")
    public String cid;

    /**
     * ユーザID
     */
    @Basic(optional = false)
    @Column(name = "USER_ID")
    public String userId;
}
