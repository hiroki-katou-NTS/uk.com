package nts.uk.ctx.sys.assist.infra.entity.storage;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.sys.assist.dom.storage.SystemType;
import nts.uk.ctx.sys.assist.dom.storage.TargetCategory;
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
        return new TargetCategory(targetCategoryPk.storeProcessingId, 
        						targetCategoryPk.categoryId,
        						EnumAdaptor.valueOf(targetCategoryPk.systemType, SystemType.class)
        						);
    }
    
    public static SspmtTargetCategory toEntity(TargetCategory domain) {
        return new SspmtTargetCategory(new SspmtTargetCategoryPk(
        		domain.getStoreProcessingId(), 
        		domain.getCategoryId(),
        		domain.getSystemType().value));
    }

}
