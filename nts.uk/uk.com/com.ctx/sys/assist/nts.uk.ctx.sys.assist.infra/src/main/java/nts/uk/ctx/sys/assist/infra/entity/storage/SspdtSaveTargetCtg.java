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
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
* 対象カテゴリ
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SSPDT_SAVE_TARGET_CTG")
public class SspdtSaveTargetCtg extends ContractUkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public SspdtSaveTargetCtgPk targetCategoryPk;
    
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
    
    public static SspdtSaveTargetCtg toEntity(TargetCategory domain) {
        return new SspdtSaveTargetCtg(new SspdtSaveTargetCtgPk(
        		domain.getStoreProcessingId(), 
        		domain.getCategoryId(),
        		domain.getSystemType().value));
    }

}
