package nts.uk.ctx.sys.assist.infra.entity.storage;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 対象カテゴリ
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SSPMT_TARGET_CATEGORY")
public class SspmtTargetCategory extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public SspmtTargetCategoryPk targetCategoryPk;
    
    @Override
    protected Object getKey()
    {
        return targetCategoryPk;
    }

    public TargetCategory toDomain() {
        return new TargetCategory(this.targetCategoryPk.storeProcessingId, this.targetCategoryPk.categoryId, );
    }
    public static SspmtTargetCategory toEntity(TargetCategory domain) {
        return new SspmtTargetCategory(new SspmtTargetCategoryPk(domain.getStoreProcessingId(), domain.getCategoryId()), );
    }

}
