package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empcomofficehis;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.AffOfficeInformation;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;


/**
* 所属事業所情報
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQBMT_AFF_OFFICE_INFOR")
public class QqbmtAffOfficeInfor extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QqbmtAffOfficeInforPk affOfficeInforPk;
    
    @Override
    protected Object getKey()
    {
        return affOfficeInforPk;
    }

    public AffOfficeInformation toDomain() {
       return null;
    }
    public static QqbmtAffOfficeInfor toEntity(AffOfficeInformation domain) {
        return null;
    }

}
