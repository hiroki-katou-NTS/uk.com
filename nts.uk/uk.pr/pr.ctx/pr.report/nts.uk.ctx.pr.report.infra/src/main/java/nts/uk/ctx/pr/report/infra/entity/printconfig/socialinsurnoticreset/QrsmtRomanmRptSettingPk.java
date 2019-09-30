package nts.uk.ctx.pr.report.infra.entity.printconfig.socialinsurnoticreset;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * ローマ字氏名届作成設定: 主キー情報
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QrsmtRomanmRptSettingPk implements Serializable
{
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
