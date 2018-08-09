package nts.uk.ctx.sys.log.app.find.reference;

import lombok.Data;
import nts.uk.ctx.sys.log.dom.reference.ItemNoEnum;
import nts.uk.ctx.sys.log.dom.reference.LogOutputItem;
import nts.uk.ctx.sys.log.dom.reference.RecordTypeEnum;

/*
 * author: hiep.th
 */

@Data
public class LogOutputItemDto {

	public LogOutputItemDto() {
		super();
	}

	/** The Item NO. */
	private int itemNo;

	/** The Item Name */
	private String itemName;

	/** The Record Type */
	private int recordType;

	/** Sort Order */
	private int sortOrder;

	public static LogOutputItemDto fromDomain(LogOutputItem domain) {

		LogOutputItemDto logOutputItemDto = new LogOutputItemDto(domain.getItemNo(), domain.getItemName().v(),
				domain.getRecordType().code);
		RecordTypeEnum recordTypeEnum = RecordTypeEnum.valueOf(logOutputItemDto.getRecordType());
		ItemNoEnum itemNoEnum = ItemNoEnum.valueOf(logOutputItemDto.getItemNo());
		switch (recordTypeEnum) {
		case LOGIN:
			switch (itemNoEnum) {
			case ITEM_NO_2:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_2.code);
				break;
			case ITEM_NO_3:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_1.code);
				break;
			case ITEM_NO_7:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_3.code);
				break;
			case ITEM_NO_19:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_5.code);
				break;
			case ITEM_NO_20:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_4.code);
				break;
			case ITEM_NO_22:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_6.code);
				break;
			default:
				break;
			}
			break;
		case START_UP:
			switch (itemNoEnum) {
			case ITEM_NO_2:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_2.code);
				break;
			case ITEM_NO_3:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_1.code);
				break;
			case ITEM_NO_7:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_3.code);
				break;
			case ITEM_NO_18:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_5.code);
				break;
			case ITEM_NO_19:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_4.code);
				break;
			default:
				break;
			}
			break;
		case UPDATE_PERSION_INFO:
			switch (itemNoEnum) {
			case ITEM_NO_2:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_5.code);
				break;
			case ITEM_NO_3:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_4.code);
				break;
			case ITEM_NO_7:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_6.code);
				break;
			case ITEM_NO_20:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_2.code);
				break;
			case ITEM_NO_21:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_1.code);
				break;
			case ITEM_NO_22:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_3.code);
				break;
			case ITEM_NO_23:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_1.code);
				break;
			case ITEM_NO_24:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_3.code);
				break;
			case ITEM_NO_29:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_2.code);
				break;
			case ITEM_NO_31:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_4.code);
				break;
			case ITEM_NO_33:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_5.code);
				break;
			case ITEM_NO_36:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_7.code);
				break;
			default:
				break;
			}
			break;
		case DATA_CORRECT:
			switch (itemNoEnum) {
			case ITEM_NO_2:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_4.code);
				break;
			case ITEM_NO_3:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_3.code);
				break;
			case ITEM_NO_7:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_5.code);
				break;
			case ITEM_NO_20:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_2.code);
				break;
			case ITEM_NO_21:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_1.code);
				break;
			case ITEM_NO_22:
			case ITEM_NO_23:
			case ITEM_NO_24:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_1.code);
				break;
			case ITEM_NO_26:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_5.code);
				break;
			case ITEM_NO_27:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_2.code);
				break;
			case ITEM_NO_30:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_3.code);
				break;
			case ITEM_NO_31:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_4.code);
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
		return logOutputItemDto;
	}
	
	public static LogOutputItemDto fromDomainAll(LogOutputItem domain) {

		LogOutputItemDto logOutputItemDto = new LogOutputItemDto(domain.getItemNo(), domain.getItemName().v(),
				domain.getRecordType().code);
		RecordTypeEnum recordTypeEnum = RecordTypeEnum.valueOf(logOutputItemDto.getRecordType());
		ItemNoEnum itemNoEnum = ItemNoEnum.valueOf(logOutputItemDto.getItemNo());
		switch (recordTypeEnum) {
		case LOGIN:
			switch (itemNoEnum) {
			case ITEM_NO_1:
				 logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_1.code);
				break;
			case ITEM_NO_2:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_2.code);
				break;
			case ITEM_NO_3:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_3.code);
				break;
			case ITEM_NO_4:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_4.code);
				break;
			case ITEM_NO_5:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_5.code);
				break;
			case ITEM_NO_6:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_6.code);
				break;
			case ITEM_NO_7:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_7.code);
				break;
			case ITEM_NO_8:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_8.code);
				break;
			case ITEM_NO_9:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_9.code);
				break;
			case ITEM_NO_10:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_10.code);
				break;
			case ITEM_NO_11:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_11.code);
				break;
			case ITEM_NO_12:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_12.code);
				break;
			case ITEM_NO_13:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_13.code);
				break;
			case ITEM_NO_14:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_14.code);
				break;
			case ITEM_NO_15:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_15.code);
				break;
				
			case ITEM_NO_16:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_16.code);
				break;
				
			case ITEM_NO_17:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_17.code);
				break;
				
			case ITEM_NO_18:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_18.code);
				break;
				
			case ITEM_NO_19:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_19.code);
				break;			
			case ITEM_NO_20:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_20.code);
				break;
				
			case ITEM_NO_21:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_21.code);
				break;
			case ITEM_NO_22:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_22.code);
				break;
			default:
				break;
			}
			break;
		case START_UP:
			switch (itemNoEnum) {
			case ITEM_NO_2:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_2.code);
				break;
			case ITEM_NO_3:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_1.code);
				break;
			case ITEM_NO_7:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_3.code);
				break;
			case ITEM_NO_18:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_5.code);
				break;
			case ITEM_NO_19:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_4.code);
				break;
			default:
				break;
			}
			break;
		case UPDATE_PERSION_INFO:
			switch (itemNoEnum) {
			case ITEM_NO_2:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_5.code);
				break;
			case ITEM_NO_3:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_4.code);
				break;
			case ITEM_NO_7:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_6.code);
				break;
			case ITEM_NO_20:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_2.code);
				break;
			case ITEM_NO_21:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_1.code);
				break;
			case ITEM_NO_22:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_3.code);
				break;
			case ITEM_NO_23:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_1.code);
				break;
			case ITEM_NO_24:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_3.code);
				break;
			case ITEM_NO_29:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_2.code);
				break;
			case ITEM_NO_31:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_4.code);
				break;
			case ITEM_NO_33:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_5.code);
				break;
			case ITEM_NO_36:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_7.code);
				break;
			default:
				break;
			}
			break;
		case DATA_CORRECT:
			switch (itemNoEnum) {
			case ITEM_NO_2:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_4.code);
				break;
			case ITEM_NO_3:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_3.code);
				break;
			case ITEM_NO_7:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_5.code);
				break;
			case ITEM_NO_20:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_2.code);
				break;
			case ITEM_NO_21:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_1.code);
				break;
			case ITEM_NO_22:
			case ITEM_NO_23:
			case ITEM_NO_24:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_1.code);
				break;
			case ITEM_NO_26:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_5.code);
				break;
			case ITEM_NO_27:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_2.code);
				break;
			case ITEM_NO_30:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_3.code);
				break;
			case ITEM_NO_31:
				logOutputItemDto.setSortOrder(ItemNoEnum.ITEM_NO_4.code);
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
		return logOutputItemDto;
	}

	public LogOutputItemDto(int itemNo, String itemName, int recordType) {
		super();
		this.itemNo = itemNo;
		this.itemName = itemName;
		this.recordType = recordType;

	}

}
