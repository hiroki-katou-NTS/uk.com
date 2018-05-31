package nts.uk.ctx.at.function.infra.entity.monthlycorrection.fixedformatmonthly;


import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 月別実績のグリッドの列幅: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KrcmtMonGridColWidthPk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * 会社ID
    */
    @Basic(optional = false)
    @Column(name = "CID")
    public String cid;
    
    /**
    * 勤怠項目ID
    */
    @Basic(optional = false)
    @Column(name = "ATTENDANCE_ITEM_ID")
    public int attendanceItemId;
    
}

