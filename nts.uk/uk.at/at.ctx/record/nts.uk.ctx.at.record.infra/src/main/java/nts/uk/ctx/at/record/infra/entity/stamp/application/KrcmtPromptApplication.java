package nts.uk.ctx.at.record.infra.entity.stamp.application;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;


@Entity
@NoArgsConstructor
@Table(name="KRCMT_PROMPT_APPLICATION")
public class KrcmtPromptApplication extends ContractUkJpaEntity{

	@EmbeddedId
    public KrcmtPromptApplicationPk pk;
	
	/** 利用区分 */
	@Column(name ="USE_ART")
	public int useArt;
	
	/** エラー有時に促すメッセージ */
	@Column(name ="MESSAGE_CONTENT")
	public int messageContent;
	
	/** メッセージ色*/
	@Column(name ="MESSAGE_COLOR")
	public int MessageColor;
	
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
}																	
			
						
					
									
