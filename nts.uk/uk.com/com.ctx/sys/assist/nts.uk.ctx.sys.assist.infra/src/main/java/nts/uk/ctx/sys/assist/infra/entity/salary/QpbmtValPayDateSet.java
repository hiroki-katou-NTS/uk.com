package nts.uk.ctx.sys.assist.infra.entity.salary;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.assist.dom.salary.ValPayDateSet;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 支払日の設定の規定値
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_VAL_PAY_DATE_SET")
public class QpbmtValPayDateSet extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtValPayDateSetPk valPayDateSetPk;
    
    @Override
    protected Object getKey()
    {
        return valPayDateSetPk;
    }

    public ValPayDateSet toDomain() {
        return new ValPayDateSet(this.valPayDateSetPk.cid, this.valPayDateSetPk.processCateNo );
    }
    public static QpbmtValPayDateSet toEntity(ValPayDateSet domain) {
        return new QpbmtValPayDateSet(new QpbmtValPayDateSetPk(domain.getCid(), domain.getProcessCateNo()) );
    }

}
