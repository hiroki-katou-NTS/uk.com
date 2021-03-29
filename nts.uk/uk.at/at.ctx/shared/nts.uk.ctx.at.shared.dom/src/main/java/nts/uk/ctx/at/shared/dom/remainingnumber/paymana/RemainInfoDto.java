package nts.uk.ctx.at.shared.dom.remainingnumber.paymana;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RemainInfoDto {
	
	//	発生管理データID
	private String occurrenceId;
	
	//	発生時間数
	private Integer occurrenceHour; 
	
	//	発生日数
	private Double occurrenceDay;
	
	//	発生日
	private String accrualDate;
	
	//	消化管理データID
	private String digestionId;
	
	//	消化時間数
	private Integer digestionTimes;
	
	//	/消化日数
	private Double digestionDays;
	
	//	消化日
	private String digestionDay;
	
	//	法定区分
	private Integer legalDistinction;
	
	//	残数時間数
	private Integer remainingHours;
	
	//	残数日数
	private Double dayLetf;
	
	//	期限日
	private String deadLine;
	
	//	使用期限時間数
	private Integer usedTime;
	
	//	使用期限日数
	private Double usedDay;
	
	private Integer mergeCell;
}
