package nts.uk.ctx.at.record.infra.entity.stamp.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.stamp.application.CheckErrorType;
import nts.uk.ctx.at.record.dom.stamp.application.MessageContent;
import nts.uk.ctx.at.record.dom.stamp.application.PromptingMessage;
import nts.uk.ctx.at.record.dom.stamp.application.StampPromptApplication;
import nts.uk.ctx.at.record.dom.stamp.application.StampRecordDis;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
import nts.uk.shr.com.enumcommon.NotUseAtr;


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
	public String messageContent;
	
	/** メッセージ色*/
	@Column(name ="MESSAGE_COLOR")
	public String messageColor;
	
	
	@Override
	protected Object getKey() {
		return this.pk;
	}

	public static KrcmtPromptApplication toEntity(StampRecordDis listStamp, String companyId){
		return new KrcmtPromptApplication(new KrcmtPromptApplicationPk(
				companyId, 
				listStamp.getCheckErrorType().value),
				listStamp.getUseArt().value, 
				listStamp.getPromptingMssage().get().getMessageContent().v(), 
				listStamp.getPromptingMssage().get().getMessageColor().v() );
	}

	public static List<KrcmtPromptApplication> toEntity(StampPromptApplication application) {
		List<KrcmtPromptApplication> lstPrompt = new ArrayList<>();
		for(StampRecordDis listStamp : application.getLstStampRecordDis()){
			lstPrompt.add(KrcmtPromptApplication.toEntity(listStamp, application.getCompanyId()));
		}
		return lstPrompt;
	}
	
	
	public StampRecordDis toDomainRecord(){
		return new StampRecordDis(
				EnumAdaptor.valueOf(this.useArt, NotUseAtr.class) , 
				EnumAdaptor.valueOf(this.pk.errorType, CheckErrorType.class), 
				Optional.of(new PromptingMessage(
						new MessageContent(this.messageContent), 
						new ColorCode(this.messageColor))));
	}

	public KrcmtPromptApplication(KrcmtPromptApplicationPk pk, int useArt, String messageContent, String messageColor) {
		super();
		this.pk = pk;
		this.useArt = useArt;
		this.messageContent = messageContent;
		this.messageColor = messageColor;
	}
	
}																	
			
						
					
									
