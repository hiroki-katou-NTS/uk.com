package nts.uk.ctx.at.function.app.find.alarm.mastercheck;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.MasterCheckFixedExtractItem;

@Getter
@NoArgsConstructor
public class MasterCheckFixedExtractItemDto {
	
	private int no;
	
	private String message;
	
	private int erAlAtr;
	
	private String name;

	public MasterCheckFixedExtractItemDto(int no, String message, int erAlAtr, String name) {
		super();
		this.no = no;
		this.message = message;
		this.erAlAtr = erAlAtr;
		this.name = name;
	}
	
	public static MasterCheckFixedExtractItemDto fromDomain(MasterCheckFixedExtractItem domain) {
		return new MasterCheckFixedExtractItemDto(
				domain.getNo().value,
				domain.getInitMessage().v(),
				domain.getErAlAtr().value,
				domain.getName().v()
				);
	}
}
