package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empbenepenpeninfor;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.WelfarePenTypeInfor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;


/**
* 厚生年金種別情報
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQBMT_WELF_PEN_TYPE_INFOR")
public class QqbmtWelfPenTypeInfor extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QqbmtWelfPenTypeInforPk welfPenTypeInforPk;
    
    /**
    * 坑内員区分
    */
    @Basic(optional = false)
    @Column(name = "UNDERGOUND_DIVISION")
    public int undergoundDivision;
    
    @Override
    protected Object getKey()
    {
        return welfPenTypeInforPk;
    }

    public WelfarePenTypeInfor toDomain() {
        return new WelfarePenTypeInfor(this.welfPenTypeInforPk.historyId, this.undergoundDivision);
    }
    public static QqbmtWelfPenTypeInfor toEntity(WelfarePenTypeInfor domain) {
        return new QqbmtWelfPenTypeInfor(new QqbmtWelfPenTypeInforPk(domain.getHistoryId()),domain.getUndergroundDivision().value);
    }

}
