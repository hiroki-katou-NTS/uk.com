package nts.uk.ctx.pr.core.infra.entity.laborinsurance.laborinsuranceoffice;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 労働保険事業所: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtLaborInsuOfficePk implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * 会社ID
     */
    @Basic(optional = false)
    @Column(name = "CID")
    public String cid;

    /**
     * コード
     */
    @Basic(optional = false)
    @Column(name = "LABOR_OFFICE_CODE")
    public String laborOfficeCode;

}
