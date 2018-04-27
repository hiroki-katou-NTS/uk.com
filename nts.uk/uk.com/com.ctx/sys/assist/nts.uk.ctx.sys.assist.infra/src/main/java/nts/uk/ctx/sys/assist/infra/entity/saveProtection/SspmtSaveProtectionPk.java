package nts.uk.ctx.sys.assist.infra.entity.saveProtection;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

/**
* 保存保護: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@Embeddable
public class SspmtSaveProtectionPk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
}
